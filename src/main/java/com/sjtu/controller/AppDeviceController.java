package com.sjtu.controller;

import com.sjtu.pojo.App;
import com.sjtu.pojo.Device;
import com.sjtu.pojo.RetStat;
import com.sjtu.service.AppDeviceService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiaoke on 17-9-26.
 */
// used for device
@Controller
@RequestMapping("/app-device")
public class AppDeviceController {

    private static  final Logger log = Logger.getLogger(AppDeviceController.class);

    @Autowired
    private AppDeviceService appDeviceService;

    @RequestMapping("/addAppDevice")
    public String addDeviceToApp(App app, Device dev) {
        RetStat rs = null;
        if (app == null || dev == null) {
            rs = RetStat.FAILED;
        } else {
            String res = appDeviceService.addDeviceToApp(app, dev);
            rs = RetStat.apply(res);
        }
        if (rs.isSucc()) {
            return "redirect:/page/app?appId=" + app.getAppId();
        } else {
            return "opFailed";
        }
    }

    @RequestMapping("/delAppDevice")
    public String deleteDeviceFromApp(App app, Device dev) {
        RetStat rs = null;
        if (app == null || dev == null) {
            rs = RetStat.FAILED;
        } else {
            String res = appDeviceService.delDeviceFromApp(app, dev);
            rs = RetStat.apply(res);
        }
        if (rs.isSucc()) {
            return "redirect:/page/app?appId=" + app.getAppId();
        } else {
            return "opFailed";
        }
    }

    @ResponseBody
    @RequestMapping("/getAppDevice")
    public Device getDeviceFromApp(App app, Device dev) {
        if (app == null || dev == null) {
            return null;
        } else {
            return appDeviceService.getDeviceFromApp(app, dev);
        }
    }

    @ResponseBody
    @RequestMapping("/getAppDeviceById")
    public List<Device> getAppDeviceById(String appId) {
        if (appId == null) {
            return new ArrayList<Device>();
        } else {
            return appDeviceService.getAppDeviceById(appId);
        }
    }

    @ResponseBody
    @RequestMapping("/getAppDeviceByName")
    public List<Device> getAppDeviceByName(String appName) {
        if (appName == null) {
            return new ArrayList<Device>();
        } else {
            return appDeviceService.getAppDeviceByName(appName);
        }
    }
}
