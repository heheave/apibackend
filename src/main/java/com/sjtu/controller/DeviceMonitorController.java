package com.sjtu.controller;

import com.sjtu.pojo.*;
import com.sjtu.service.DeviceMonitorService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Collections;
import java.util.Comparator;
import java.util.Map;

/**
 * Created by xiaoke on 17-10-6.
 */
@Controller
@RequestMapping("/monitor")
public class DeviceMonitorController {

    private static  final Logger log = Logger.getLogger(DeviceMonitorController.class);

    @Autowired
    private DeviceMonitorService deviceMonitorService;

    @ResponseBody
    @RequestMapping("/pointer")
    public PMonValues getDevMonitor(Pointer p, String len) {
        int l;
        if (len == null) {
            l = 0;
        } else {
            try {
                l = Integer.parseInt(len);
            } catch (Exception e) {
                l = 0;
            }
        }
        PMonValues prv = deviceMonitorService.getDevRealtime(p, l);
        if (l != 1) {
            Collections.sort(prv.getValues(), new Comparator<RV>() {
                public int compare(RV o1, RV o2) {
                    return (int) (o1.getSt() - o2.getSt());
                }
            });
        }
        return prv;
    }

    @ResponseBody
    @RequestMapping("/scene")
    public Map<Integer, PMonValues> getDevsMonitor(Scene scene, String len) {
        int l;
        if (len == null) {
            l = 0;
        } else {
            try {
                l = Integer.parseInt(len);
            } catch (Exception e) {
                l = 0;
            }
        }
        Map<Integer, PMonValues> res = deviceMonitorService.getDevsRealtime(scene, l);
        if (l != 1) {
            for (Map.Entry<Integer, PMonValues> entry : res.entrySet()) {
                Collections.sort(entry.getValue().getValues(), new Comparator<RV>() {
                    public int compare(RV o1, RV o2) {
                        return (int) (o1.getSt() - o2.getSt());
                    }
                });
            }
        }
        return res;
    }

//    @ResponseBody
//    @RequestMapping("/getAvgMonitor")
//    public DeviceAvgValue getAvgMonitor(App app, Device dev, String port) {
//        int p;
//        if (port == null) {
//            p = -1;
//        } else {
//            try {
//                p = Integer.parseInt(port);
//            } catch (Exception e) {
//                p = -1;
//            }
//        }
//        return deviceMonitorService.getDevsAvg(app, dev, p);
//    }

}
