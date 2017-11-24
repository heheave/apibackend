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

    public static final String CNT_SERVER_HOST = "192.168.31.110";
    public static final String CNT_SERVER_PORT = "9090";

    public static final Mark<String> APP_ZK_ROOT_PATH = Mark.makeMark("sjtumaster.app.root.path", "/application");
    public static final Mark<String> DCONF_ZK_ROOT_PATH = Mark.makeMark("sjtumaster.dconf.root.path", "/deviceconf");
    
    public static final Mark<String> REDIS_HOST_ADDRESS = Mark.makeMark("sjtumaster.redis.host.address", "localhost");
    public static final Mark<Integer> REDIS_MAX_ACTIVE = Mark.makeMark("sjtumaster.redis.max.active");
    public static final Mark<Integer> REDIS_MAX_IDLE = Mark.makeMark("sjtumaster.redis.max.idle");
    public static final Mark<Integer> REDIS_MAX_WAIT = Mark.makeMark("sjtumaster.redis.max.wait", 3000);
    public static final Mark<Boolean> REDIS_TEST_ON_BORROW = Mark.makeMark("sjtumaster.redis.test.on.borrow", false);
    public static final Mark<Integer> REDIS_LIST_MAX_NUM = Mark.makeMark("sjtumaster.reids.list.max.num", 10);


    // used for kafka
    public static final Mark<String> KAFKA_REALTIME_TOPIC = Mark.makeMark("sjtumaster.kafka.realtime.topic", "cleaned-data-topic");
    public static final Mark<String> KAFKA_AVG_TOPIC = Mark.makeMark("sjtumaster.kafka.avg.topic", "cleaned-avg-topic");
    public static final Mark<Integer> KAFKA_REALTIME_CONSUMER_THREADS = Mark.makeMark("sjtumaster.kafka.realtime.consumer.threads", 4);
    public static final Mark<Integer> KAFKA_AVG_CONSUMER_THREADS = Mark.makeMark("sjtumaster.kafka.avg.consumer.threads", 2);
    public static final Mark<String> KAFKA_ZK_URL = Mark.makeMark("sjtumaster.kafka.dislock.url", "192.168.31.110:2181");
    public static final Mark<String> KAFKA_BROKER_URL = Mark.makeMark("sjtumaster.kafka.broker.url", "192.168.31.110:9092");
    public static final Mark<String> KAFKA_GROUP_ID = Mark.makeMark("sjtumaster.kafka.group.id", "WEB_CONSUMER_GROUP");
    public static final Mark<String> KAFKA_ZK_SESSION_TIMEOUT = Mark.makeMark("sjtumaster.kafka.dislock.session.timeout", "7000");
    public static final Mark<String> KAFKA_AUTO_OFFSET_RESET = Mark.makeMark("sjtumaster.auto.offset.reset", "largest");
    public static final Mark<String> KAFKA_ZK_SYNC_TIME = Mark.makeMark("sjtumaster.kafka.dislock.sync.time", "2000");
    public static final Mark<String> KAFKA_REBALANCE_MAX_RETRIES = Mark.makeMark("sjtumaster.kafka.rebalance.max.retries", "4");
    public static final Mark<String> KAFKA_REBALANCE_BACKOFF = Mark.makeMark("sjtumaster.kafka.rebalance.backoff", "2000");
    public static final Mark<String> KAFKA_AUTO_COMMIT_INTERVAL = Mark.makeMark("sjtumaster.auto.commit.interval", "1000");

    public static final Mark<String> DSLOCK_ZK_URL = Mark.makeMark("sjtumaster.dslock.zk.host", "localhost:2181");
    //public static final Mark<Integer> DSLOCK_ZK_PORT = Mark.makeMark("sjtumaster.dslock.zk.port", 2181);
    public static final Mark<Integer> DSLOCK_ZK_TIMEOUT = Mark.makeMark("sjtumaster.dslock.zk.timeout", 4000);
    public static final Mark<String> DSLOCK_ZK_FAIR_PATH = Mark.makeMark("sjtumaster.dslock.zk.fair.path", "/dslockTmpPath");
    public static final Mark<String> DSLOCK_ZK_UNFAIR_PATH = Mark.makeMark("sjtumaster.dslock.zk.unfair.path", "/dslock");
    public static final Mark<Long> DSLOCK_ZK_LOCK_MINIMUM = Mark.makeMark("sjtumaster.dslock.lock.minimum", 3000L);

    public static final Mark<String> RPC_BIND_HOST = Mark.makeMark("sjtumaster.rpc.bind.host", "localhost");
    public static final Mark<Integer> RPC_BIND_PORT = Mark.makeMark("sjtumaster.rpc.bind.port", 8099);
    public static final Mark<String> RPC_BIND_NAME = Mark.makeMark("sjtumaster.rpc.bind.name", "RPC-JOB-SERVER");


    // used for mongodb
    public static final Mark<String> MONGO_DB_HOST = Mark.makeMark("sjtumaster.mongo.db.host", "localhost");
    public static final Mark<Integer> MONGO_DB_PORT = Mark.makeMark("sjtumaster.mongo.db.port", 27017);
    public static final Mark<Integer> MONGO_DB_TIMEOUT = Mark.makeMark("sjtumaster.mongo.db.timeout", 5000);
    public static final Mark<String> MONGO_DBNAME_DEVICECONF = Mark.makeMark("sjtumaster.mongo.dbname.deviceconf", "dcDb");
    public static final Mark<String> MONGO_COLNAME_DEVICECONF = Mark.makeMark("sjtumaster.mongo.colname.deviceconf", "dcCol");


}
