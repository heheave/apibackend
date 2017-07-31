package com.sjtu.deamon;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.ContextStoppedEvent;

/**
 * Created by xiaoke on 17-5-30.
 */
public class LauncherStop implements ApplicationListener<ContextStoppedEvent> {

    private static final Logger log = Logger.getLogger(LauncherStart.class);

    public void onApplicationEvent(ContextStoppedEvent contextStoppedEvent) {
        if (contextStoppedEvent.getApplicationContext().getParent() == null) {
            Launcher launcher = Launcher.instance();
            if (launcher != null) {
                launcher.stopAll();
            } else {
                log.warn("Stop launcher instance error because instance is null");
            }
        }
    }
}
