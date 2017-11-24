package com.sjtu.pojo;

import com.sjtu.avgcache.AvgCacheValue;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by xiaoke on 17-6-6.
 */
public class DeviceAvgValue {

    private final String appName;

    private final String dmark;

    private final int port;

    private static class AVG_V {

        private double sumData;

        private int sumNum;

        public AVG_V(double sum, int num) {
            this.sumData = sum;
            this.sumNum = num;
        }

        public double getSumData() {
            return sumData;
        }

        public int getSumNum() {
            return sumNum;
        }
    }
    private final Map<String, AVG_V> avgValues;

    public DeviceAvgValue(String appName, String dmark, int port) {
        this.appName = appName;
        this.dmark = dmark;
        this.port = port;
        this.avgValues = new HashMap<String, AVG_V>();
    }

    public void addAvgValue(AvgCacheValue avgCacheValue) {
        if (avgCacheValue != null) {
            String type = avgCacheValue.getAvgMark();
            if (type != null) {
                AVG_V av = new AVG_V(avgCacheValue.getSumData(), avgCacheValue.getSumNum());
                avgValues.put(type, av);
            }
        }
    }
    public String getAppName() {
        return appName;
    }

    public String getDmark() {
        return dmark;
    }

    public int getPort() {
        return port;
    }

    public Map<String, AVG_V> getAvgValues() {
        return avgValues;
    }
}
