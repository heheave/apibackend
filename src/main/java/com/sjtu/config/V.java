package com.sjtu.config;

/**
 * Created by xiaoke on 17-5-16.
 */
public class V {
    // used for para info
    public static final String LOG_PATH = "file/log4j.properties" ;
    public static final String VAR_FILE_PATH = "file/var.xml";
    public static final String EMPRY_JSON_OBJ = "{}";
    public static final String EMPRY_JSON_ARY = "[]";
    public static final String[] DEVICE_TYPE = {"SWITCH", "DIGITL", "ANALOG"};
    public static final String AVG_MIN = "MIN";
    public static final String AVG_HOUR = "HOUR";
    public static final String AVG_DAY = "DAY";

    public static final String REDIS_HOST_ADDRESS = "sjtumaster.redis.host.address";
    public static final String REDIS_MAX_ACTIVE = "sjtumaster.redis.max.active";
    public static final String REDIS_MAX_IDLE = "sjtumaster.redis.max.idle";
    public static final String REDIS_MAX_WAIT = "sjtumaster.redis.max.wait";
    public static final String REDIS_TEST_ON_BORROW = "sjtumaster.redis.test.on.borrow";
    public static final String REDIS_LIST_MAX_NUM = "sjtumaster.reids.list.max.num";


    // used for kafka
    public static final String KAFKA_REALTIME_TOPIC = "sjtumaster.kafka.realtime.topic";
    public static final String KAFKA_AVG_TOPIC = "sjtumaster.kafka.avg.topic";
    public static final String KAFKA_CONSUMER_THREADS = "sjtumaster.kafka.consumer.threads";
    public static final String KAFKA_ZK_URL = "sjtumaster.kafka.dislock.url";
    public static final String KAFKA_BROKER_URL = "sjtumaster.kafka.broker.url";
    public static final String KAFKA_GROUP_ID = "sjtumaster.kafka.group.id";
    public static final String KAFKA_ZK_SESSION_TIMEOUT = "sjtumaster.kafka.dislock.session.timeout";
    public static final String KAFKA_AUTO_OFFSET_RESET = "sjtumaster.auto.offset.reset";
    public static final String KAFKA_ZK_SYNC_TIME = "sjtumaster.kafka.dislock.sync.time";
    public static final String KAFKA_REBALANCE_MAX_RETRIES = "sjtumaster.kafka.rebalance.max.retries";
    public static final String KAFKA_REBALANCE_BACKOFF = "sjtumaster.kafka.rebalance.backoff";
    public static final String KAFKA_AUTO_COMMIT_INTERVAL = "sjtumaster.auto.commit.interval";

    public static final String DSLOCK_ZK_HOST = "sjtumaster.dslock.zk.host";
    public static final String DSLOCK_ZK_PORT = "sjtumaster.dslock.zk.port";
    public static final String DSLOCK_ZK_TIMEOUT = "sjtumaster.dslock.zk.timeout";
    public static final String DSLOCK_ZK_PATH = "sjtumaster.dslock.zk.path";
    public static final String DSLOCK_ZK_UPDATE_PATH = "sjtumaster.dslock.zk.update.path";
    public static final String DSLOCK_ZK_LOCK_MINIMUM = "sjtumaster.dslock.lock.minimum";

}
