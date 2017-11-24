package com.sjtu.controller;

import com.sjtu.pojo.ConfigEntry;
import com.sjtu.pojo.RetStat;
import com.sjtu.service.ConfigEntryService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by xiaoke on 17-8-31.
 */
@Controller
@RequestMapping("/config")
public class ConfigEntryController {

    private static  final Logger log = Logger.getLogger(ConfigEntryController.class);

    @Autowired
    private ConfigEntryService configEntryService;

    @RequestMapping("/addEntry")
    public String addEntry(ConfigEntry ce) {
        String retStr = configEntryService.addConfigEntry(ce);
        RetStat rs = RetStat.apply(retStr);
        if (rs.isSucc()) {
            return "redirect:/page/device?appName=" + ce.getCaname() + "&dmark=" + ce.getCdmark();
        } else {
            return "opFailed";
        }
    }

    @RequestMapping("/removeEntry")
    public String removeEntry(ConfigEntry ce) {
        RetStat rs = null;
        if (ce.getCid() < 0) {
            rs = RetStat.FAILED;
        } else {
            String retStr = configEntryService.upAppConfigEntry(ce, true);
            rs = RetStat.apply(retStr);
        }
        if (rs.isSucc()) {
            return "redirect:/page/device?appName=" + ce.getCaname() + "&dmark=" + ce.getCdmark();
        } else {
            return "opFailed";
        }
    }

    @RequestMapping("/updateEntry")
    public String updateEntry(ConfigEntry ce) {
        RetStat rs = null;
        if (ce.getCid() < 0) {
            rs = RetStat.FAILED;
        } else {
            ce.setCcmd(ce.getCcmd() == null ? null : ce.getCcmd().toUpperCase());
            ce.setCavg(ce.getCavg() == null ? null : ce.getCavg().toUpperCase());
            String retStr = configEntryService.upAppConfigEntry(ce, false);
            rs = RetStat.apply(retStr);
        }
        if (rs.isSucc()) {
            return "redirect:/page/device?appName=" + ce.getCaname() + "&dmark=" + ce.getCdmark();
        } else {
            return "opFailed";
        }
    }

    @ResponseBody
    @RequestMapping("/getEntry")
    public ConfigEntry getEntry(ConfigEntry ce) {
        return configEntryService.getConfigEntry(ce);
    }
}
