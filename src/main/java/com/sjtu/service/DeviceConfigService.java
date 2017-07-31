package com.sjtu.service;

import com.mysql.jdbc.Connection;
import com.sjtu.accessor.AccessorFactory;
import com.sjtu.config.Configure;
import com.sjtu.config.V;
import com.sjtu.dao.DeviceConfigDAO;
import com.sjtu.pojo.DeviceConfig;
import com.sjtu.zk.DsLockClient;
import org.apache.log4j.Logger;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.List;

/**
 * Created by xiaoke on 17-6-9.
 */
public class DeviceConfigService {

    private static final Logger log = Logger.getLogger(DeviceConfigService.class);

    private Configure conf = AccessorFactory.getConf();

    private DsLockClient dsLockClient = DsLockClient.instance(conf);

    @Autowired
    @Qualifier("deviceConfigDAO")
    private DeviceConfigDAO deviceConfigDAO;

    public List<DeviceConfig> getEntries(DeviceConfig deviceConfig) {
        if (deviceConfig.getDid() != null) {
            return deviceConfigDAO.selectAll(deviceConfig);
        }
        return null;
    }

    public DeviceConfig getEntry(DeviceConfig deviceConfig) {
        if (deviceConfig.getDid() != null) {
            return deviceConfigDAO.select(deviceConfig);
        }
        return null;
    }

    public boolean insertEntry(DeviceConfig dconfig) {
        if (dconfig.getDid() != null && dconfig.getCmd() != null) {
            int id = deviceConfigDAO.insert(dconfig);
            if (id > 0 && "SUCCEEDED".equals(notifySlave(id))) {
                return true;
            }
        }
        return false;
    }

    public boolean removeEntry(DeviceConfig dconfig) {
        if (dconfig.getDid() != null) {
            int id = deviceConfigDAO.remove(dconfig);
            System.out.println(id);
            if (id > 0 && "SUCCEEDED".equals(notifySlave(id))) {
                return true;
            }
        }
        return false;
    }

    private String notifySlave(int id) {
        String lockPath = conf.getStringOrElse(V.DSLOCK_ZK_PATH, "/dslockTmpPath");
        String lockedPath = dsLockClient.tryLock(lockPath);
        if (lockedPath != null) {
            try {
                try {
                    ZooKeeper zk = dsLockClient.zk();
                    String updatePath = conf.getStringOrElse(V.DSLOCK_ZK_UPDATE_PATH, "/deviceConfig/change");
                    if (zk.exists(updatePath, false) == null) {
                        zk.create(updatePath, String.valueOf(id).getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
                    } else {
                        zk.setData(updatePath, String.valueOf(id).getBytes(), -1);
                    }
                } catch (Exception e) {
                    log.warn("Set id " + id + " to path: " + lockedPath + " error");
                    return "FAILED";
                }
            } finally {
                try {
                    Thread.sleep(conf.getLongOrElse(V.DSLOCK_ZK_LOCK_MINIMUM, 3000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                dsLockClient.unlock(lockedPath);
            }
            log.warn("Locked ....................");
            return "SUCCEEDED";
        } else {
            log.warn("Unlocked ....................");
            return "FAILED";
        }
    }
}
