package com.sjtu.service;

import com.sjtu.dao.AppDAO;
import com.sjtu.dao.AppDeviceDAO;
import com.sjtu.pojo.App;
import com.sjtu.pojo.Device;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiaoke on 17-9-26.
 */
public class AppDeviceService {

    @Autowired
    private AppDAO appDAO;
    @Autowired
    private AppDeviceDAO appDeviceDAO;

    public String addDeviceToApp(App app, Device device) {
        int appId = device.getDappid() > 0 ? device.getDappid() :
                                            app.getAppId() > 0 ? app.getAppId() : -1;
        if (appId <= 0) {
            appDAO.getApp(app);
            appId = app.getAppId();
        }
        device.setDappid(appId);
        if(appId > 0) {
            for (int i = 0; i < device.getDportnum(); i++) {
                if (device.getDports().get(i) != null) {
                    device.getDports().get(i).setDport(i);
                }
            }
            Device dev = appDeviceDAO.addDevice(device);
            if (dev == null) {
                return null;
            } else {
                return String.valueOf(dev.getDid());
            }
        } else {
            return null;
        }
    }

    public String delDeviceFromApp(App app, Device device) {
        int appId = device.getDappid() > 0 ? device.getDappid() :
                app.getAppId() > 0 ? app.getAppId() : -1;
        if (appId <= 0) {
            appDAO.getApp(app);
            appId = app.getAppId();
        }
        device.setDappid(appId);
        Device dev = appDeviceDAO.delDevice(device);
        if (dev == null) {
            return null;
        } else {
            return String.valueOf(dev.getDid());
        }
    }

    public Device getDeviceDetail(App app, Device device) {
        if (app == null && device.getDid() <= 0) {
            return null;
        } else if (device.getDid() > 0) {
            return appDeviceDAO.getDeviceByDid(device.getDid());
        } else {
            return appDeviceDAO.getAppDevice(app, device);
        }
    }
    public List<Device> getAppDeviceById(String appId) {
        try {
            int aid = Integer.parseInt(appId);
            List<Device> res = appDeviceDAO.getAppDevices(aid);
            return res;
        } catch (Exception e) {
            return new ArrayList<Device>();
        }

    }

    public List<Device> getAppDeviceByName(String appName) {
        return appDeviceDAO.getAppDevices(appName);
    }

    public Device getDeviceFromApp(App app, Device dev) {
        return appDeviceDAO.getAppDevice(app, dev);
    }
}
