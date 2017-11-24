package com.sjtu.pojo;

import java.util.List;

/**
 * Created by xiaoke on 17-9-26.
 */
public class Device {

    private int did;

    private int dappid;

    private String dmark;

    private String dtype;

    private String dcompany;

    private double dlocX;

    private double dlocY;

    private String ddes;

    private int dportnum;

    private List<Port> dports;

    public int getDid() {
        return did;
    }

    public void setDid(int did) {
        this.did = did;
    }

    public int getDappid() {
        return dappid;
    }

    public void setDappid(int dappid) {
        this.dappid = dappid;
    }

    public String getDmark() {
        return dmark;
    }

    public void setDmark(String dmark) {
        this.dmark = dmark;
    }

    public String getDtype() {
        return dtype;
    }

    public void setDtype(String dtype) {
        this.dtype = dtype;
    }

    public String getDcompany() {
        return dcompany;
    }

    public void setDcompany(String dcompany) {
        this.dcompany = dcompany;
    }

    public double getDlocX() {
        return dlocX;
    }

    public void setDlocX(double dlocX) {
        this.dlocX = dlocX;
    }

    public double getDlocY() {
        return dlocY;
    }

    public void setDlocY(double dlocY) {
        this.dlocY = dlocY;
    }

    public String getDdes() {
        return ddes;
    }

    public void setDdes(String ddes) {
        this.ddes = ddes;
    }

    public int getDportnum() {
        return dportnum;
    }

    public void setDportnum(int dportnum) {
        this.dportnum = dportnum;
    }

    public List<Port> getDports() {
        return dports;
    }

    public void setDports(List<Port> dports) {
        this.dports = dports;
    }
}
