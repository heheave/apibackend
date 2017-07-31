package com.sjtu.pojo;

import com.sjtu.avgcache.AvgCacheKey;
import com.sjtu.avgcache.AvgCacheValue;
import com.sjtu.avgcache.AvgTimeFormat;

/**
 * Created by xiaoke on 17-6-6.
 */
public class DeviceAvgValue {

    private String id;
    private int portid;
    private String avg;
    private long time;
    private double sum;
    private int num;

    public DeviceAvgValue(AvgCacheKey key, AvgCacheValue value) {
        if (key != null) {
            id = key.getDid();
            portid = key.getPidx();
            avg = key.getAvgType();
        }

        if (value != null) {
            time = AvgTimeFormat.unFormatTime(value.getAvgMark(), avg);
            sum = value.getSumData();
            num = value.getSumNum();
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getPortid() {
        return portid;
    }

    public void setPortid(int portid) {
        this.portid = portid;
    }

    public String getAvg() {
        return avg;
    }

    public void setAvg(String avg) {
        this.avg = avg;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public double getSum() {
        return sum;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}
