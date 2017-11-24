package com.sjtu.service;

import com.sjtu.dao.AppGroupDAO;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by xiaoke on 17-8-31.
 */
public class AppGroupService {

    @Autowired
    private AppGroupDAO appGroupDAO;

    public String createGroup(String appName) {
        return appGroupDAO.createGroup(appName);
    }

    public String deleteGroup(String appName) {
        if (appGroupDAO.deleteGroup(appName) > 0) {
            return "SUCCESS";
        } else {
            return null;
        }
    }
}
