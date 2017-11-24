package com.sjtu.accessor.cache.cacheHandler;

import com.sjtu.config.Configure;

/**
 * Created by xiaoke on 17-11-9.
 */
abstract public class CacheHandlerExecutor {

    protected final Configure conf;

    protected CacheHandlerExecutor(Configure conf) {
        this.conf = conf;
    }

    abstract public void start() throws Exception;

    abstract public void stop();

    abstract public byte[] getEntry(String key);
}
