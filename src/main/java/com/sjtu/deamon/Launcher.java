package com.sjtu.deamon;

import com.sjtu.accessor.AccessorFactory;
import com.sjtu.config.Configure;
import com.sjtu.deamon.kafka.KafkaService;

/**
 * Created by xiaoke on 17-5-29.
 */
public class Launcher {

    private static volatile Launcher launcher;

    private final Configure conf;

    private final KafkaService kafkaService;

    private Launcher() {
        this.conf = AccessorFactory.getConf();
        this.kafkaService = new KafkaService(conf);
    }

    public static Launcher instance() {
        if (launcher == null) {
            synchronized (Launcher.class) {
                if (launcher == null) {
                    launcher = new Launcher();
                }
            }
        }
        return launcher;
    }

    public void startAll() throws Exception {
        kafkaService.start();
    }

    public void stopAll() {
        kafkaService.stop();
    }
}
