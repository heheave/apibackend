package com.sjtu.dao;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.sjtu.pojo.App;
import com.sjtu.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiaoke on 17-11-10.
 */
public class UserDAO {

    @Autowired
    @Qualifier("dataSource")
    private ComboPooledDataSource dataSource;

    public User getUser(User user) {
        Connection cnn = null;
        PreparedStatement pstat = null;
        try {
            cnn = dataSource.getConnection();
            if (user.getUid() > 0) {
                pstat = cnn.prepareStatement("select * from user where uid = ?");
                pstat.setInt(1, user.getUid());
            } else if (user.getUnick() != null && user.getUpasswd() != null) {
                pstat = cnn.prepareStatement("select * from user where unick = ? and upasswd = ?");
                pstat.setString(1, user.getUnick());
                pstat.setString(2, user.getUpasswd());
            } else {
                return user;
            }
            ResultSet rs = pstat.executeQuery();
            if (rs.next()) {
                user.setUid(rs.getInt("uid"));
                user.setUnick(rs.getString("unick"));
                user.setUname(rs.getString("uname"));
                user.setUlevel(rs.getInt("ulevel"));
                user.setUdesc(rs.getString("udesc"));
                user.setUtime(rs.getString("utime"));
                user.setUcom(rs.getString("ucom"));
                user.setUphone(rs.getString("uphone"));
                user.setUemail(rs.getString("uemail"));
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DAOUtil.closeCnn(cnn, pstat);
        }
        return user;
    }

    public User addUser(User user) {
        Connection cnn = null;
        PreparedStatement pstat = null;
        try {
            cnn = dataSource.getConnection();
            pstat = cnn.prepareStatement("insert into user(unick, uname, upasswd, ulevel, " +
                    "udesc, ubyid, ucom, uphone, uemail, utime) values(?, ?, ?, ?, ?, ?, ?, ?, ?, NOW())");
            pstat.setString(1, user.getUnick());
            pstat.setString(2, user.getUname());
            pstat.setString(3, user.getUpasswd());
            pstat.setInt(4, user.getUlevel());
            pstat.setString(5, user.getUdesc());
            pstat.setInt(6, user.getUbyid());
            pstat.setString(7, user.getUcom());
            pstat.setString(8, user.getUphone());
            pstat.setString(9, user.getUemail());
            pstat.executeUpdate();
            pstat = cnn.prepareStatement("SELECT LAST_INSERT_ID() as id from user");
            ResultSet rs = pstat.executeQuery();
            if (rs.next()) {
                user.setUid(rs.getInt("id"));
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DAOUtil.closeCnn(cnn, pstat);
        }
        return user;
    }

    public User deleteUser(User user) {
        Connection cnn = null;
        PreparedStatement pstat = null;
        try {
            cnn = dataSource.getConnection();
            if (user.getUid() > 0) {
                pstat = cnn.prepareStatement("delete from user where uid = ?");
                pstat.setInt(1, user.getUid());
            } else if (user.getUnick() != null) {
                pstat = cnn.prepareStatement("delete from user where unick = ?");
                pstat.setString(1, user.getUnick());
            } else {
                return null;
            }
            pstat.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DAOUtil.closeCnn(cnn, pstat);
        }
        return user;
    }

    public List<User> getUsers(User user) {
        Connection cnn = null;
        PreparedStatement pstat = null;
        List<User> res = new ArrayList<User>();
        try {
            cnn = dataSource.getConnection();
            if (user.getUid() > 0) {
                pstat = cnn.prepareStatement("select * from user where ubyid = ?");
                pstat.setInt(1, user.getUid());
            } else {
                return res;
            }
            ResultSet rs = pstat.executeQuery();
            while (rs.next()) {
                User u = new User();
                u.setUid(rs.getInt("uid"));
                u.setUnick(rs.getString("unick"));
                u.setUname(rs.getString("uname"));
                u.setUlevel(rs.getInt("ulevel"));
                u.setUdesc(rs.getString("udesc"));
                u.setUtime(rs.getString("utime"));
                u.setUbyid(rs.getInt("ubyid"));
                u.setUcom(rs.getString("ucom"));
                u.setUphone(rs.getString("uphone"));
                u.setUemail(rs.getString("uemail"));
                res.add(u);
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DAOUtil.closeCnn(cnn, pstat);
        }
        return res;
    }

    public boolean addAppToUser(User user, App app) {
        Connection cnn = null;
        PreparedStatement pstat = null;
        List<User> res = new ArrayList<User>();
        try {
            if (user.getUid() > 0 && app.getAppId() > 0) {
                cnn = dataSource.getConnection();
                pstat = cnn.prepareStatement("insert into apptouser(auaid, auuid, autime) values(?, ?, NOW())");
                pstat.setInt(1, app.getAppId());
                pstat.setInt(2, user.getUid());
            } else {
                return false;
            }
            pstat.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DAOUtil.closeCnn(cnn, pstat);
        }
        return true;
    }
}
