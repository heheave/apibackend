package com.sjtu.avgcache;

import java.io.Serializable;

/**
 * Created by xiaoke on 17-6-6.
 */
public class AvgCacheKey implements Serializable{

    //private final String did;

    //private final int pidx;

    private final String pid;

    private final String avgType;

    public AvgCacheKey(String pid, String avgType) {
        this.pid = pid;
        //this.did = did;
        //this.pidx = pidx;
        this.avgType = avgType;
    }

    public String getPid() {
        return pid;
    }

    public String getAvgType() {
        return avgType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AvgCacheKey that = (AvgCacheKey) o;

        if (pid != null ? !pid.equals(that.pid) : that.pid != null) return false;
        return avgType != null ? avgType.equals(that.avgType) : that.avgType == null;

    }

    @Override
    public int hashCode() {
        int result = pid != null ? pid.hashCode() : 0;
        result = 31 * result + (avgType != null ? avgType.hashCode() : 0);
        return result;
    }
}
