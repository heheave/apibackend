package com.sjtu.pojo;

/**
 * Created by xiaoke on 17-11-23.
 */
public class RV {

    private long st;

    private double value;

    private boolean valid = true;

    public long getSt() {
        return st;
    }

    public void setSt(long st) {
        this.st = st;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }
}
