package com.sjtu.service;

import com.sjtu.config.V;
import com.sjtu.dao.DeviceDAO;
import com.sjtu.dao.RedisDAO;
import com.sjtu.dao.UserDAO;
import com.sjtu.pojo.Dcode;
import com.sjtu.pojo.ODevice;
import com.sjtu.pojo.User;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.*;

/**
 * Created by xiaoke on 17-5-30.
 */
public class DeviceService {

    @Autowired
    private RedisDAO redisDAO;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private DeviceDAO deviceDAO;

    public Map<String, Set<String>> getAllDeviceList() {
        Map<String, Set<String>> res = new HashMap<String, Set<String>>(V.DEVICE_TYPE.length);
        for (String dt: V.DEVICE_TYPE) {
            String pattern = dt + "*";
            Set<String> tmp = redisDAO.getKeys(pattern);
            res.put(dt, tmp);
        }
        return res;
    }

    public List<String> getDevice(String did) {
        if (did != null) {
            return redisDAO.getAll(did);
        } else {
            return null;
        }
    }

    public boolean pathGenDcode(User user, int num) {
        if (user.getUid() <= 0) {
            userDAO.getUser(user);
        }
        int uid = user.getUid();
        if (uid > 0) {
            List<String> strs = new ArrayList<String>(num);
            String idStr = String.format("%03d", uid);
            String randomStr = UUID.randomUUID().toString().substring(0, 3).toUpperCase();
            int begin = (int)(System.currentTimeMillis() / 10000);
            for (int i = 0; i < num; i++) {
                int nnn = begin + i;
                if (nnn > 10000) {
                    nnn %= 1000;
                }
                String nowTimeStr = String.format("%04d", nnn);
                strs.add(randomStr + idStr + nowTimeStr);
            }
            return deviceDAO.insertDcode(user, strs);
        } else {
            return false;
        }

    }

    public List<ODevice> getDcodeList(int uid, int p) {
        String host = V.CNT_SERVER_HOST + ":" + V.CNT_SERVER_PORT;
        List<Dcode> dcodes = deviceDAO.queryDcode(uid, p);
        List<ODevice> res = new ArrayList<ODevice>();
        OkHttpClient client = new OkHttpClient();
        for (Dcode dc: dcodes) {
            try {
                Request request = new Request.Builder()
                        .url("http://" + host + "/devinfo/" + dc.getDccode()).build();
                Response response = client.newCall(request).execute();
                String str = response.body().string();
                res.add(new ODevice(dc, new JSONObject(str)));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return res;
    }

    public int getDcodeCnt(int uid) {
        int cnt = deviceDAO.cntDcode(uid);
        if (cnt % 10 == 0) {
            return cnt / 10;
        } else {
            return cnt / 10 + 1;
        }
    }

    public ODevice getDcode(User user, Dcode dc) {
        String host = V.CNT_SERVER_HOST + ":" + V.CNT_SERVER_PORT;
        deviceDAO.getDcode(dc);
        dc.setDcnick(user.getUnick());
        ODevice od = null;
        OkHttpClient client = new OkHttpClient();
        try {
            Request request = new Request.Builder()
                    .url("http://" + host + "/devinfo/" + dc.getDccode()).build();
            Response response = client.newCall(request).execute();
            String str = response.body().string();
            od = new ODevice(dc, new JSONObject(str));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return od;
    }
}
