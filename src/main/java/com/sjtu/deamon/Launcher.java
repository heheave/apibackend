package com.sjtu.deamon;

import com.sjtu.accessor.AccessorFactory;
import com.sjtu.accessor.cache.cacheServer.CacheServerService;
import com.sjtu.config.Configure;
import com.sjtu.deamon.kafka.KafkaService;

import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by xiaoke on 17-5-29.
 */
public class Launcher {

    private static volatile Launcher launcher;

    private final Configure conf;

    private final KafkaService kafkaService;

    private final CacheServerService cacheServerService;

    private Launcher() {
        this.conf = AccessorFactory.getConf();
        this.kafkaService = new KafkaService(conf);
        Map<String, String> map = new HashMap<String, String>();
        map.put("deviceconf", "com.sjtu.accessor.cache.cacheHandler.DeviceEntryHandler");
        this.cacheServerService = new CacheServerService(
          new InetSocketAddress("localhost", 8888),
          "localhost", 5672, "deviceconf", map, conf
        );
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
        cacheServerService.startService();
    }

    public void stopAll() {
        kafkaService.stop();
        cacheServerService.stopService();
    }
}
