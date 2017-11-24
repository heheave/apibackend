package com.sjtu.pojo;

import org.json.JSONObject;

/**
 * Created by xiaoke on 17-11-13.
 */
public class HBInfo {

    private final float cpu;

    private final float mem;

    private final float io;

    private final float net;

    private final int msgNum;

    private final long msgBytes;

    private final int msgNumT;

    private final long msgBytesT;

    private final int cntNum;

    private final long time;

    public HBInfo(JSONObject jo) {
        this.cpu = jo.has("cpu") ? (float)jo.getDouble("cpu") : 0.0f;
        this.mem = jo.has("mem") ? (float)jo.getDouble("mem") : 0.0f;
        this.io = jo.has("io") ? (float)jo.getDouble("io") : 0.0f;
        this.net = jo.has("net") ? (float)jo.getDouble("net") : 0.0f;
        this.msgNum = jo.has("msgNum") ? jo.getInt("msgNum") : 0;
        this.msgBytes = jo.has("msgBytes") ? jo.getLong("msgBytes") : 0L;
        this.msgNumT = jo.has("msgNum") ? jo.getInt("msgNumT") : 0;
        this.msgBytesT = jo.has("msgBytes") ? jo.getLong("msgBytesT") : 0L;
        this.cntNum = jo.has("cntNum") ? jo.getInt("cntNum") : 0;
        this.time = jo.has("time") ? jo.getLong("time") : 0L;
    }

    public float getCpu() {
        return cpu;
    }

    public float getMem() {
        return mem;
    }

    public float getIo() {
        return io;
    }

    public float getNet() {
        return net;
    }

    public long getMsgNum() {
        return msgNum;
    }

    public long getMsgBytes() {
        return msgBytes;
    }

    public long getCntNum() {
        return cntNum;
    }

    public long getTime() {
        return time;
    }

    public int getMsgNumT() {
        return msgNumT;
    }

    public long getMsgBytesT() {
        return msgBytesT;
    }
}
