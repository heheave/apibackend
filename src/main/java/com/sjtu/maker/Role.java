package com.sjtu.maker;

import com.sjtu.pojo.User;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by xiaoke on 17-11-12.
 */
public class Role {

    private static Map<Integer, Role> roleMap = new LinkedHashMap<Integer, Role>() {
        @Override
        protected boolean removeEldestEntry(Map.Entry<Integer, Role> eldest) {
            return this.size() > 16;
        }
    };

    public enum REnum {
        ADMIN, MANAGER, USER, GUEST
    }

    private final REnum level;

    private final User user;

    public Role(User user) {
        this.user = user;
        if (user == null) {
            this.level = REnum.GUEST;
        } else {
            switch (user.getUlevel()) {
                case 0:
                    this.level = REnum.ADMIN;
                    break;
                case 1:
                    this.level = REnum.MANAGER;
                    break;
                case 2:
                    this.level = REnum.USER;
                    break;
                default:
                    this.level = REnum.GUEST;
            }
        }
    }

    public REnum getLevel() {
        return level;
    }

    public User getUser() {
        return user;
    }

    public static Role instance(User user) {
        Role role = roleMap.get(user.getUid());
        if (role != null) {
            return role;
        } else {
            role = new Role(user);
            roleMap.put(user.getUid(), role);
            return role;
        }
    }

}
