package com.sjtu.dao;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.sjtu.pojo.ConfigEntry;
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
 * Created by xiaoke on 17-8-31.
 */
public class ConfigEntryDAO {

    private static final Logger log = Logger.getLogger(ConfigEntryDAO.class);

    @Autowired
    @Qualifier("dataSource")
    private ComboPooledDataSource dataSource;

    public ConfigEntry addAppConfigEntry(ConfigEntry ce) {
        Connection cnn = null;
        PreparedStatement pstat = null;
        try {
            cnn = dataSource.getConnection();
            pstat = cnn.prepareStatement("insert into c_config(caname, cdmark, cpidx, ccmd, cavg, ctime) values(?, ?, ?, ?, ?, NOW())");
            pstat.setString(1, ce.getCaname());
            pstat.setString(2, ce.getCdmark());
            pstat.setInt(3, ce.getCpidx());
            pstat.setString(4, ce.getCcmd());
            pstat.setString(5, ce.getCavg());
            int chgCnt = pstat.executeUpdate();
            if (chgCnt > 0) {
                pstat = cnn.prepareStatement("SELECT LAST_INSERT_ID() as id from c_config;");
                ResultSet rs = pstat.executeQuery();
                if (rs.next()) {
                    ce.setCid(rs.getInt("id"));
                }
                rs.close();
                ce.setCused(1);
                return ce;
            }
        } catch (SQLException e) {
            log.warn("Get connection error", e);
        } finally {
            DAOUtil.closeCnn(cnn, pstat);
        }
        return null;
    }

    public ConfigEntry rmAppConfigEntry(ConfigEntry ce) {
        Connection cnn = null;
        PreparedStatement pstat = null;
        try {
            cnn = dataSource.getConnection();
            if (ce.getCid() > 0) {
                pstat = cnn.prepareStatement("update c_config set cused = 0 where cid = ?");
                pstat.setInt(1, ce.getCid());
            } else {
                pstat = cnn.prepareStatement("select cid from c_config where caname = ? and cdmark = ? and cpidx = ?");
                pstat.setString(1, ce.getCaname());
                pstat.setString(2, ce.getCdmark());
                pstat.setInt(3, ce.getCpidx());
                ResultSet rs = pstat.executeQuery();
                if (rs.next()) {
                    ce.setCid(rs.getInt("cid"));
                }
                pstat = cnn.prepareStatement("update c_config set cused = 0 where cid = ?");
                pstat.setInt(1, ce.getCid());
            }
            int chgCnt = pstat.executeUpdate();
            if (chgCnt > 0) {
                return ce;
            }
        } catch (SQLException e) {
            log.warn("Get connection error", e);
        } finally {
            DAOUtil.closeCnn(cnn, pstat);
        }
        return null;
    }

    public ConfigEntry upAppConfigEntry(ConfigEntry ce) {
        Connection cnn = null;
        PreparedStatement pstat = null;
        try {
            cnn = dataSource.getConnection();
            if (ce.getCid() > 0) {
                pstat = cnn.prepareStatement("update c_config set ccmd = ?, cavg = ? where cid = ?");
                pstat.setString(1, ce.getCcmd());
                pstat.setString(2, ce.getCavg());
                pstat.setInt(3, ce.getCid());
            } else {
                pstat = cnn.prepareStatement("select cid from c_config where caname = ? and cdmark = ? and cpidx = ?");
                pstat.setString(1, ce.getCaname());
                pstat.setString(2, ce.getCdmark());
                pstat.setInt(3, ce.getCpidx());
                ResultSet rs = pstat.executeQuery();
                if (rs.next()) {
                    ce.setCid(rs.getInt("cid"));
                }
                pstat = cnn.prepareStatement("update c_config set ccmd = ?, cavg = ? where cid = ?");
                pstat.setString(1, ce.getCcmd());
                pstat.setString(2, ce.getCavg());
                pstat.setInt(3, ce.getCid());
            }
            int chgCnt = pstat.executeUpdate();
            if (chgCnt > 0) {
                return ce;
            }
        } catch (SQLException e) {
            log.warn("Get connection error", e);
        } finally {
            DAOUtil.closeCnn(cnn, pstat);
        }
        return null;
    }

    public List<ConfigEntry> queryAllConfigEntryByAppDev(String appName, String dmark, int pidx) {
        Connection cnn = null;
        PreparedStatement pstat = null;
        List<ConfigEntry> result = new ArrayList<ConfigEntry>();
        try {
            cnn = dataSource.getConnection();
            pstat = cnn.prepareStatement("select cid, caname, cdmark, cpidx, ccmd, cavg, ctime, cused from c_config where cused = 1 and caname = ? and cdmark = ? and cpidx = ?");
            pstat.setString(1, appName);
            pstat.setString(2, dmark);
            pstat.setInt(3, pidx);
            ResultSet rs = pstat.executeQuery();
            if (rs.next()) {
                String avgType = rs.getString("cavg");
                if (avgType != null) {
                    int cid = rs.getInt("cid");
                    String caname = rs.getString("caname");
                    String cdmark = rs.getString("cdmark");
                    int cpidx = rs.getInt("cpidx");
                    String ccmd = rs.getString("ccmd");
                    String ctime = rs.getString("ctime");
                    int cused = rs.getInt("cused");
                    String[] avgs = avgType.split(":");
                    for (String avg: avgs) {
                        ConfigEntry ce = new ConfigEntry();
                        ce.setCavg(avg);
                        ce.setCid(cid);
                        ce.setCaname(caname);
                        ce.setCdmark(cdmark);
                        ce.setCpidx(cpidx);
                        ce.setCcmd(ccmd);
                        ce.setCtime(ctime);
                        ce.setCused(cused);
                        result.add(ce);
                    }
                }
            }
        } catch (SQLException e) {
            log.warn("Get connection error", e);
        } finally {
            DAOUtil.closeCnn(cnn, pstat);
        }
        return result;
    }

    public ConfigEntry queryAllConfigEntryByAppDev0(String appName, String dmark, int pidx) {
        Connection cnn = null;
        PreparedStatement pstat = null;
        try {
            cnn = dataSource.getConnection();
            pstat = cnn.prepareStatement("select cid, caname, cdmark, cpidx, ccmd, cavg, ctime, cused from c_config where cused = 1 and caname = ? and cdmark = ? and cpidx = ?");
            pstat.setString(1, appName);
            pstat.setString(2, dmark);
            pstat.setInt(3, pidx);
            ResultSet rs = pstat.executeQuery();
            if (rs.next()) {
                ConfigEntry ce = new ConfigEntry();
                int cid = rs.getInt("cid");
                String caname = rs.getString("caname");
                String cdmark = rs.getString("cdmark");
                int cpidx = rs.getInt("cpidx");
                String ccmd = rs.getString("ccmd");
                String avgType = rs.getString("cavg");
                String ctime = rs.getString("ctime");
                int cused = rs.getInt("cused");
                ce.setCavg(avgType);
                ce.setCid(cid);
                ce.setCaname(caname);
                ce.setCdmark(cdmark);
                ce.setCpidx(cpidx);
                ce.setCcmd(ccmd);
                ce.setCtime(ctime);
                ce.setCused(cused);
                return ce;
            }
        } catch (SQLException e) {
            log.warn("Get connection error", e);
        } finally {
            DAOUtil.closeCnn(cnn, pstat);
        }
        return null;
    }

}
