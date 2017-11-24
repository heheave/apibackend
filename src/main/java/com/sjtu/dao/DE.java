package com.sjtu.dao;

/**
 * Created by xiaoke on 17-11-23.
 */
public class DE {

    private int uid;
    private int aid;
    private int sid;
    private int pid;
    private String pcalculate;
    private String poutunit;
    private String pavg;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getAid() {
        return aid;
    }

    public void setAid(int aid) {
        this.aid = aid;
    }

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public String getPcalculate() {
        return pcalculate;
    }

    public void setPcalculate(String pcalculate) {
        this.pcalculate = pcalculate;
    }

    public String getPoutunit() {
        return poutunit;
    }

    public void setPoutunit(String poutunit) {
        this.poutunit = poutunit;
    }

    public String getPavg() {
        return pavg;
    }

    public void setPavg(String pavg) {
        this.pavg = pavg;
    }
}
