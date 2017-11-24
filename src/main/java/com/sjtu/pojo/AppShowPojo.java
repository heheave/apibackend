package com.sjtu.pojo;

import java.util.List;

/**
 * Created by xiaoke on 17-10-6.
 */
public class AppShowPojo {

    private App app;

    private List<Device> devices;

    private List<Show> shows;

    public App getApp() {
        return app;
    }

    public void setApp(App app) {
        this.app = app;
    }

    public List<Device> getDevices() {
        return devices;
    }

    public void setDevices(List<Device> devices) {
        this.devices = devices;
    }

    public List<Show> getShows() {
        return shows;
    }

    public void setShows(List<Show> shows) {
        this.shows = shows;
    }
}
