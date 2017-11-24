package com.sjtu.dao;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by xiaoke on 17-8-31.
 */
public class AppGroupDAO {

    private static final Logger log = Logger.getLogger(AppGroupDAO.class);

    @Autowired
    @Qualifier("dataSource")
    private ComboPooledDataSource dataSource;

    public String createGroup(String appName) {
        Connection cnn = null;
        PreparedStatement pstat = null;
        try {
            cnn = dataSource.getConnection();
            pstat = cnn.prepareStatement("select g_id from a_group where g_app = ?");
            pstat.setString(1, appName);
            ResultSet rs = pstat.executeQuery();
            if (rs.next()) {
                return rs.getString(1);
            } else {
                pstat = cnn.prepareStatement("insert into a_group(g_app, g_time) values(?, NOW())");
                pstat.setString(1, appName);
                int updateCnt = pstat.executeUpdate();
                if (updateCnt == 1) {
                    pstat = cnn.prepareStatement("select g_id from a_group where g_app = ?");
                    pstat.setString(1, appName);
                    rs = pstat.executeQuery();
                    if (rs.next()) {
                        return rs.getString(1);
                    }
                }
            }
        } catch (SQLException e) {
            log.warn("Get connection error", e);
        } finally {
            DAOUtil.closeCnn(cnn, pstat);
        }
        return null;

    }

    public int deleteGroup(String appName) {
        Connection cnn = null;
        PreparedStatement pstat = null;
        try {
            cnn = dataSource.getConnection();
            pstat = cnn.prepareStatement("delete from a_group where g_app = ?");
            pstat.setString(1, appName);
            return pstat.executeUpdate();
        } catch (SQLException e) {
            log.warn("Get connection error", e);
        } finally {
            DAOUtil.closeCnn(cnn, pstat);
        }
        return -1;
    }
}
