package com.sjtu.service;

import com.sjtu.avgcache.AvgCacheManager;
import com.sjtu.avgcache.AvgCacheValue;
import com.sjtu.dao.*;
import com.sjtu.pojo.*;
import com.sjtu.util.StringToPojo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xiaoke on 17-10-6.
 */
public class DeviceMonitorService {

    @Autowired
    AppDAO appDAO;
    @Autowired
    AppDeviceDAO appDeviceDAO;
    @Autowired
    RedisDAO redisDAO;
    @Autowired
    ConfigEntryDAO configEntryDAO;
    @Autowired
    PointerDAO pointerDAO;


    public Map<Integer, PMonValues> getDevsRealtime(Scene scene, int len) {
        List<Pointer> ps = pointerDAO.listScene(scene);
        Map<Integer, PMonValues> res = new HashMap<Integer, PMonValues>(ps.size());
        for(Pointer p: ps) {
            //Code$Result cr = RedisKeyUtil.getKey(app, d);
            PMonValues prv = new PMonValues();
            prv.setP(p);
            List<String> strs = redisDAO.getRange("real-" + p.getPid(), -len, -1);
            List<RV> tmp = new ArrayList<RV>(strs.size());
            for (String str: strs) {
                RV rv = StringToPojo.strToDeviceValue(str);
                //dv.setDmark(d.getDmark());
                tmp.add(rv);
            }
            prv.setValues(tmp);
            res.put(p.getPid(), prv);
        }
        return res;
    }

    public PMonValues getDevRealtime(Pointer p, int len) {
        pointerDAO.get(p);
        PMonValues pvs = new PMonValues();
        pvs.setP(p);
        AV av = new AV();
        AvgCacheValue acv = AvgCacheManager.get(String.valueOf(p.getPid()), p.getPavg());
        av.setNum(acv.getSumNum());
        av.setSum(acv.getSumData());
        av.setTime(acv.getAvgMark());
        pvs.setAv(av);
        //Code$Result cr = RedisKeyUtil.getKey(app, dev);
        List<String> strs = redisDAO.getRange("real-" + p.getPid(), -len, -1);
        List<RV> rvs = new ArrayList<RV>(strs.size());
        for (String str: strs) {
            RV rv = StringToPojo.strToDeviceValue(str);
            //dv.setDmark(dev.getDmark());
            rvs.add(rv);
        }
//        List<RV> rvs = new ArrayList<RV>(10);
//        long time = System.currentTimeMillis();
//        for (int i = 0; i < 10; i++) {
//            RV rv = new RV();
//            rv.setSt(time - i * 60 * 1000);
//            rv.setValue(new Random().nextDouble() * 5);
//            //dv.setDmark(dev.getDmark());
//            rvs.add(rv);
//        }
        pvs.setValues(rvs);
        return pvs;
    }

//    public DeviceAvgValue getDevsAvg(App app, Device dev, int p) {
//        if (app.getAppName() == null) {
//            appDAO.getApp(app);
//        }
//        if (dev.getDmark() == null) {
//            appDeviceDAO.getAppDevice(app, dev);
//        }
//        List<ConfigEntry> ces = configEntryDAO.queryAllConfigEntryByAppDev(app.getAppName(), dev.getDmark(), p);
//        DeviceAvgValue res = new DeviceAvgValue(app.getAppName(), dev.getDmark(), p);
//        for (ConfigEntry ce: ces) {
//            AvgCacheValue acv = AvgCacheManager.get(ce.getCaname(), ce.getCdmark(), ce.getCpidx(), ce.getCavg());
//            if (acv == null) {
//                acv = new AvgCacheValue(AvgTimeFormat.formatTime(System.currentTimeMillis(), ce.getCavg()));
//            }
//            res.addAvgValue(acv);
//        }
//        return res;
//    }
}
