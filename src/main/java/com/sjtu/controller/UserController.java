package com.sjtu.controller;

import com.sjtu.config.SessionKey;
import com.sjtu.maker.AuthMaker;
import com.sjtu.maker.OpEnum;
import com.sjtu.pojo.App;
import com.sjtu.pojo.User;
import com.sjtu.service.UserService;
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
 * Created by xiaoke on 17-11-10.
 */

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/addUser")
    public String addUser(User user) {
        User ru = userService.addUser(user);
        if (ru.getUid() > 0) {
            return "redirect:/user/getUsers";
        } else {
            return "F";
        }
    }

    @RequestMapping("/deleteUser")
    public String deleteUser(User user) {
        User ru = userService.deleteUser(user);
        if (ru.getUid() > 0) {
            return "S";
        } else {
            return "F";
        }
    }

    @ResponseBody
    @RequestMapping("/getUser")
    public User getUser(HttpSession hs, User user) {
        User fu = (User)hs.getAttribute(SessionKey.USER);
        if (AuthMaker.authMake(user, user, OpEnum.READ)) {
            User ru = userService.getUser(user);
            return ru;
        } else {
            return null;
        }
    }

    @ResponseBody
    @RequestMapping("/getUsersJson")
    public List<User> getUsersJson(HttpSession hs) {
        User user = (User)hs.getAttribute(SessionKey.USER);
        if (AuthMaker.authMake(user, (User)null, OpEnum.READ)) {
            User ru = userService.getUser(user);
            return userService.getUserList(user);
        } else {
            return null;
        }
    }

    @RequestMapping("/getUsers")
    public ModelAndView getTenantList(HttpSession hs) {
        User user = (User)hs.getAttribute(SessionKey.USER);
        if (AuthMaker.authMake(user, (User)null, OpEnum.READ)) {
            User ru = userService.getUser(user);
            List<User> users = userService.getUserList(user);
            Map<String, List<User>> map = new HashMap<String, List<User>>();
            map.put("ulist", users);
            if (user.getUlevel() == 0) {
                return new ModelAndView("tenantList", map);
            } else {
                return new ModelAndView("userList", map);
            }
        } else {
            return new ModelAndView("F");
        }
    }

    @RequestMapping("/addTenant")
    public String addTenant(HttpSession hs, User cuser) {
        User fuser = (User)hs.getAttribute(SessionKey.USER);
        if (AuthMaker.authMake(fuser, cuser, OpEnum.MODIFY)) {
            userService.getUser(fuser);
            cuser.setUbyid(fuser.getUid());
            cuser.setUlevel(fuser.getUlevel() + 1);
            User user = userService.addUser(cuser);
            if (user.getUid() > 0) {
                return "redirect:/user/getUsers";
            } else {
                return "F";
            }
        } else {
            return "F";
        }
    }

    @ResponseBody
    @RequestMapping("/appAdd")
    public Boolean addAppToUser(HttpSession hs, User user, App app) {
        User fuser = (User)hs.getAttribute(SessionKey.USER);
        if (AuthMaker.authMake("User add app to user")) {
            //userService.getUser(fuser);
            //cuser.setUbyid(fuser.getUid());
            //cuser.setUlevel(fuser.getUlevel() + 1);
            return userService.addAppToUser(user, app);
        } else {
            return false;
        }
    }
}
