package com.sjtu.controller;

import com.sjtu.config.PageIndex;
import com.sjtu.config.SessionKey;
import com.sjtu.maker.AuthMaker;
import com.sjtu.pojo.*;
import com.sjtu.service.AppService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xiaoke on 17-9-26.
 */

// used for app
@Controller
@RequestMapping("/app")
public class AppController {
    private static  final Logger log = Logger.getLogger(AppController.class);

    @Autowired
    private AppService appService;

    @RequestMapping("/list")
    public ModelAndView listApp(HttpSession hs) {
        User user = (User)hs.getAttribute(SessionKey.USER);
        if (AuthMaker.authMake("List app")) {
            List<App> res = appService.getAllApp(user);
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("alist", res);
            return new ModelAndView("applist", map);
        } else {
            return null;
        }
    }

    @ResponseBody
    @RequestMapping("/getAppsJson")
    public List<App> listAppJson(HttpSession hs) {
        User user = (User)hs.getAttribute(SessionKey.USER);
        if (AuthMaker.authMake("List app")) {
            return appService.getAllApp(user);
        } else {
            return null;
        }
    }

    @RequestMapping("/addApp")
    public String createGroup(HttpSession hs, App app) {
        User user = (User)hs.getAttribute("id");
        if (AuthMaker.authMake("Create user")) {
            app.setAbyuid(user.getUid());
            String res = appService.createApp(app);
            RetStat rs = RetStat.apply(res);
            if (rs.isSucc()) {
                return "redirect:/app/list";
            } else {
                return "opFailed";
            }
        } else {
            return PageIndex.UNAUTH;
        }
    }

    @ResponseBody
    @RequestMapping("/getApp")
    public App getGroup(App app) {
        App retApp = appService.getApp(app);
        return retApp;
    }

    @RequestMapping("/removeApp")
    public String deleteGroup(App app) {
        String res = appService.deleteApp(app);
        RetStat rs = RetStat.apply(res);
        if (rs.isSucc()) {
            return "redirect:/app/list";
        } else {
            return "opFailed";
        }
    }

    @ResponseBody
    @RequestMapping("/getAppDetailInfo")
    public AppShowPojo getAppShow(App app) {
        log.info("Query Once");
        return appService.getAppShowInfo(app);
    }

    @ResponseBody
    @RequestMapping("/pm")
    public boolean pointerModify(HttpSession hs, Scene scene, Pointer p, int x, int y) {
        User user = (User)hs.getAttribute(SessionKey.USER);
        if (AuthMaker.authMake("Bind Pointer with App")) {
            return appService.bindPointerScene(scene, p, x, y);
        } else {
            return false;
        }
    }

    @RequestMapping("/scene-add")
    public String sceneAdd(HttpSession hs, Scene s) {
        User user = (User)hs.getAttribute(SessionKey.USER);
        if (AuthMaker.authMake("Scene add access") || s.getSaid() <= 0) {
            s.setSbyuid(user.getUid());
            Scene sRet = appService.addScene(s);
            if (sRet.getSid() > 0) {
                return "redirect:/page/forward?page=sceneAdd&appId=" + s.getSaid();
            } else {
                return PageIndex.FAILURE;
            }
        } else {
            return PageIndex.UNAUTH;
        }
    }

    @ResponseBody
    @RequestMapping("/scene-list")
    public List<Scene> sceneList(HttpSession hs, App app) {
        User user = (User)hs.getAttribute(SessionKey.USER);
        if (AuthMaker.authMake("Scene list access")) {
            return appService.getSceneList(app);
        } else {
            return null;
        }
    }

    @ResponseBody
    @RequestMapping("/scene")
    public AppScene pointerModify(HttpSession hs, Scene scene) {
        User user = (User)hs.getAttribute(SessionKey.USER);
        if (AuthMaker.authMake("Scene info access")) {
            return appService.getScene(scene);
        } else {
            return null;
        }
    }


    @ResponseBody
    @RequestMapping("/summary")
    public List<AppSummary> summary(HttpSession hs) {
        User user = (User)hs.getAttribute(SessionKey.USER);
        if (AuthMaker.authMake("App summary access")) {
            return appService.summary(user);
        } else {
            return null;
        }
    }

    @ResponseBody
    @RequestMapping("/summary-scene-cnt")
    public int summaryCnt(HttpSession hs) {
        User user = (User)hs.getAttribute(SessionKey.USER);
        if (AuthMaker.authMake("App summary access")) {
            return appService.summarySceneCnt(user);
        } else {
            return 0;
        }
    }

    @ResponseBody
    @RequestMapping("/summary-scene")
    public List<SceneSummary> summary(HttpSession hs, String page) {
        User user = (User)hs.getAttribute(SessionKey.USER);
        if (AuthMaker.authMake("App summary access")) {
            int p = 0;
            try {
                p = Integer.parseInt(page);
            } catch (Exception e){}
            return appService.summaryScene(user, p);
        } else {
            return null;
        }
    }

}
