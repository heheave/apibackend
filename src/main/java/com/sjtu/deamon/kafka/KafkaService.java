package com.sjtu.deamon.kafka;

import com.sjtu.avgcache.AvgCacheManager;
import com.sjtu.avgcache.AvgCacheValue;
import com.sjtu.avgcache.AvgTimeFormat;
import com.sjtu.config.Configure;
import com.sjtu.config.JsonField;
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

import java.util.*;
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

    private final int kafkaConsumerThreads;

    private ConsumerConnector connector;

    public KafkaService(Configure conf) {
        super();
        this.conf = conf;
        this.redisDAO = new RedisDAO();
        this.kafkaRealtimeTopic = conf.getStringOrElse(V.KAFKA_REALTIME_TOPIC, "cleaned-data-topic");
        this.kafkaAvgTopic = conf.getStringOrElse(V.KAFKA_AVG_TOPIC, "cleaned-avg-topic");
        this.kafkaConsumerThreads = conf.getIntOrElse(V.KAFKA_CONSUMER_THREADS, 10);
        this.executors = Executors.newFixedThreadPool(kafkaConsumerThreads + (kafkaConsumerThreads >> 2));
    }

    @Override
    protected void launch() throws Exception {
        ConsumerConfig config = createConsumerConfig();
        connector = Consumer.createJavaConsumerConnector(config);

        Map<String, Integer> topicThreadsMap = new HashMap<String, Integer>();
        topicThreadsMap.put(kafkaRealtimeTopic, kafkaConsumerThreads);
        topicThreadsMap.put(kafkaAvgTopic, kafkaConsumerThreads >> 2);
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
        final int redisMaxListCache = conf.getIntOrElse(V.REDIS_LIST_MAX_NUM, 10);
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
                        String id = jo.getString(JsonField.DeviceValue.ID);
                        try {
                            if (id != null) {
                                log.info("info id is " + id);
                                redisDAO.rpush(id, 10, message);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            log.equals(e);
                        }
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
                        String avg = jo.getString(JsonField.DeviceValue.AVGTYPE);
                        long pts = jo.getLong(JsonField.DeviceValue.PTIMESTAMP);
                        String avgMark = AvgTimeFormat.formatTime(pts, avg);
                        if (avgMark != null) {
                            String id = jo.getString(JsonField.DeviceValue.ID);
                            log.info("info id is " + id + "-" + avgMark);
                            int port = jo.getInt(JsonField.DeviceValue.PORTID);
                            double sum = jo.getDouble(JsonField.DeviceValue.SUMV);
                            int num = jo.getInt(JsonField.DeviceValue.NUMV);
                            AvgCacheValue newAvg = AvgCacheManager.put(id, port, avg, avgMark, sum, num);
                            if (newAvg != null) {
                                log.info("new avg info is " + id + ":" + port + ":" + avgMark);
                            }
                        }
                    }
                }
            }
        });
    }

    private ConsumerConfig createConsumerConfig() {
        String zookeeper = conf.getStringOrElse(V.KAFKA_ZK_URL, "192.168.1.110:2181");
        String broker = conf.getStringOrElse(V.KAFKA_BROKER_URL, "192.168.1.110:9092");
        String groupId = conf.getStringOrElse(V.KAFKA_GROUP_ID, "WEB_CONSUMER_GROUP");
        String autoOffsetReset = conf.getStringOrElse(V.KAFKA_AUTO_OFFSET_RESET, "largest");
        String sessionTimeout = conf.getStringOrElse(V.KAFKA_ZK_SESSION_TIMEOUT, "7000");
        String rbMaxRetries = conf.getStringOrElse(V.KAFKA_REBALANCE_MAX_RETRIES, "4");
        String rbBackOff = conf.getStringOrElse(V.KAFKA_REBALANCE_BACKOFF, "2000");
        String syncTime = conf.getStringOrElse(V.KAFKA_ZK_SYNC_TIME, "2000");
        String autoCommit = conf.getStringOrElse(V.KAFKA_AUTO_COMMIT_INTERVAL, "1000");
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
