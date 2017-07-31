package com.sjtu.deamon.service;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by xiaoke on 17-5-29.
 */
abstract public class AbstractorDeamonService implements IDeamonService{

    protected final AtomicInteger state;

    protected AbstractorDeamonService() {
        state = new AtomicInteger(0);
    }

    public void start() throws Exception {
        if (state.compareAndSet(0, 1)) {
            launch();
        } else {
            throw new RuntimeException("Failed to start in state: " + state());
        }
    }

    public void stop() {
        if (state.compareAndSet(1, 2)) {
            shutdown();
        } else {
            throw new RuntimeException("Failed to stop in state: " + state());
        }
    }

    public int state() {
        return state.get();
    }

    public boolean isRunning() {
        return state() == 1;
    }

    public boolean isStopped() {
        return state() == 2;
    }

    abstract protected void launch() throws Exception;

    abstract protected void shutdown();
}
