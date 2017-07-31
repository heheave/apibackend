package com.sjtu.pojo;

import java.util.List;

/**
 * Created by xiaoke on 17-5-26.
 */
public class DeviceValue {

    private String id;

    private String mtype;

    private String dtype;

    private String desc;

    private String company;

    private double locLong;

    private double locDim;

    private long dtimestamp;

    private long ptimestamp;

    private int portNum;

    private List<Value> values;

    public String getId() {
        return id;
    }

    public String getMtype() {
        return mtype;
    }

    public String getDtype() {
        return dtype;
    }

    public String getDesc() {
        return desc;
    }

    public String getCompany() {
        return company;
    }

    public double getLocLong() {
        return locLong;
    }

    public double getLocDim() {
        return locDim;
    }

    public long getDtimestamp() {
        return dtimestamp;
    }

    public long getPtimestamp() {
        return ptimestamp;
    }

    public int getPortNum() {
        return portNum;
    }

    public List<Value> getValues() {
        return values;
    }
}
