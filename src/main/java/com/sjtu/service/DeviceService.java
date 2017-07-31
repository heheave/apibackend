package com.sjtu.service;

import com.sjtu.accessor.AccessorFactory;
import com.sjtu.accessor.RedisAccess;
import com.sjtu.config.V;
import com.sjtu.dao.RedisDAO;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

/**
 * Created by xiaoke on 17-5-30.
 */
public class DeviceService {

    @Autowired
    private RedisDAO redisDAO;

    public Map<String, Set<String>> getAllDeviceList() {
        Map<String, Set<String>> res = new HashMap<String, Set<String>>(V.DEVICE_TYPE.length);
        for (String dt: V.DEVICE_TYPE) {
            String pattern = dt + "*";
            Set<String> tmp = redisDAO.getKeys(pattern);
            res.put(dt, tmp);
        }
        return res;
    }

    public List<String> getDevice(String did) {
        if (did != null) {
            return redisDAO.getAll(did);
        } else {
            return null;
        }
    }
}
