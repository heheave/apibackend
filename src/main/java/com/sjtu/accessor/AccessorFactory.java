package com.sjtu.accessor;

import com.sjtu.config.Configure;
import org.apache.log4j.Logger;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;


/**
 * Created by xiaoke on 17-5-28.
 */
public class AccessorFactory {

    private static final Logger log = Logger.getLogger(AccessorFactory.class);

    private final Configure conf;

    private static AccessorFactory launcher;

    private static RedisAccess redisAccess;

    private AccessorFactory(Configure conf) {
        this.conf = conf;
    }

    private static void checkLauncherNotNull() {
        if (launcher == null) {
            synchronized (AccessorFactory.class) {
                if (launcher == null) {
                    Configure conf = new Configure();
                    try {
                        Resource resource = new ClassPathResource("var.xml");
                        String filePath = resource.getURL().getPath();
                        conf.readFromXml(filePath);
                    } catch (Exception e) {
                        log.error("Read configure file var.xml error", e);
                        System.exit(-1);
                    }
                    launcher = new AccessorFactory(conf);
                }
            }
        }
    }

    public static Configure getConf() {
        checkLauncherNotNull();
        return launcher.conf;
    }

    public static RedisAccess getRedisAccess() {
        if (redisAccess == null) {
            synchronized (AccessorFactory.class) {
                if (redisAccess == null) {
                    checkLauncherNotNull();
                    redisAccess = new RedisAccess(launcher.conf);
                }
            }
        }
        return redisAccess;
    }

}
