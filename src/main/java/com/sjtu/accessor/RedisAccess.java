package com.sjtu.accessor;

import com.sjtu.config.Configure;
import com.sjtu.config.V;
import org.apache.log4j.Logger;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.List;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by xiaoke on 17-5-28.
 */
public class RedisAccess {

    private static final Logger log = Logger.getLogger(RedisAccess.class);

    private final Configure conf;

    private final JedisPoolConfig poolConfig;

    private final JedisPool pool;

    private final Lock redisGetLock;

    public RedisAccess(Configure conf) {
        this.conf = conf;
        this.poolConfig = new JedisPoolConfig();
        int predictMaxActive = conf.getIntOrElse(V.KAFKA_CONSUMER_THREADS, 10) << 1;
        int maxActive = conf.getIntOrElse(V.REDIS_MAX_ACTIVE, predictMaxActive);
        int maxIdle = conf.getIntOrElse(V.REDIS_MAX_IDLE, predictMaxActive >> 1);
        int maxWait = conf.getIntOrElse(V.REDIS_MAX_WAIT, 3000);
        boolean testOnBorrow = conf.getBooleanOrElse(V.REDIS_TEST_ON_BORROW, false);
        this.poolConfig.setMaxTotal(maxActive);
        this.poolConfig.setMaxIdle(maxIdle);
        this.poolConfig.setMaxWaitMillis(maxWait);
        this.poolConfig.setTestOnBorrow(testOnBorrow);
        String redisHost = this.conf.getStringOrElse(V.REDIS_HOST_ADDRESS, "localhost");
        this.pool = new JedisPool(poolConfig, redisHost);
        this.redisGetLock = new ReentrantLock();
    }


    public final Jedis getRedis() {
        redisGetLock.lock();
        Jedis jedis;
        try {
            if (pool == null) {
                throw new NullPointerException("Jeids pool is null");
            } else {
                jedis = pool.getResource();
            }
        } finally {
            redisGetLock.unlock();
        }
        return jedis;
    }

    public final void release(Jedis jedis) {
        if (pool == null) {
            throw new NullPointerException("Jeids pool is null");
        } else {
            pool.returnResource(jedis);
        }
    }
}
