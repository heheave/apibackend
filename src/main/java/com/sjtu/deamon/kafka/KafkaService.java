package com.sjtu.deamon.kafka;

import com.sjtu.avgcache.AvgCacheManager;
import com.sjtu.avgcache.AvgCacheValue;
import com.sjtu.avgcache.AvgTimeFormat;
import com.sjtu.config.Configure;
import com.sjtu.config.V;
import com.sjtu.dao.RedisDAO;
import com.sjtu.deamon.service.AbstractorDeamonService;
import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;
import kafka.message.MessageAndMetadata;
import org.apache.log4j.Logger;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by xiaoke on 17-5-29.
 */
public class KafkaService extends AbstractorDeamonService{

    private static final Logger log = Logger.getLogger(KafkaService.class);

    private final Configure conf;

    private final RedisDAO redisDAO;

    private final ExecutorService executors;

    private final String kafkaRealtimeTopic;

    private final String kafkaAvgTopic;

    private final int kafkaRealtimeConsumerThreads;

    private final int kafkaAvgConsumerThreads;

    private ConsumerConnector connector;

    public KafkaService(Configure conf) {
        super();
        this.conf = conf;
        this.redisDAO = new RedisDAO();
        this.kafkaRealtimeTopic = conf.getStringOrElse(V.KAFKA_REALTIME_TOPIC);
        this.kafkaAvgTopic = conf.getStringOrElse(V.KAFKA_AVG_TOPIC);
        this.kafkaRealtimeConsumerThreads = conf.getIntOrElse(V.KAFKA_REALTIME_CONSUMER_THREADS);
        this.kafkaAvgConsumerThreads = conf.getIntOrElse(V.KAFKA_AVG_CONSUMER_THREADS);
        this.executors = Executors.newFixedThreadPool(kafkaRealtimeConsumerThreads + kafkaAvgConsumerThreads);
    }

    @Override
    protected void launch() throws Exception {
        ConsumerConfig config = createConsumerConfig();
        connector = Consumer.createJavaConsumerConnector(config);

        Map<String, Integer> topicThreadsMap = new HashMap<String, Integer>();
        topicThreadsMap.put(kafkaRealtimeTopic, kafkaRealtimeConsumerThreads);
        topicThreadsMap.put(kafkaAvgTopic, kafkaAvgConsumerThreads);
        Map<String, List<KafkaStream<byte[], byte[]>>> kafkaStreams =
                connector.createMessageStreams(topicThreadsMap);
        List<KafkaStream<byte[], byte[]>> topicStreams = kafkaStreams.get(kafkaRealtimeTopic);
        for (final KafkaStream<byte[], byte[]> stream: topicStreams) {
            realtimeTaskSubmit(stream);
        }

        List<KafkaStream<byte[], byte[]>> topicStreams1 = kafkaStreams.get(kafkaAvgTopic);
        for (final KafkaStream<byte[], byte[]> stream: topicStreams1) {
            avgTaskSubmit(stream);
        }
    }

    private void realtimeTaskSubmit(final KafkaStream<byte[], byte[]> stream) {
        final int redisMaxListCache = conf.getIntOrElse(V.REDIS_LIST_MAX_NUM);
        executors.submit(new Runnable() {
            public void run() {
                ConsumerIterator<byte[], byte[]> iterator = stream.iterator();
                while (iterator.hasNext()) {
                    MessageAndMetadata<byte[], byte[]> mam = iterator.next();
                    //String topic = mam.topic();
                    //String key = mam.key() != null ? new String(mam.key()) : null;
                    String message = mam.message() != null ? new String(mam.message()) : null;
                    //int partition = mam.partition();
                    JSONObject jo = null;
                    try {
                        jo = new JSONObject(message);
                    } catch (Exception e) {
                        log.warn(String.format("Transfer %s to JSONObject error", message), e);
                    }
                    if (jo != null) {
                        log.info(jo.toString());
                        //Code$Result cr = RedisKeyUtil.getKey(jo);
                        String key = jo.get("pid").toString();
                        redisDAO.rpush("real-" + key, 10, message);
                    }
                    //log.info("Thread id: " + Thread.currentThread().getId()+ ", key: " + key + ", massge: " + message + ", partition: " + partition);
                }
            }
        });
    }

    private void avgTaskSubmit(final KafkaStream<byte[], byte[]> stream) {
        executors.submit(new Runnable() {
            public void run() {
                ConsumerIterator<byte[], byte[]> iterator = stream.iterator();
                while (iterator.hasNext()) {
                    MessageAndMetadata<byte[], byte[]> mam = iterator.next();
                    //String topic = mam.topic();
                    //String key = mam.key() != null ? new String(mam.key()) : null;
                    String message = mam.message() != null ? new String(mam.message()) : null;
                    //int partition = mam.partition();
                    JSONObject jo = null;
                    try {
                        jo = new JSONObject(message);
                    } catch (Exception e) {
                        log.warn(String.format("Transfer %s to JSONObject error", message), e);
                    }
                    if (jo != null) {
                        log.info(jo.toString());
                        String avg = jo.getString("avgName");
                        long pts = jo.getLong("st");
                        String avgMark = AvgTimeFormat.formatTime(pts, avg);
                        if (avgMark != null) {
                            String key = jo.get("pid").toString();
                            //redisDAO.rpush("avg-" + key, 10, message);
                            //String app = JOKeyGetUtil.getString(jo, JsonField.DeviceValue.APP);
                            //String id = JOKeyGetUtil.getString(jo, JsonField.DeviceValue.ID);
                            //if (app == null || id == null) return;
                            //log.info("info id is " + id + "-" + avgMark);
                            //int port = jo.getInt(JsonField.DeviceValue.PORTID);
                            double sum = jo.getDouble("sum");
                            int num = jo.getInt("num");
                            AvgCacheValue newAvg = AvgCacheManager.put(key, avg, avgMark, sum, num);
                            if (newAvg != null) {
                                log.info("new avg info is " + key + ":" + avg + ":" + avgMark);
                            }
                        }
                    }
                }
            }
        });
    }

    private ConsumerConfig createConsumerConfig() {
        String zookeeper = conf.getStringOrElse(V.KAFKA_ZK_URL);
        String broker = conf.getStringOrElse(V.KAFKA_BROKER_URL);
        String groupId = conf.getStringOrElse(V.KAFKA_GROUP_ID);
        String autoOffsetReset = conf.getStringOrElse(V.KAFKA_AUTO_OFFSET_RESET);
        String sessionTimeout = conf.getStringOrElse(V.KAFKA_ZK_SESSION_TIMEOUT);
        String rbMaxRetries = conf.getStringOrElse(V.KAFKA_REBALANCE_MAX_RETRIES);
        String rbBackOff = conf.getStringOrElse(V.KAFKA_REBALANCE_BACKOFF);
        String syncTime = conf.getStringOrElse(V.KAFKA_ZK_SYNC_TIME);
        String autoCommit = conf.getStringOrElse(V.KAFKA_AUTO_COMMIT_INTERVAL);
        Properties props = new Properties();
        props.put("zookeeper.connect", zookeeper);
        props.put("metadata.broker.list", broker);
        props.put("group.id", groupId);
        props.put("auto.offset.reset", autoOffsetReset);
        props.put("zookeeper.session.timeout.ms", sessionTimeout);
        props.put("rebalance.max.retries", rbMaxRetries);
        props.put("rebalance.backoff.ms", rbBackOff);
        props.put("zookeeper.sync.time.ms", syncTime);
        props.put("auto.commit.interval.ms", autoCommit);
        return new ConsumerConfig(props);
    }

    @Override
    protected void shutdown() {
        executors.shutdown();
        if (connector != null) {
            connector.shutdown();
        }
    }
}
