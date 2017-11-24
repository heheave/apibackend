package com.sjtu.dao;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.sjtu.pojo.App;
import com.sjtu.pojo.Dcode;
import com.sjtu.pojo.Scene;
import com.sjtu.pojo.User;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiaoke on 17-11-12.
 */
public class DeviceDAO {
    private static final Logger log = Logger.getLogger(DeviceDAO.class);

    @Autowired
    @Qualifier("dataSource")
    private ComboPooledDataSource dataSource;

    public boolean insertDcode(User user, List<String> dcodes) {
        Connection cnn = null;
        PreparedStatement pstat = null;
        try {
            cnn = dataSource.getConnection();
            pstat = cnn.prepareStatement("insert into dcode(dccode, dcuid, dctime) values(?, ?, NOW())");
            cnn.setAutoCommit(false);
            for (String dcode: dcodes) {
                pstat.setString(1, dcode);
                pstat.setInt(2, user.getUid());
                pstat.addBatch();
            }
            int[] retNum = pstat.executeBatch();
            if (retNum.length != dcodes.size()) {
                throw new SQLException("Batch uncompletly");
            } else {
                cnn.commit();
                return true;
            }
        } catch (SQLException e) {
            log.warn("Get connection error", e);
            try {
                cnn.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        } finally {
            DAOUtil.closeCnn(cnn, pstat);
        }
        return false;
    }

    public Dcode getDcode(Dcode dc) {
        Connection cnn = null;
        PreparedStatement pstat = null;
        List<Dcode> res = new ArrayList<Dcode>();
        try {
            cnn = dataSource.getConnection();
            if (dc.getDcid() > 0) {
                pstat = cnn.prepareStatement("select * from dcode where dcid = ?");
                pstat.setInt(1, dc.getDcid());
            } else if (dc.getDccode() != null){
                pstat = cnn.prepareStatement("select * from dcode where dccode = ?");
                pstat.setString(1, dc.getDccode());
            } else {
                return dc;
            }
            ResultSet rs = pstat.executeQuery();
            if (rs.next()) {
                dc.setDcid(rs.getInt("dcid"));
                dc.setDccode(rs.getString("dccode"));
                dc.setDcuid(rs.getInt("dcuid"));
                dc.setDctime(rs.getString("dctime"));
                res.add(dc);
            }
            rs.close();
        } catch (SQLException e) {
            log.warn("Get connection error", e);
        } finally {
            DAOUtil.closeCnn(cnn, pstat);
        }
        return dc;
    }

    public List<Dcode> queryDcode(int uid, int p) {
        Connection cnn = null;
        PreparedStatement pstat = null;
        List<Dcode> res = new ArrayList<Dcode>();
        try {
            cnn = dataSource.getConnection();
            if (uid > 0) {
                pstat = cnn.prepareStatement("select dcid, dccode, dcuid, dctime, unick from dcode, user where dcuid = uid and uid = ? limit ?, 10;");
                pstat.setInt(1, uid);
                pstat.setInt(2, p * 10);
            } else {
                pstat = cnn.prepareStatement("select dcid, dccode, dcuid, dctime, unick from dcode, user where dcuid = uid order by dcid limit ?, 10;");
                pstat.setInt(1, p * 10);
            }

            ResultSet rs = pstat.executeQuery();
            while (rs.next()) {
                Dcode dc = new Dcode();
                dc.setDcid(rs.getInt("dcid"));
                dc.setDccode(rs.getString("dccode"));
                dc.setDcuid(rs.getInt("dcuid"));
                dc.setDctime(rs.getString("dctime"));
                dc.setDcnick(rs.getString("unick"));
                res.add(dc);
            }
            rs.close();
        } catch (SQLException e) {
            log.warn("Get connection error", e);
        } finally {
            DAOUtil.closeCnn(cnn, pstat);
        }
        return res;
    }

    public int cntDcode(int uid) {
        Connection cnn = null;
        PreparedStatement pstat = null;
        int res = 0;
        try {
            cnn = dataSource.getConnection();
            if (uid > 0) {
                pstat = cnn.prepareStatement("select count(dcid) as cnt from dcode where dcuid = ?");
                pstat.setInt(1, uid);
            } else {
                pstat = cnn.prepareStatement("select count(dcid) as cnt from dcode;");
            }
            ResultSet rs = pstat.executeQuery();
            if (rs.next()) {
                res = rs.getInt("cnt");
            }
            rs.close();
        } catch (SQLException e) {
            log.warn("Get connection error", e);
        } finally {
            DAOUtil.closeCnn(cnn, pstat);
        }
        return res;
    }

    public void getUserDcnum(User user) {
        Connection cnn = null;
        PreparedStatement pstat = null;
        int res = 0;
        try {
            cnn = dataSource.getConnection();
            pstat = cnn.prepareStatement("select count(dcid) as cnt from dcode where dcuid = ?");
            pstat.setInt(1, user.getUid());
            ResultSet rs = pstat.executeQuery();
            if (rs.next()) {
                user.setDcNum(rs.getInt("cnt"));
            }
            rs.close();
        } catch (SQLException e) {
            log.warn("Get connection error", e);
        } finally {
            DAOUtil.closeCnn(cnn, pstat);
        }
    }

    public List<String> getAppDcode(App app) {
        Connection cnn = null;
        PreparedStatement pstat = null;
        List<String> res = new ArrayList<String>();
        try {
            cnn = dataSource.getConnection();
            pstat = cnn.prepareStatement("SELECT distinct(pdcode) as dcode FROM scene, pointer where sid = psid and said = ?;");
            pstat.setInt(1, app.getAppId());
            ResultSet rs = pstat.executeQuery();
            while (rs.next()) {
                res.add(rs.getString("dcode"));
            }
            rs.close();
        } catch (SQLException e) {
            log.warn("Get connection error", e);
        } finally {
            DAOUtil.closeCnn(cnn, pstat);
        }
        return res;
    }

    public List<String> getSceneDcode(Scene scene) {
        Connection cnn = null;
        PreparedStatement pstat = null;
        List<String> res = new ArrayList<String>();
        try {
            cnn = dataSource.getConnection();
            pstat = cnn.prepareStatement("SELECT distinct(pdcode) as dcode FROM pointer where psid = ?;");
            pstat.setInt(1, scene.getSid());
            ResultSet rs = pstat.executeQuery();
            while (rs.next()) {
                res.add(rs.getString("dcode"));
            }
            rs.close();
        } catch (SQLException e) {
            log.warn("Get connection error", e);
        } finally {
            DAOUtil.closeCnn(cnn, pstat);
        }
        return res;
    }
}
