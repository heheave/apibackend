package com.sjtu.util;

import com.sjtu.config.JsonField;
import com.sjtu.pojo.App;
import com.sjtu.pojo.Device;
import org.apache.log4j.Logger;
import org.json.JSONObject;

/**
 * Created by xiaoke on 17-10-6.
 */
public class RedisKeyUtil {

    private static final Logger log = Logger.getLogger(RedisKeyUtil.class);

    public static Code$Result getKey(App app, Device dev) {
        if (app.getAppName() == null || dev.getDmark() == null) {
            return Code$Result.FAIL_NO_ERROR;
        } else {
            return Code$Result.succedWithRet(String.format("%s$%s", app.getAppName(), dev.getDmark()));
        }
    }

    private static Code$Result setNull(App app, Device dev) {
        app.setAppId(-1);
        app.setAppName(null);
        dev.setDid(-1);
        dev.setDmark(null);
        return Code$Result.FAIL_NO_ERROR;
    }

    public static Code$Result getAppAndDevice(String key, App app, Device dev) {
        if (key == null) {
            return setNull(app, dev);
        } else {
            String[] a$d = key.split("$", 2);
            if (a$d.length != 2 || a$d[0] == null || a$d[1] == null) {
                return setNull(app, dev);
            } else {
                app.setAppName(a$d[0]);
                dev.setDmark(a$d[1]);
                return Code$Result.SUCC_NO_RET;
            }
        }
    }

    public static Code$Result getKey(JSONObject jo) {
        String appName = JOKeyGetUtil.getString(jo, JsonField.DeviceValue.APP);
        String dmark = JOKeyGetUtil.getString(jo, JsonField.DeviceValue.ID);
        log.info(jo.toString());
        if (appName == null || dmark == null) {
            return Code$Result.failedWithError(new NullPointerException("AppName and Device mark should be null"));
        } else {
            return Code$Result.succedWithRet(String.format("%s$%s", appName, dmark));
        }
    }
}
