package com.sjtu.avgcache;

import java.io.Serializable;

/**
 * Created by xiaoke on 17-6-6.
 */
public class AvgCacheValue implements Serializable{

    private final String avgMark;

    private double sumData;

    private int sumNum;

    public AvgCacheValue(String avgMark, double sumData, int sumNum) {
        this.avgMark = avgMark;
        this.sumData = sumData;
        this.sumNum = sumNum;
    }

    public AvgCacheValue(String avgMark) {
        this(avgMark, 0, 0);
    }

    public synchronized void add(double sumData, double sumNum) {
        this.sumData += sumData;
        this.sumNum += sumNum;
    }

    public String getAvgMark() {
        return avgMark;
    }

    public synchronized double getSumData() {
        return sumData;
    }

    public synchronized int getSumNum() {
        return sumNum;
    }
}
