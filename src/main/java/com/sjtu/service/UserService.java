package com.sjtu.service;

import com.sjtu.dao.AppDAO;
import com.sjtu.dao.DeviceDAO;
import com.sjtu.dao.UserDAO;
import com.sjtu.pojo.App;
import com.sjtu.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by xiaoke on 17-11-10.
 */
public class UserService {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private DeviceDAO deviceDAO;

    @Autowired
    private AppDAO appDAO;

    private String getAllAppName(List<App> apps) {
        String allAppName = "";
        boolean isStart = true;
        for (App a: apps) {
            if (isStart) {
                allAppName += a.getAppName();
                isStart = false;
            } else {
                allAppName += "," + a.getAppName();
            }
        }
        return allAppName;
    }

    public User getUser(User user) {
        userDAO.getUser(user);
        if (user.getUid() > 0 && user.getUlevel() == 1) {
            deviceDAO.getUserDcnum(user);
            List<App> apps = appDAO.getAllApp(user);
            user.setAllAppName(getAllAppName(apps));
        } else if (user.getUid() > 0 && user.getUlevel() == 2) {
            List<App> apps = appDAO.getAppToUser(user);
            user.setAllAppName(getAllAppName(apps));
        }
        return user;
    }

    public User addUser(User user) {
        if (user.getUdesc() != null) {
            user.setUdesc(user.getUdesc().trim());
        }
        return userDAO.addUser(user);
    }

    public User deleteUser(User user) {
        return userDAO.deleteUser(user);
    }

    public User login(User user) {
        return userDAO.getUser(user);
    }

    public List<User> getUserList(User user) {
        if(user.getUid() <= 0) {
            userDAO.getUser(user);
        }
        return userDAO.getUsers(user);
    }

    public boolean addAppToUser(User user, App app) {
        return userDAO.addAppToUser(user, app);
    }
}
