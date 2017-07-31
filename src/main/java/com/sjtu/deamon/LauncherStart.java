package com.sjtu.deamon;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

/**
 * Created by xiaoke on 17-5-30.
 */
public class LauncherStart implements ApplicationListener<ContextRefreshedEvent> {

    private static final Logger log = Logger.getLogger(LauncherStart.class);

    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        if (contextRefreshedEvent.getApplicationContext().getParent() == null) {
            Launcher launcher = Launcher.instance();
            if (launcher != null) {
                try {
                    launcher.startAll();
                } catch (Exception e) {
                    log.error("Start launcher error", e);
                    System.exit(-1);
                }
            } else {
                log.error("Get launcher instance error because instance is null");
                System.exit(-1);
            }
        }
    }
}
