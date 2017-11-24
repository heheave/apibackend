package com.sjtu.service;

/**
 * Created by xiaoke on 17-9-27.
 */

import com.sjtu.dao.AppDAO;
import com.sjtu.dao.AppShowDAO;
import com.sjtu.pojo.App;
import com.sjtu.pojo.Show;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by xiaoke on 17-9-26.
 */
public class AppShowService {

    @Autowired
    private AppDAO appDAO;
    @Autowired
    private AppShowDAO appShowDAO;

    public String addShowToApp(App app, Show show) {
        if (app.getAppId() <= 0) {
            appDAO.getApp(app);
        }
        Show sRet = appShowDAO.add(app, show);
        if (sRet == null) {
            return null;
        } else {
            return String.valueOf(sRet.getSid());
        }
    }

    public String delShowFromApp(App app, Show show) {
        Show sRet = appShowDAO.remove(app, show);
        if (sRet == null) {
            return null;
        } else {
            return String.valueOf(sRet.getSid());
        }
    }

    public List<Show> getAppShows(App app) {
        return appShowDAO.getAppShows(app);

    }
}

