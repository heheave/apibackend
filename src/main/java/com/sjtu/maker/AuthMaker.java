package com.sjtu.maker;

import com.sjtu.pojo.Device;
import com.sjtu.pojo.User;
import org.apache.log4j.Logger;

/**
 * Created by xiaoke on 17-11-11.
 */
public class AuthMaker {

    private static final Logger log = Logger.getLogger(AuthMaker.class);

    public static boolean authMake(User fuser, User cuser, OpEnum opEnum) {
        Role role = Role.instance(fuser);
        Type<User> type = Type.getUserType(cuser);
        Operator<User> operator = Operator.getUserOperator(opEnum);
        return operator.checkAuth(role, type);
    }

    public static boolean authMake(User fuser, Device device, OpEnum opEnum) {
        Role role = Role.instance(fuser);
        Type<Device> type = Type.getDeviceType(device);
        Operator<Device> operator = Operator.getDeviceOperator(opEnum);
        return operator.checkAuth(role, type);
    }

    public static boolean authMake(String str) {
        log.info("Auth " + str + " doesn't be implemented now!");
        return true;
    }

}
