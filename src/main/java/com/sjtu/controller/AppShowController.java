package com.sjtu.controller;

/**
 * Created by xiaoke on 17-9-27.
 */

import com.sjtu.pojo.App;
import com.sjtu.pojo.RetStat;
import com.sjtu.pojo.Show;
import com.sjtu.service.AppShowService;
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
// used for show
@Controller
@RequestMapping("/app-show")
public class AppShowController {

    private static  final Logger log = Logger.getLogger(AppShowController.class);

    @Autowired
    private AppShowService appShowService;

    @RequestMapping("/addAppShow")
    public String addShowToApp(App app, Show show) {
        RetStat rs = null;
        if (app == null || show == null) {
            rs = RetStat.FAILED;
        } else {
            String res = appShowService.addShowToApp(app, show);
            rs =  RetStat.apply(res);
        }
        if (rs.isSucc()) {
            return "redirect:/page/app?appId=" + app.getAppId();
        } else {
            return "opFailed";
        }
    }

    @RequestMapping("/delAppShow")
    public String deleteShowFromApp(App app, Show show) {
        RetStat rs = null;
        if (app == null || show == null) {
            rs = RetStat.FAILED;
        } else {
            String res = appShowService.delShowFromApp(app, show);
            rs =  RetStat.apply(res);
        }
        if (rs.isSucc()) {
            return "redirect:/page/app?appId=" + app.getAppId();
        } else {
            return "opFailed";
        }
    }

    @ResponseBody
    @RequestMapping("/getAppShows")
    public List<Show> getAppShows(App app) {
        if (app == null) {
            return new ArrayList<Show>();
        } else {
            return appShowService.getAppShows(app);
        }
    }
}

