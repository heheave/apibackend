package com.sjtu.zk;

/**
 * Created by xiaoke on 17-10-20.
 */
public class DSLocker {

    private final ZkManager zkManager;

    private final String lockPath;

    public DSLocker(ZkManager zkManager, String lockPath) {
        this.zkManager = zkManager;
        this.lockPath = lockPath;
    }

    public String getLockPath() {
        return this.lockPath;
    }

    public boolean isLocked() {
        return zkManager != null && lockPath != null;
    }

    public void unlock() {
        if (zkManager != null) {
            if (lockPath != null) {
                zkManager.getClient().delete(lockPath);
            }
            zkManager.close();
        }
    }
}
