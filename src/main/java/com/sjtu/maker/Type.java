package com.sjtu.maker;

import com.sjtu.pojo.Device;
import com.sjtu.pojo.User;

/**
 * Created by xiaoke on 17-11-12.
 */
abstract public class Type<T> {

    public enum TEnum {
        DEV_INFO, DEV_LIST, USER_INTO, USER_LIST, APP_INFO, APP_LIST
    }

    protected final T attach;

    protected Type(T attach) {
        this.attach = attach;
    }

    public T getAttach() {
        return attach;
    }

    abstract protected boolean checkAuth(Role role);

    private static class UserInfo extends Type<User> {

        protected UserInfo(User user) {
            super(user);
        }

        @Override
        protected boolean checkAuth(Role role) {
            return true;
        }
    }

    public static class DevInfo extends Type<Device> {

        protected DevInfo(Device device) {
            super(device);
        }

        @Override
        protected boolean checkAuth(Role role) {
            User user = role.getUser();
            return true;
        }
    }

    public static Type<User> getUserType(User user) {
        return new UserInfo(user);
    }

    public static Type<Device> getDeviceType(Device dev) {
        return new DevInfo(dev);
    }
}
