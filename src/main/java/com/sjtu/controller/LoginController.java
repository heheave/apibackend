package com.sjtu.controller;

import com.sjtu.pojo.User;
import com.sjtu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

/**
 * Created by xiaoke on 17-11-11.
 */
@Controller
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private UserService userService;

    @RequestMapping("/postLogin")
    public String login(HttpSession hs, User user) {
        User ru = userService.login(user);
        if (ru.getUid() > 0) {
            hs.setAttribute("id", user);
            if (ru.getUlevel() == 0) {
                return "redirect:/page/forward?page=admin";
            } else if (ru.getUlevel() == 1) {
                return "redirect:/page/forward?page=manager";
            } else {
                return "redirect:/page/forward?page=user";
            }
        } else {
            return "F";
        }
    }

    @RequestMapping("/page")
    public String login() {
        return "login";
    }

    @RequestMapping("/logout")
    public String logout(HttpSession hs) {
        hs.removeAttribute("id");
        return "redirect:/login/page";
    }
}
