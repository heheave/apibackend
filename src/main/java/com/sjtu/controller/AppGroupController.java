package com.sjtu.controller;

import com.sjtu.pojo.RetStat;
import com.sjtu.service.AppGroupService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by xiaoke on 17-8-31.
 */

@Controller
@RequestMapping("/group")
public class AppGroupController {

    private static  final Logger log = Logger.getLogger(AppGroupController.class);

    @Autowired
    private AppGroupService appGroupService;

    @ResponseBody
    @RequestMapping("/addGroup")
    public RetStat createGroup(String appName) {
        String res = appGroupService.createGroup(appName);
        return RetStat.apply(res);
    }

    @ResponseBody
    @RequestMapping("/removeGroup")
    public RetStat deleteGroup(String appName) {
        String res = appGroupService.deleteGroup(appName);
        return RetStat.apply(res);
    }

}
