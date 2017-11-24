package com.sjtu.pojo;

/**
 * Created by xiaoke on 17-11-23.
 */
public class AV {

    private String time;

    private int num;

    private double sum;



    public int getNum() {
        return num;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public double getSum() {
        return sum;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }

    public double getAvg() {
        return num == 0 ? 0 : sum / num;
    }

}
