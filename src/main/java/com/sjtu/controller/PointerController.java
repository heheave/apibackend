package com.sjtu.controller;

import com.sjtu.config.PageIndex;
import com.sjtu.config.SessionKey;
import com.sjtu.maker.AuthMaker;
import com.sjtu.pojo.Pointer;
import com.sjtu.pojo.User;
import com.sjtu.service.PointerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by xiaoke on 17-11-15.
 */
@Controller
@RequestMapping("/pointer")
public class PointerController {


    @Autowired
    private PointerService pointerService;

    @RequestMapping("/create")
    public String createPointer(HttpSession hs, Pointer pointer) {
        User user = (User)hs.getAttribute(SessionKey.USER);
        if (AuthMaker.authMake("Create Pointer")) {
            pointer.setPuid(user.getUid());
            Pointer p = pointerService.createPointer(pointer);
            if (p != null) {
                return "redirect:/page/forward?page=pointerList";
            } else {
                return PageIndex.FAILURE;
            }
        } else {
            return PageIndex.UNAUTH;
        }
    }

    @ResponseBody
    @RequestMapping("/get")
    public Pointer getPointer(HttpSession hs, Pointer pointer) {
        User user = (User)hs.getAttribute(SessionKey.USER);
        if (AuthMaker.authMake("Create Pointer")) {
            return pointerService.getPointer(pointer);
        } else {
            return null;
        }
    }

    @ResponseBody
    @RequestMapping("/list")
    public List<Pointer> getPointer(HttpSession hs, String page) {
        User user = (User)hs.getAttribute(SessionKey.USER);
        if (AuthMaker.authMake("Create Pointer")) {
            int p = 0;
            if (page != null) {
                try {
                    p = Integer.parseInt(page);
                } catch (Exception e) {
                    p = 0;
                }
            }
            return pointerService.listPointer(user, p);
        } else {
            return null;
        }
    }

    @ResponseBody
    @RequestMapping("/cnt")
    public int cntPointer(HttpSession hs) {
        User user = (User)hs.getAttribute(SessionKey.USER);
        if (AuthMaker.authMake("Cnt Pointer")) {
            //int uid = user.getUlevel() == 0 ? -1 : user.getUid();
            return pointerService.cntPointer(user);
        } else {
            return 0;
        }
    }
}
