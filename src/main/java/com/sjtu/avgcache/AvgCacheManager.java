package com.sjtu.avgcache;

import com.sjtu.dao.RedisDAO;
import com.sjtu.util.SerializeUtil;

/**
 * Created by xiaoke on 17-6-6.
 */
public class AvgCacheManager {

    private static final RedisDAO ra = new RedisDAO();

    public static AvgCacheValue put(String pid, String avgName, String avgMark, double sum, int num) {
        AvgCacheValue retV = null;
        AvgCacheKey key = new AvgCacheKey(pid, avgName);
        byte[] bkey = SerializeUtil.serialize(key);
        if (bkey == null) {
            retV =  null;
        } else {
            byte[] tmpV = ra.get(bkey);
            AvgCacheValue oldValue =  tmpV == null ? null : (AvgCacheValue) SerializeUtil.deserialize(tmpV);
            if (oldValue != null) {
                if (avgMark.equals(oldValue.getAvgMark())) {
                    oldValue.add(sum, num);
                } else {
                    byte[] newValue = SerializeUtil.serialize(new AvgCacheValue(avgMark, sum, num));
                    if (newValue != null) {
                        ra.set(bkey, newValue);
                        retV = oldValue;
                    }
                }
            } else {
                byte[] newValue = SerializeUtil.serialize(new AvgCacheValue(avgMark, sum, num));
                if (newValue != null) {
                    ra.set(bkey, newValue);
                }
            }
        }
        return retV;
    }

    public static AvgCacheValue get(String pid, String avgName) {
        AvgCacheKey key = new AvgCacheKey(pid, avgName);
        byte[] bkey = SerializeUtil.serialize(key);
        if (bkey != null) {
            byte[] tmpV = ra.get(bkey);
            return tmpV == null ? null : (AvgCacheValue) SerializeUtil.deserialize(tmpV);
        }
        return null;
    }
}
