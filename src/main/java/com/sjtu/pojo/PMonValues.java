package com.sjtu.pojo;

import java.util.List;

/**
 * Created by xiaoke on 17-11-23.
 */
public class PMonValues {

    private Pointer p;

    private AV av;

    private List<RV> values;

    public Pointer getP() {
        return p;
    }

    public void setP(Pointer p) {
        this.p = p;
    }

    public AV getAv() {
        return av;
    }

    public void setAv(AV av) {
        this.av = av;
    }

    public List<RV> getValues() {
        return values;
    }

    public void setValues(List<RV> values) {
        this.values = values;
    }
}
