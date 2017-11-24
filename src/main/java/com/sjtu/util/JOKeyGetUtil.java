package com.sjtu.util;

import org.json.JSONObject;

/**
 * Created by xiaoke on 17-10-6.
 */
public class JOKeyGetUtil {

    public static boolean getBoolean(JSONObject jo, String key) {
        return getBoolean(jo, key, false);
    }

    public static boolean getBoolean(JSONObject jo, String key, boolean dv) {
        return jo.has(key) ? jo.getBoolean(key) : dv;
    }

    public static int getInt(JSONObject jo, String key) {
        return getInt(jo, key, 0);
    }

    public static int getInt(JSONObject jo, String key, int dv) {
        return jo.has(key) ? jo.getInt(key) : dv;
    }

    public static long getLong(JSONObject jo, String key) {
        return getLong(jo, key, 0);
    }

    public static long getLong(JSONObject jo, String key, long dv) {
        return jo.has(key) ? jo.getLong(key) : dv;
    }

    public static double getDouble(JSONObject jo, String key) {
        return getDouble(jo, key, 0);
    }

    public static double getDouble(JSONObject jo, String key, double dv) {
        return jo.has(key) ? jo.getDouble(key) : dv;
    }

    public static String getString(JSONObject jo, String key) {
        return getString(jo, key, null);
    }

    public static String getString(JSONObject jo, String key, String dv) {
        return jo.has(key) ? jo.getString(key) : dv;
    }
}
