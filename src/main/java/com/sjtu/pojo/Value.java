package com.sjtu.pojo;

/**
 * Created by xiaoke on 17-5-28.
 */
public class Value {

    private boolean isValid;

    private double value;

    private String unit;

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
