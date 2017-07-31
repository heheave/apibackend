package com.sjtu.controller;

import com.sjtu.config.V;
import com.sjtu.pojo.DeviceConfig;
import com.sjtu.pojo.RetStat;
import com.sjtu.service.DeviceConfigService;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by xiaoke on 17-6-9.
 */
@Controller
@RequestMapping("/deviceConfig")
public class DeviceConfigRestfulController {

    private static  final Logger log = Logger.getLogger(DeviceRestfulController.class);

    @Autowired
    private DeviceConfigService deviceConfigService;

    @ResponseBody
    @RequestMapping("/getAllDconfig")
    public String getAllConfig(DeviceConfig config) {
        List<DeviceConfig> dconfigs = deviceConfigService.getEntries(config);
        if (dconfigs != null && !dconfigs.isEmpty()) {
            JSONArray ja = new JSONArray(dconfigs);
            return ja.toString();
        } else {
            return V.EMPRY_JSON_ARY;
        }
    }

    @ResponseBody
    @RequestMapping("/getDconfig")
    public String getConfig(DeviceConfig config) {
        DeviceConfig dconfig = deviceConfigService.getEntry(config);
        if (dconfig != null) {
            JSONObject jo = new JSONObject(dconfig);
            return jo.toString();
        } else {
            return V.EMPRY_JSON_OBJ;
        }
    }

    @ResponseBody
    @RequestMapping("/addDconfig")
    public RetStat addConfig(DeviceConfig config) {
        RetStat retStat;
        if (deviceConfigService.insertEntry(config)) {
            retStat = RetStat.SUCCEEDED;
        } else {
            retStat = RetStat.FAILED;
        }
        return retStat;
    }

    @ResponseBody
    @RequestMapping("/rmDconfig")
    public RetStat rmConfig(DeviceConfig config) {
        RetStat retStat;
        if (deviceConfigService.removeEntry(config)) {
            retStat = RetStat.SUCCEEDED;
        } else {
            retStat = RetStat.FAILED;
        }
        return retStat;
    }
}
