package com.sjtu.zk;

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.apache.log4j.Logger;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * Created by xiaoke on 17-6-9.
 */
public class DSLockUtil {

    private static final Logger log = Logger.getLogger(DSLockUtil.class);

    public static DSLocker unfairLock(final String url, final int timeout, final String path) {
        ZkManager zkManager = new ZkManager(url, timeout);
        final ZkClient client = zkManager.getClient();
        String createdPath = null;
        try {
            createdPath = client.create(path, "", CreateMode.EPHEMERAL);
        } catch (Exception e) {
            createdPath = null;
        }
        if (createdPath != null && path.equals(createdPath)) {
            return new DSLocker(zkManager, createdPath);
        } else {
            final CountDownLatch latch = new CountDownLatch(1);
            final IZkDataListener dl = new IZkDataListener() {

                public void handleDataChange(String s, Object o) throws Exception {
                    // do nothing
                }

                public void handleDataDeleted(String s) throws Exception {
                    if (path.equals(s)) {
                        String createdPath = null;
                        try {
                            createdPath = client.create(path, "", CreateMode.EPHEMERAL);
                        } catch (Exception e) {
                            createdPath = null;
                        }
                        if (path.equals(createdPath)) {
                            client.unsubscribeDataChanges(path, this);
                            latch.countDown();
                        }
                    }
                }
            };
            client.subscribeDataChanges(path, dl);
            client.exists(path);
            try {
                latch.await();
                return new DSLocker(zkManager, createdPath);
            } catch (InterruptedException e) {
                return new DSLocker(zkManager, null);
            }
        }
    }

    public static DSLocker fairLock(final String url, final int timeout, final String path) {
        ZkManager zkManager = new ZkManager(url, timeout);
        final ZkClient client = zkManager.getClient();
        try {
            client.create(path, "", CreateMode.PERSISTENT);
        } catch (Exception e) {
            // do nothing
        }
        final String realPath = path + "/tmpLockNode";
        String createdPath = null;
        try {
            createdPath = client.create(realPath, "", ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
        } catch (Exception e) {
            createdPath = null;
        }
        if (createdPath == null) {
            return new DSLocker(zkManager, null);
        }
        final CountDownLatch latch = new CountDownLatch(1);
        final IZkDataListener dl = new IZkDataListener() {

            public void handleDataChange(String s, Object o) throws Exception {
                // do nothing
            }

            public void handleDataDeleted(String s) throws Exception {
                log.info("Deleted Node: " + s);
                client.unsubscribeDataChanges(s, this);
                latch.countDown();
            }
        };

        List<String> childrens = client.getChildren(path);
        Collections.sort(childrens);
        String created = createdPath.substring(createdPath.lastIndexOf("/") + 1);
        if (!created.equals(childrens.get(0))) {
            int idx = childrens.indexOf(created);
            String watchPath = path + "/" + childrens.get(idx - 1);
            client.subscribeDataChanges(watchPath, dl);
            if (client.exists(watchPath)) {
                try {
                    latch.await();
                    return new DSLocker(zkManager, createdPath);
                } catch (InterruptedException e) {
                    return new DSLocker(zkManager, null);
                }
            } else {
                return new DSLocker(zkManager, createdPath);
            }
        } else {
            return new DSLocker(zkManager, createdPath);
        }
    }

}
