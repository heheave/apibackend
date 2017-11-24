package com.sjtu.maker;

import com.sjtu.pojo.Device;
import com.sjtu.pojo.User;

/**
 * Created by xiaoke on 17-11-12.
 */
abstract public class Operator<T> {

    abstract public boolean checkAuth(Role role, Type<T> type);

    private static class UserInfoRead extends Operator<User> {
        @Override
        public boolean checkAuth(Role role, Type<User> type) {
            return true;
        }
    }

    private static class UserInfoModify extends Operator<User> {

        @Override
        public boolean checkAuth(Role role, Type<User> type) {
            return true;
        }

    }

    private static class UserInfoDelete extends Operator<User> {
        @Override
        public boolean checkAuth(Role role, Type<User> type) {
            return true;
        }

    }

    private static class UserInfoNone extends Operator<User> {
        @Override
        public boolean checkAuth(Role role, Type<User> type) {
            return true;
        }

    }

    private static class DevInfoRead extends Operator<Device> {
        @Override
        public boolean checkAuth(Role role, Type<Device> type) {
            Device d = type.getAttach();
            return true;
        }

    }

    private static class DevInfoModify extends Operator<Device> {
        @Override
        public boolean checkAuth(Role role, Type<Device> type) {
            Device d = type.getAttach();
            return true;
        }

    }

    private static class DevInfoDelete extends Operator<Device> {
        @Override
        public boolean checkAuth(Role role, Type<Device> type) {
            return true;
        }
    }

    private static class DevInfoNone extends Operator<Device> {
        @Override
        public boolean checkAuth(Role role, Type<Device> type) {
            return true;
        }
    }

    public static Operator<User> getUserOperator(OpEnum opEnum) {
        switch (opEnum) {
            case READ: {
                return new UserInfoRead();
            }
            case MODIFY: {
                return new UserInfoModify();
            }
            case DELETE: {
                return new UserInfoDelete();
            }
            default: return new UserInfoNone();
        }
    }

    public static Operator<Device> getDeviceOperator(OpEnum opEnum) {
        switch (opEnum) {
            case READ: {
                return new DevInfoRead();
            }
            case MODIFY: {
                return new DevInfoModify();
            }
            case DELETE: {
                return new DevInfoDelete();
            }
            default: return new DevInfoNone();
        }
    }
}
