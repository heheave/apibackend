package com.sjtu.pojo;

import java.util.List;

/**
 * Created by xiaoke on 17-5-26.
 */
public class DeviceValue {

    private String dmark;

    private long dtimestamp;

    private long ptimestamp;

    private int portNum;

    private List<Value> values;

    public String getDmark() {
        return dmark;
    }

    public void setDmark(String dmark) {
        this.dmark = dmark;
    }

    public long getDtimestamp() {
        return dtimestamp;
    }

    public void setDtimestamp(long dtimestamp) {
        this.dtimestamp = dtimestamp;
    }

    public long getPtimestamp() {
        return ptimestamp;
    }

    public void setPtimestamp(long ptimestamp) {
        this.ptimestamp = ptimestamp;
    }

    public int getPortNum() {
        return portNum;
    }

    public void setPortNum(int portNum) {
        this.portNum = portNum;
    }

    public List<Value> getValues() {
        return values;
    }

    public void setValues(List<Value> values) {
        this.values = values;
    }
}
