package com.sjtu.controller;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


/**
 * Created by xiaoke on 17-5-26.
 */

@Controller
@RequestMapping("/device")
public class DeviceController {
    private static final Logger log = Logger.getLogger(DeviceController.class);

    @RequestMapping("/list")
    public String list() {
        log.info("get device list");
        return "deviceList";
    }

    @RequestMapping("/detail")
    public ModelAndView detail(String did) {
        log.info("get device detail: " + did);
        return new ModelAndView("device", "did", did);
    }
}
