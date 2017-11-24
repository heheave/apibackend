package com.sjtu.util;

import com.sjtu.pojo.RV;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by xiaoke on 17-10-6.
 */
public class StringToPojo {

    public static RV strToDeviceValue(String str) {
        RV dv = new RV();
        if (str == null) {
            return dv;
        }
        try {
            JSONObject jo = new JSONObject(str);
            //dv.setSt(JOKeyGetUtil.getString(jo,JsonField.DeviceValue.ID));
            dv.setSt(JOKeyGetUtil.getLong(jo, "st"));
            dv.setValue(JOKeyGetUtil.getDouble(jo, "value"));
            dv.setValid(true);
            return dv;
        } catch (JSONException e) {
            return dv;
        }
    }
}
