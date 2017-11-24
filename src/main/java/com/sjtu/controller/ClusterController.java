package com.sjtu.controller;

import com.sjtu.config.V;
import com.sjtu.maker.AuthMaker;
import com.sjtu.pojo.HBInfo;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;

/**
 * Created by xiaoke on 17-11-14.
 */
@Controller
@RequestMapping("/cluster")
public class ClusterController {

    @ResponseBody
    @RequestMapping("/statistic")
    public Map<String, List<HBInfo>> addShowToApp(HttpSession hs) {
        Map<String, List<HBInfo>> res = new HashMap<String, List<HBInfo>>();
        if(AuthMaker.authMake("See cluster statistic")) {
            String host = V.CNT_SERVER_HOST + ":" + V.CNT_SERVER_PORT;
            OkHttpClient client = new OkHttpClient();
            try {
                Request request = new Request.Builder()
                        .url("http://" + host + "/clusterInfo").build();
                Response response = client.newCall(request).execute();
                JSONObject jo = new JSONObject(response.body().string());
                for (Object obj : jo.keySet()) {
                    String key = (String)obj;
                    JSONArray ja = jo.getJSONArray(key);
                    List<HBInfo> list = new ArrayList<HBInfo>(ja.length());
                    for (int i = 0; i < ja.length(); i++) {
                        list.add(new HBInfo(ja.getJSONObject(i)));
                    }
                    Collections.sort(list, new Comparator<HBInfo>() {
                        public int compare(HBInfo o1, HBInfo o2) {
                            return (int)(o1.getTime() - o2.getTime());
                        }
                    });
                    res.put(key, list);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return res;
    }
}
