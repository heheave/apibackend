package com.sjtu.pojo;

/**
 * Created by xiaoke on 17-6-12.
 */
public class RetStat {

    public static transient final RetStat SUCCEEDED = new RetStat("Succeeded");
    public static transient final RetStat FAILED = new RetStat("Failed");

    private final String stat;

    public RetStat(String stat) {
        this.stat = stat;
    }

    public String getStat() {
        return stat;
    }

    public boolean isSucc() {
        return !"Failed".equals(stat);
    }

    public boolean isFailed() {
        return !isSucc();
    }

    public static final RetStat apply(String stat) {
        if (stat == null) {
            return FAILED;
        } else {
            return new RetStat(stat);
        }
    }
}
