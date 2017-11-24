package com.sjtu.pojo;

/**
 * Created by xiaoke on 17-11-22.
 */
public class AppSummary {

    private App app;

    private int totalDev;

    private int onlineDev;

    public int getMsgNum() {
        return msgNum;
    }

    public void setMsgNum(int msgNum) {
        this.msgNum = msgNum;
    }

    public App getApp() {
        return app;
    }

    public void setApp(App app) {
        this.app = app;
    }

    public int getTotalDev() {
        return totalDev;
    }

    public void setTotalDev(int totalDev) {
        this.totalDev = totalDev;
    }

    public int getOnlineDev() {
        return onlineDev;
    }

    public void setOnlineDev(int onlineDev) {
        this.onlineDev = onlineDev;
    }

    private int msgNum;

}
