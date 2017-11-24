package com.sjtu.pojo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiaoke on 17-9-27.
 */
public class Show {

    private int sid;

    private String stype;

    private String sdesc;

    private int slen = -1;

    private String stime;

    private int sversion;

    transient private String sdids;

    transient private List<Integer> dids = new ArrayList<Integer>();

    transient private String sports;

    transient private List<Integer> ports = new ArrayList<Integer>();

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public String getStype() {
        return stype;
    }

    public void setStype(String stype) {
        this.stype = stype;
    }

    public String getSdesc() {
        return sdesc;
    }

    public void setSdesc(String sdesc) {
        this.sdesc = sdesc;
    }

    public int getSlen() {
        return slen;
    }

    public void setSlen(int slen) {
        this.slen = slen;
    }

    public String sdidStr() {
        return this.sdids;
    }

    public String sportStr() {
        return this.sports;
    }

    public List<Integer> getSdids() {
        return dids;
    }

    public List<Integer> getSports() {
        return ports;
    }

    public void setSdids(String sdids) {
        String[] didStr = sdids.split(":");
        this.slen = didStr.length;
        this.sdids = sdids;
        dids.clear();
        for (String s: didStr) {
            dids.add(Integer.parseInt(s));
        }
    }

//    public void setSdids(int[] sdids) {
//        dids.clear();
//        if (sdids == null) {
//            this.sdids = null;
//        } else {
//            String tmpStr = "";
//            for (int i = 0; i < sdids.length; i++) {
//                dids.add(sdids[i]);
//                if (i == 0) {
//                    tmpStr += sdids[i];
//                } else {
//                    tmpStr += ":" + sdids[i];
//                }
//            }
//            this.slen = sdids.length;
//            this.sdids = tmpStr;
//        }
//    }
//
//    public void setSdids(List<?> sdids) {
//        dids.clear();
//        if (sdids == null) {
//            this.sdids = null;
//        } else {
//            String tmpStr = "";
//            for (int i = 0; i < sdids.size(); i++) {
//                int did = Integer.parseInt(sdids.get(i).toString());
//                dids.add(did);
//                if (i == 0) {
//                    tmpStr += did;
//                } else {
//                    tmpStr += ":" + did;
//                }
//            }
//            this.slen = sdids.size();
//            this.sdids = tmpStr;
//        }
//    }

    public void setSports(String sports) {
        ports.clear();
        if (sports != null) {
            String[] didStr = sports.split(":");
            this.sports = sports;
            for (String s : didStr) {
                ports.add(Integer.parseInt(s));
            }
        } else {
            for (int i = 0; i < slen; i++) {
                ports.add(0);
            }
        }

    }

//    public void setSports(int[] sports) {
//        ports.clear();
//        if (sdids == null) {
//            this.sports = null;
//        } else {
//            String tmpStr = "";
//            for (int i = 0; i < sports.length; i++) {
//                ports.add(sports[i]);
//                if (i == 0) {
//                    tmpStr += sports[i];
//                } else {
//                    tmpStr += ":" + sports[i];
//                }
//            }
//            this.sports = tmpStr;
//        }
//    }
//
//    public void setSports(List<?> sports) {
//        ports.clear();
//        if (sdids == null) {
//            this.sports = null;
//        } else {
//            String tmpStr = "";
//            for (int i = 0; i < sports.size(); i++) {
//                int port = Integer.parseInt(sports.get(i).toString());
//                ports.add(port);
//                if (i == 0) {
//                    tmpStr += port;
//                } else {
//                    tmpStr += ":" + port;
//                }
//            }
//            this.sports = tmpStr;
//        }
//    }

    public String getStime() {
        return stime;
    }

    public void setStime(String stime) {
        this.stime = stime;
    }

    public int getSversion() {
        return sversion;
    }

    public void setSversion(int sversion) {
        this.sversion = sversion;
    }
}
