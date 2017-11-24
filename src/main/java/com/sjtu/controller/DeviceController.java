package com.sjtu.controller;

import com.sjtu.config.PageIndex;
import com.sjtu.config.SessionKey;
import com.sjtu.maker.AuthMaker;
import com.sjtu.pojo.Dcode;
import com.sjtu.pojo.ODevice;
import com.sjtu.pojo.User;
import com.sjtu.service.DeviceService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;


/**
 * Created by xiaoke on 17-5-26.
 */
// used for device data
@Controller
@RequestMapping("/device")
public class DeviceController {

    private static final Logger log = Logger.getLogger(DeviceController.class);

    @Autowired
    DeviceService deviceService;

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

    @RequestMapping("/gen-dcode")
    public String genDcode(HttpSession hs, User user, int num) {
        User fuser = (User)hs.getAttribute(SessionKey.USER);
        if (AuthMaker.authMake("Gen dcode")) {
            if (deviceService.pathGenDcode(user, num)) {
                return "redirect:/page/forward?page=devicelist";
            } else {
                return "redirect:/page/forward?page=devicelist";
            }
        } else {
            return PageIndex.UNAUTH;
        }
    }

    @ResponseBody
    @RequestMapping("/cnt-dcode")
    public int cntDcode(HttpSession hs) {
        User user = (User)hs.getAttribute(SessionKey.USER);
        if (AuthMaker.authMake("List Device Code")) {
            int uid = user.getUlevel() == 0 ? -1 : user.getUid();
            return deviceService.getDcodeCnt(uid);
        } else {
            return 0;
        }
    }

    @ResponseBody
    @RequestMapping("/list-dcode")
    public List<ODevice> listDcode(HttpSession hs, String page) {
        User user = (User)hs.getAttribute(SessionKey.USER);
        if (AuthMaker.authMake("List Device Code")) {
            int p = 0;
            if (page != null) {
                try {
                    p = Integer.parseInt(page);
                } catch (Exception e) {
                    p = 0;
                }
            }
            int uid = user.getUlevel() == 0 ? -1 : user.getUid();
            return deviceService.getDcodeList(uid, p);
        } else {
            return null;
        }
    }

    @ResponseBody
    @RequestMapping("/get-dcode")
    public ODevice dcCode(HttpSession hs, Dcode dcode) {
        User user = (User)hs.getAttribute(SessionKey.USER);
        if (AuthMaker.authMake("List Device Code")) {
            return deviceService.getDcode(user, dcode);
        } else {
            return null;
        }
    }
}
