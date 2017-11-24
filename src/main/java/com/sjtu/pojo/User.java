package com.sjtu.pojo;

/**
 * Created by xiaoke on 17-11-10.
 */
public class User {

    private int uid = -1;

    private int ubyid = 0;

    private String unick;

    private String uname;

    private String upasswd;

    private int ulevel;

    private String udesc;

    private String utime;

    private String ucom;

    private String uphone;

    private String uemail;

    private int dcNum;

    private String allAppName;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getUbyid() {
        return ubyid;
    }

    public void setUbyid(int ubyid) {
        this.ubyid = ubyid;
    }

    public String getUnick() {
        return unick;
    }

    public void setUnick(String unick) {
        this.unick = unick;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getUpasswd() {
        return upasswd;
    }

    public void setUpasswd(String upasswd) {
        this.upasswd = upasswd;
    }

    public int getUlevel() {
        return ulevel;
    }

    public void setUlevel(int ulevel) {
        this.ulevel = ulevel;
    }

    public String getUdesc() {
        return udesc;
    }

    public void setUdesc(String udesc) {
        this.udesc = udesc;
    }

    public String getUtime() {
        return utime;
    }

    public void setUtime(String utime) {
        this.utime = utime;
    }

    public String getUcom() {
        return ucom;
    }

    public void setUcom(String ucom) {
        this.ucom = ucom;
    }

    public String getUphone() {
        return uphone;
    }

    public void setUphone(String uphone) {
        this.uphone = uphone;
    }

    public String getUemail() {
        return uemail;
    }

    public void setUemail(String uemail) {
        this.uemail = uemail;
    }

    public int getDcNum() {
        return dcNum;
    }

    public void setDcNum(int dcNum) {
        this.dcNum = dcNum;
    }

    public String getAllAppName() {
        return allAppName;
    }

    public void setAllAppName(String allAppName) {
        this.allAppName = allAppName;
    }
}
