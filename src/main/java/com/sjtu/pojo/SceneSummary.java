package com.sjtu.pojo;

/**
 * Created by xiaoke on 17-11-22.
 */
public class SceneSummary {

    private Scene scene;

    private int totalDev;

    private int onlineDev;

    private int msgNum;

    public Scene getScene() {
        return scene;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
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

    public int getMsgNum() {
        return msgNum;
    }

    public void setMsgNum(int msgNum) {
        this.msgNum = msgNum;
    }
}
