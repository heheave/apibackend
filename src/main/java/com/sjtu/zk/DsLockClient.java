package com.sjtu.zk;

import com.sjtu.config.Configure;
import com.sjtu.config.V;
import org.apache.log4j.Logger;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Created by xiaoke on 17-6-9.
 */
public class DsLockClient {

    private static final Logger log = Logger.getLogger(DsLockClient.class);

    private CountDownLatch latch = null;

    private ZooKeeper zk = null;

    private String host;

    private int port;

    private int timeout;

    private volatile static DsLockClient ds;

    public static DsLockClient instance(Configure conf) {
        if (ds == null) {
            synchronized (DsLockClient.class) {
                if (ds == null) {
                    String zkHost = conf.getStringOrElse(V.DSLOCK_ZK_HOST, "localhost");
                    int zkPort = conf.getIntOrElse(V.DSLOCK_ZK_PORT, 2181);
                    int zkTimeout = conf.getIntOrElse(V.DSLOCK_ZK_TIMEOUT, 4000);
                    try {
                        ds = new DsLockClient(zkHost, zkPort, zkTimeout);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return ds;
    }



    private DsLockClient(String zkHost, int zkPort, int zkTimeout) throws IOException, InterruptedException {
        this.host = zkHost;
        this.port = zkPort;
        this.timeout = zkTimeout;
        getZkClient(host, port, false);
    }

    private void getZkClient(String host, int port, boolean isRec) {
        if (zk == null) {
            synchronized (this) {
                if (zk == null) {
                    try {
                        latch = new CountDownLatch(1);
                        buildClient(host, port, isRec);
                    } catch (Exception e) {
                        log.warn("Create zk client error", e);
                    }
                }
            }
        }
    }

    private void buildClient(String h, int p, boolean isRec) throws IOException, InterruptedException, KeeperException {
        String hostUrl = h + ":" + p;
        zk = new ZooKeeper(hostUrl, timeout, new Watcher() {
            public void process(WatchedEvent watchedEvent) {
                if (watchedEvent.getState() == Event.KeeperState.SyncConnected) {
                    if (latch != null) {
                        latch.countDown();
                    }
                } else if (watchedEvent.getState() == Event.KeeperState.Expired) {
                    close();
                    getZkClient(host, port, true);
                } else {
                    log.info(watchedEvent.getState());
                }
            }
        });
        try {
            latch.await(timeout, TimeUnit.MILLISECONDS);
        } finally {
            latch = null;
        }
    }

    public ZooKeeper zk() throws InterruptedException {
        if (latch != null) {
            latch.await(timeout, TimeUnit.MILLISECONDS);
        }
        return zk;
    }

    public String tryLock(String lockPath) {
        String lockedPath = null;
        if (lockPath == null) {
            return null;
        }
        try {
            ZooKeeper zk = zk();
            Stat stat;
            while ((stat = zk.exists(lockPath, false)) == null) {
                zk.create(lockPath, lockPath.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            }
            String toLockPath = lockPath + "/lock";
            lockedPath = zk.create(toLockPath, "lock".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
            List<String> childrens = zk.getChildren(lockPath, false);
            Collections.sort(childrens, new Comparator<String>() {
                public int compare(String o1, String o2) {
                    return o1.compareTo(o2);
                }
            });
            //System.out.println("................" + childrens.size());
            int index = getIndex(lockedPath.substring(lockedPath.lastIndexOf("/") + 1), childrens);
            if (index == 0) {
                return lockedPath;
            } else {
                String index_1 = childrens.get(index - 1);
                final CountDownLatch latch1 = new CountDownLatch(1);
                zk.exists(lockPath + "/" + index_1, new Watcher() {
                    public void process(WatchedEvent watchedEvent) {
                        if (watchedEvent.getType() == Event.EventType.NodeDeleted) {
                            latch1.countDown();
                        }
                    }
                });
                latch1.await();
                return lockedPath;
            }
        } catch (Exception e) {
            if (lockedPath != null) {
                unlock(lockedPath);
            }
            return null;
        }
    }

    public void unlock(String path) {
        if (path != null) {
            try {
                zk().delete(path, -1);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void close() {
        if (latch != null) {
            latch.countDown();
        }
        latch = null;
        if (zk != null) {
            try {
                zk.close();
                zk = null;
            } catch (Exception e) {
                log.warn("Close zk client error", e);
            }
        }
    }

    private int getIndex(String path, List<String> childrens) {
        int index = 0;
        for (String child: childrens) {
            if (child.equals(path)) {
                return index;
            }
            index++;
        }
        return index;
    }
}
