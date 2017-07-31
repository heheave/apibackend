package com.sjtu.dao;

import com.sjtu.accessor.AccessorFactory;
import com.sjtu.accessor.RedisAccess;
import org.apache.log4j.Logger;
import redis.clients.jedis.Jedis;

import java.util.List;
import java.util.Set;

/**
 * Created by xiaoke on 17-5-28.
 */
public class RedisDAO {

    private static final Logger log = Logger.getLogger(RedisDAO.class);
    
    private static RedisAccess redisAccess = AccessorFactory.getRedisAccess();

    public boolean containsKey(String key) {
        Jedis accessor = redisAccess.getRedis();
        if (accessor == null) {
            return false;
        }
        try {
            return accessor.exists(key);
        } finally {
            redisAccess.release(accessor);
        }
    }

    public String get(String key) {
        Jedis accessor = redisAccess.getRedis();
        if (accessor == null) {
            return null;
        }
        try {
            return accessor.get(key);
        } finally {
            redisAccess.release(accessor);
        }
    }

    public byte[] get(byte[] key) {
        Jedis accessor = redisAccess.getRedis();
        if (accessor == null) {
            return null;
        }
        try {
            return accessor.get(key);
        } finally {
            redisAccess.release(accessor);
        }
    }

    public String set(String key, String value) {
        Jedis accessor = redisAccess.getRedis();
        if (accessor == null) {
            return null;
        }
        try {
            return accessor.set(key, value);
        } finally {
            redisAccess.release(accessor);
        }
    }

    public String set(byte[] key, byte[] value) {
        Jedis accessor = redisAccess.getRedis();
        if (accessor == null) {
            return null;
        }
        try {
            return accessor.set(key, value);
        } finally {
            redisAccess.release(accessor);
        }
    }


    public Long lpush(String key, long maxSize, String... value) {
        Jedis accessor = redisAccess.getRedis();
        if (accessor == null) {
            return -1L;
        }
        try {
            long length = accessor.lpush(key, value);
            if (length > maxSize) {
                accessor.rpop(key);
                return maxSize;
            }
            return length;
        } finally {
            redisAccess.release(accessor);
        }
    }

    public Long rpush(String key, long maxSize, String... value) {
        Jedis accessor = redisAccess.getRedis();
        if (accessor == null) {
            return -1L;
        }
        try {
            long length = accessor.rpush(key, value);
            if (length > maxSize) {
                accessor.lpop(key);
                return maxSize;
            }
            return length;
        } finally {
            redisAccess.release(accessor);
        }
    }

    public String lpop(String key) {
        Jedis accessor = redisAccess.getRedis();
        if (accessor == null) {
            return null;
        }
        try {
            return accessor.lpop(key);
        } finally {
            redisAccess.release(accessor);
        }
    }

    public String rpop(String key) {
        Jedis accessor = redisAccess.getRedis();
        if (accessor == null) {
            return null;
        }
        try {
            return accessor.rpop(key);
        } finally {
            redisAccess.release(accessor);
        }
    }

    public List<String> getRange(String key, int start, int end) {
        Jedis accessor = redisAccess.getRedis();
        if (accessor == null) {
            return null;
        }
        try {
            return accessor.lrange(key, start, end);
        } finally {
            redisAccess.release(accessor);
        }
    }

    public List<String> getAll(String key) {
        Jedis accessor = redisAccess.getRedis();
        if (accessor == null) {
            return null;
        }
        try {
            return accessor.lrange(key, 0, -1);
        } finally {
            redisAccess.release(accessor);
        }
    }

    public Long remove(String key) {
        Jedis accessor = redisAccess.getRedis();
        if (accessor == null) {
            return -1L;
        }
        try {
            return accessor.del(key);
        } finally {
            redisAccess.release(accessor);
        }
    }

    public Set<String> getKeys(String pattern) {
        Jedis accessor = redisAccess.getRedis();
        if (accessor == null) {
            return null;
        }
        try {
            return accessor.keys(pattern);
        } finally {
            redisAccess.release(accessor);
        }
    }
}
