package com.sjtu.service;

import com.sjtu.accessor.AccessorFactory;
import com.sjtu.config.Configure;
import com.sjtu.config.V;
import com.sjtu.dao.ConfigEntryDAO;
import com.sjtu.pojo.ConfigEntry;
import com.sjtu.zk.DSLockUtil;
import com.sjtu.zk.DSLocker;
import com.sjtu.zk.ZkManager;
import org.I0Itec.zkclient.ZkClient;
import org.apache.zookeeper.CreateMode;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by xiaoke on 17-10-12.
 */
public class ConfigEntryService {

    @Autowired
    private ConfigEntryDAO configEntryDAO;


    public String addConfigEntry(ConfigEntry ce) {
        ConfigEntry ceRet = configEntryDAO.addAppConfigEntry(ce);
        if (ceRet == null) {
            return null;
        } else {
            Configure conf = AccessorFactory.getConf();
            String zkUrl = conf.getStringOrElse(V.DSLOCK_ZK_URL);
            int timeout = conf.getIntOrElse(V.DSLOCK_ZK_TIMEOUT);
            String lockPath = conf.getStringOrElse(V.DSLOCK_ZK_FAIR_PATH);
            DSLocker locker = DSLockUtil.fairLock(zkUrl, timeout, lockPath);
            if (locker.isLocked()) {
                try {
                    ZkManager zkManager = ZkManager.getInstance(conf);
                    ZkClient zkClient = zkManager.getClient();
                    String appRoot = conf.getStringOrElse(V.DCONF_ZK_ROOT_PATH);
                    try {
                        zkClient.create(appRoot, "".getBytes(), CreateMode.PERSISTENT);
                    } catch (Exception e) {
                        // do nothing
                    }
                    String appPath = appRoot + "/" + ce.getCaname();
                    try {
                        String str = new JSONObject(ceRet).toString();
                        System.out.println(str);
                        if (zkClient.exists(appPath)) {
                            zkClient.writeData(appPath, str);
                        } else {
                            zkClient.create(appPath, str, CreateMode.PERSISTENT);
                        }
                    } catch (Exception e) {
                        //e.printStackTrace();
                    } finally {
                        zkManager.close();
                    }
                    Thread.sleep(1000);
                } catch (Exception e) {
                   // do nothing
                } finally {
                    locker.unlock();
                }
                return String.valueOf(ceRet.getCid());
            } else {
                return null;
            }
        }
    }

    public String upAppConfigEntry(ConfigEntry ce, boolean isRm) {
        if (ce == null) {
            return null;
        } else {
            ConfigEntry ceRet = isRm ? configEntryDAO.rmAppConfigEntry(ce) : configEntryDAO.upAppConfigEntry(ce);
            if (ceRet == null) {
                return null;
            } else {
                Configure conf = AccessorFactory.getConf();
                String zkUrl = conf.getStringOrElse(V.DSLOCK_ZK_URL);
                int timeout = conf.getIntOrElse(V.DSLOCK_ZK_TIMEOUT);
                String lockPath = conf.getStringOrElse(V.DSLOCK_ZK_FAIR_PATH);
                DSLocker locker = DSLockUtil.fairLock(zkUrl, timeout, lockPath);
                if (locker.isLocked()) {
                    try {
                        ZkManager zkManager = ZkManager.getInstance(conf);
                        ZkClient zkClient = zkManager.getClient();
                        String appRoot = conf.getStringOrElse(V.DCONF_ZK_ROOT_PATH);
                        try {
                            zkClient.create(appRoot, "".getBytes(), CreateMode.PERSISTENT);
                        } catch (Exception e) {
                            // do nothing
                        }
                        String appPath = appRoot + "/" + ce.getCaname();
                        try {
                            String str = new JSONObject(ceRet).toString();
                            System.out.println(str);
                            if (zkClient.exists(appPath)) {
                                zkClient.writeData(appPath, str);
                            } else {
                                zkClient.create(appPath, str, CreateMode.PERSISTENT);
                            }
                        } catch (Exception e) {
                            //e.printStackTrace();
                        } finally {
                            zkManager.close();
                        }
                        Thread.sleep(1000);
                    } catch (Exception e) {
                        // do nothing
                    } finally {
                        locker.unlock();
                    }
                    return String.valueOf(ceRet.getCid());
                } else {
                    return null;
                }
            }
        }
    }

    public ConfigEntry getConfigEntry(ConfigEntry ce) {
        if (ce == null) {
            return null;
        } else {
            return configEntryDAO.queryAllConfigEntryByAppDev0(ce.getCaname(), ce.getCdmark(), ce.getCpidx());
        }
    }
}
