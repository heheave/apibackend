package com.sjtu.pojo;

/**
 * Created by xiaoke on 17-8-31.
 */

// An app has a ConfigEntry group
// all entry in the same group belongs to a same app
public class Group {

    private int g_id;

    private String g_app;

    private long g_time;

    public int getG_id() {
        return g_id;
    }

    public void setG_id(int g_id) {
        this.g_id = g_id;
    }

    public String getG_app() {
        return g_app;
    }

    public void setG_app(String g_app) {
        this.g_app = g_app;
    }

    public long getG_time() {
        return g_time;
    }

    public void setG_time(long g_time) {
        this.g_time = g_time;
    }
}
