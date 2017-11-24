package com.sjtu.service;

import com.sjtu.accessor.AccessorFactory;
import com.sjtu.config.Configure;
import com.sjtu.config.V;
import com.sjtu.dao.*;
import com.sjtu.pojo.*;
import com.sjtu.zk.ZkManager;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import org.I0Itec.zkclient.ZkClient;
import org.apache.zookeeper.CreateMode;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by xiaoke on 17-9-26.
 */
public class AppService {

    @Autowired
    private AppDAO appDAO;
    @Autowired
    private AppDeviceDAO appDeviceDAO;
    @Autowired
    private AppShowDAO appShowDAO;
    @Autowired
    private PointerDAO pointerDAO;
    @Autowired
    private DeviceDAO deviceDAO;

    public String createApp(App app) {
        if(app.getAppName() == null) {
            return null;
        } else {
            App aRet = appDAO.createApp(app);
            if (aRet == null) {
                return null;
            } else {
                Configure conf = AccessorFactory.getConf();
                ZkManager zkManager = ZkManager.getInstance(conf);
                ZkClient zkClient = zkManager.getClient();
                String appRoot = conf.getStringOrElse(V.APP_ZK_ROOT_PATH);
                try {
                    zkClient.create(appRoot, "".getBytes(), CreateMode.PERSISTENT);
                } catch (Exception e) {
                    // do nothing
                }
                String appPath = appRoot + "/" + aRet.getAppName();
                try {
                    String str = new JSONObject(aRet).toString();
                    zkClient.create(appPath, str, CreateMode.PERSISTENT);
                } catch (Exception e) {
                    //e.printStackTrace();
                } finally {
                    zkManager.close();
                }
                return String.valueOf(app.getAppId());
            }
        }
    }

    public String deleteApp(App app) {
        App aRet = appDAO.deleteApp(app);
        if (aRet == null) {
            return null;
        } else {
            Configure conf = AccessorFactory.getConf();
            ZkManager zkManager = ZkManager.getInstance(conf);
            ZkClient zkClient = zkManager.getClient();
            String appRoot = conf.getStringOrElse(V.APP_ZK_ROOT_PATH);
            String appPath = appRoot + "/" + aRet.getAppName();
            try {
                zkClient.delete(appPath);
            } finally {
                zkManager.close();
            }
            return String.valueOf(app.getAppId());
        }
    }

    public App getApp(App app) {
        return appDAO.getApp(app);
    }

    public List<App> getAllApp(User user) {
        return appDAO.getAllApp(user);
    }

    public AppShowPojo getAppShowInfo(App app) {
        AppShowPojo asp = new AppShowPojo();
        App appDetail = appDAO.getApp(app);
        asp.setApp(appDetail);
        List<Device> devices = appDeviceDAO.getAppDevices(app.getAppId());
        List<Show> shows = appShowDAO.getAppShows(app);
        asp.setDevices(devices);
        asp.setShows(shows);
        return asp;
    }

    public boolean bindPointerScene(Scene scene, Pointer p, int x, int y) {
        return appDAO.bindPapp(scene, p, x, y);

    }

    public AppScene getScene(Scene scene) {
        AppScene as = new AppScene();
        appDAO.getScene(scene);
        as.setApp(appDAO.getAppByScene(scene));
        List<Pointer> sps = pointerDAO.listScene(scene);
        Map<Integer, Point> ps = appDAO.getAppScenePoint(scene);
        as.setScene(scene);
        as.setPointer(sps);
        as.setDots(ps);
        return as;
    }

    public List<Scene> getSceneList(App app) {
        appDAO.getApp(app);
        if (app.getAppName() != null) {
            List<Scene> scenes = appDAO.getSceneList(app);
            for (Scene s: scenes) {
                s.setAppname(app.getAppName());
            }
            return scenes;
        } else {
            return null;
        }
    }

    public Scene addScene(Scene s) {
        String bgurl = s.getSbgurl();
        if (bgurl == null || bgurl.isEmpty() || !bgurl.contains(".")) {
            s.setSbgurl("default.png");
        }
        return appDAO.addScene(s);
    }

    public List<AppSummary> summary(User user) {
        List<App> apps = appDAO.getAllApp(user);
        List<AppSummary> summary = new ArrayList<AppSummary>(apps.size());
        String host = V.CNT_SERVER_HOST + ":" + V.CNT_SERVER_PORT;
        for (App app: apps) {
            List<String> res = deviceDAO.getAppDcode(app);
            AppSummary as = new AppSummary();
            int total = res.size();
            int online = 0;
            for (String dc: res) {
                OkHttpClient client = new OkHttpClient();
                try {
                    Request request = new Request.Builder()
                            .url("http://" + host + "/online/" + dc).build();
                    Response response = client.newCall(request).execute();
                    JSONObject jo = new JSONObject(response.body().string());
                    boolean isOn = jo.has(dc) && jo.getBoolean(dc);
                    online += (isOn ? 1 : 0);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            as.setApp(app);
            as.setTotalDev(total);
            as.setOnlineDev(online);
            summary.add(as);
        }
        return summary;
    }

    public List<SceneSummary> summaryScene(User user, int p) {
        List<Scene> scenes = appDAO.getAllScene(user, p);
        List<SceneSummary> summary = new ArrayList<SceneSummary>(scenes.size());
        String host = V.CNT_SERVER_HOST + ":" + V.CNT_SERVER_PORT;
        for (Scene scene: scenes) {
            List<String> res = deviceDAO.getSceneDcode(scene);
            SceneSummary as = new SceneSummary();
            int total = res.size();
            int online = 0;
            for (String dc: res) {
                OkHttpClient client = new OkHttpClient();
                try {
                    Request request = new Request.Builder()
                            .url("http://" + host + "/online/" + dc).build();
                    Response response = client.newCall(request).execute();
                    JSONObject jo = new JSONObject(response.body().string());
                    boolean isOn = jo.has(dc) && jo.getBoolean(dc);
                    online += (isOn ? 1 : 0);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            as.setScene(scene);
            as.setTotalDev(total);
            as.setOnlineDev(online);
            summary.add(as);
        }
        return summary;
    }

    public int summarySceneCnt(User user) {
        int anum = appDAO.getAllSceneCnt(user);
        if (anum % 10 == 0) {
            return anum / 10;
        } else {
            return anum / 10 + 1;
        }
    }
}
