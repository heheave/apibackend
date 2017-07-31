package com.sjtu.controller;

import com.sjtu.avgcache.AvgCacheKey;
import com.sjtu.avgcache.AvgCacheManager;
import com.sjtu.avgcache.AvgCacheValue;
import com.sjtu.config.JsonField;
import com.sjtu.config.V;
import com.sjtu.pojo.DeviceAvgValue;
import com.sjtu.service.DeviceService;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

/**
 * Created by xiaoke on 17-5-26.
 */
@Controller
@RequestMapping("/device")
public class DeviceRestfulController {

    private static  final Logger log = Logger.getLogger(DeviceRestfulController.class);

    @Autowired
    private DeviceService deviceService;

    @ResponseBody
    @RequestMapping("/getDevice")
    public String showDevice(String did) {
        log.info("Query device id: " + did);
        List<String> str = deviceService.getDevice(did);
        if (str != null) {
            Collections.sort(str, new Comparator<String>() {
                public int compare(String o1, String o2) {
                    JSONObject jo1 = new JSONObject(o1);
                    JSONObject jo2 = new JSONObject(o2);
                    return (int)(jo1.getLong(JsonField.DeviceValue.PTIMESTAMP)
                            - jo2.getLong(JsonField.DeviceValue.PTIMESTAMP));
                }
            });
            JSONArray ja = new JSONArray(str);
            return ja.toString();
        } else {
           return V.EMPRY_JSON_OBJ;
        }
    }

    @ResponseBody
    @RequestMapping("/getAvg")
    public String showAvg(String did, String port, String avg) {
        log.info("Query device id:" + did);
        try {
            int pidx = Integer.parseInt(port);
            AvgCacheValue acv = AvgCacheManager.get(did, pidx, avg);
            if (acv != null) {
                DeviceAvgValue dav = new DeviceAvgValue(new AvgCacheKey(did, pidx, avg), acv);
                JSONObject jo = new JSONObject(dav);
                return jo.toString();
            } else {
                return  V.EMPRY_JSON_OBJ;
            }
        } catch (Exception e) {
            return  V.EMPRY_JSON_OBJ;
        }

    }

    @ResponseBody
    @RequestMapping("/getAllDevice")
    public String showAllDevice() {
        log.info("Query all devices");
        Map<String, Set<String>> keys = deviceService.getAllDeviceList();
        if (keys != null) {
            JSONObject jos = new JSONObject(keys);
            return jos.toString();
        } else {
            return V.EMPRY_JSON_ARY;
        }
    }
}
