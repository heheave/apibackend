package com.sjtu.accessor.cache.cacheHandler;

import com.sjtu.config.Configure;
import com.sjtu.dao.CacheDAO;
import com.sjtu.dao.DE;
import org.apache.log4j.Logger;
import org.json.JSONArray;

import java.util.List;

/**
 * Created by xiaoke on 17-11-23.
 */
public class DeviceEntryHandler extends CacheHandlerExecutor{

    private static final Logger log = Logger.getLogger(DeviceEntryHandler.class);

    //private final CacheDAO cacheDAO;

    public DeviceEntryHandler(Configure conf) {
        super(conf);
        //cacheDAO = CacheDAO;
    }

    @Override
    public void start() throws Exception {
        log.info("Device Entry Cache Handler started !!!");
    }

    @Override
    public void stop() {
        log.info("Device Entry Cache Handler stopped !!!");
    }

    @Override
    public byte[] getEntry(String key) {
        log.info("Query key: " + key);
        List<DE> des = CacheDAO.queryKey(key);
        JSONArray ja = new JSONArray(des);
        log.info(ja.toString());
        return ja.toString().getBytes();
    }
}
