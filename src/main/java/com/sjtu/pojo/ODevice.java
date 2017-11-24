package com.sjtu.pojo;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by xiaoke on 17-11-13.
 */
public class ODevice {

    private final Dcode dcode;

    private final Map<String, Object> detail;

    public ODevice(Dcode dcode, JSONObject detail) {
        this.dcode = dcode;
        this.detail = fromJSONObject(detail);
    }

    private static final Map<String, Object> fromJSONObject(JSONObject jo) {
        Map<String, Object> res = new HashMap<String, Object>();
        for(Object key : jo.keySet()) {
            String keyStr = key.toString();
            Object value = jo.get(keyStr);
            if (value instanceof JSONObject) {
                Map<String, Object> map = fromJSONObject((JSONObject)value);
                res.put(keyStr, map);
            } else if (value.getClass().isArray()) {
                continue;
            } else {
                res.put(keyStr, jo.get(keyStr));
            }
        }
        return res;

    }

    public Dcode getDcode() {
        return dcode;
    }

    public Map<String, Object> getDetail() {
        return detail;
    }
}
