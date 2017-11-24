package com.sjtu.avgcache;

import com.sjtu.config.V;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by xiaoke on 17-6-7.
 */
public class AvgTimeFormat {

    private static final SimpleDateFormat min = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    private static final SimpleDateFormat hour = new SimpleDateFormat("yyyy-MM-dd HH");

    private static final SimpleDateFormat day = new SimpleDateFormat("yyyy-MM-dd");

    public static String formatTime(long timestamp, String avgType) {
        String formatedTimeStr = null;
        if (V.AVG_MIN.equals(avgType)) {
            formatedTimeStr = min.format(new Date(timestamp));
        } else if (V.AVG_HOUR.equals(avgType)) {
            formatedTimeStr = hour.format(new Date(timestamp));
        } else if (V.AVG_DAY.equals(avgType)) {
            formatedTimeStr = day.format(new Date(timestamp));
        }
        return formatedTimeStr;
    }

    public static long unFormatTime(String timeStr, String avgType) {
        long timestamp = 0;
        if (timeStr != null && avgType != null) {
            try {
                if (V.AVG_MIN.equals(avgType)) {
                    timestamp = min.parse(timeStr).getTime();
                } else if (V.AVG_HOUR.equals(avgType)) {
                    timestamp = min.parse(timeStr).getTime();
                } else if (V.AVG_DAY.equals(avgType)) {
                    timestamp = min.parse(timeStr).getTime();
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return timestamp;
    }
}
