package com.sjtu.deamon.service;

/**
 * Created by xiaoke on 17-5-29.
 */
public interface IDeamonService {

    void start() throws Exception;

    void stop();

    int state();

    boolean isRunning();

    boolean isStopped();
}
