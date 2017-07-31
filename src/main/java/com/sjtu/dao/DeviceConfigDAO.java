package com.sjtu.dao;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mysql.jdbc.Statement;
import com.sjtu.pojo.DAOUtil;
import com.sjtu.pojo.DeviceConfig;
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
 * Created by xiaoke on 17-6-12.
 */
public class DeviceConfigDAO {

    private static final Logger log = Logger.getLogger(DeviceConfigDAO.class);

    @Autowired
    @Qualifier("dataSource")
    private ComboPooledDataSource dataSource;

    public DeviceConfig select(DeviceConfig dconfig) {
        Connection cnn = null;
        PreparedStatement pstat = null;
        try {
            cnn = dataSource.getConnection();
            pstat = cnn.prepareStatement("select cmd, avg from deviceConfig where did = ? and (pidx = ? or pidx = -1) and used = 1 order by pidx desc");
            pstat.setString(1, dconfig.getDid());
            pstat.setInt(2, dconfig.getPidx());
            ResultSet rs = pstat.executeQuery();
            if (rs.next()) {
                dconfig.setCmd(rs.getString("cmd"));
                dconfig.setAvg(rs.getString("avg"));
                return dconfig;
            }
        } catch (SQLException e) {
            log.warn("Get connection error", e);
            return null;
        } finally {
            DAOUtil.closeCnn(cnn, pstat);
        }
        return null;
    }

    public List<DeviceConfig> selectAll(DeviceConfig dconfig) {
        Connection cnn = null;
        PreparedStatement pstat = null;
        try {
            cnn = dataSource.getConnection();
            pstat = cnn.prepareStatement("select pidx, cmd, avg from deviceConfig where did = ? and used = 1 order by pidx");
            pstat.setString(1, dconfig.getDid());
            ResultSet rs = pstat.executeQuery();
            List<DeviceConfig> res = new ArrayList<DeviceConfig>();
            while (rs.next()) {
                DeviceConfig tmpDc = new DeviceConfig();
                tmpDc.setDid(dconfig.getDid());
                tmpDc.setPidx(rs.getInt("pidx"));
                tmpDc.setCmd(rs.getString("cmd"));
                tmpDc.setAvg(rs.getString("avg"));
                res.add(tmpDc);
            }
            return res;
        } catch (SQLException e) {
            log.warn("Get connection error", e);
            return null;
        } finally {
            DAOUtil.closeCnn(cnn, pstat);
        }
    }

    public int insert(DeviceConfig dconfig) {
        Connection cnn = null;
        PreparedStatement pstat = null;
        try {
            cnn = dataSource.getConnection();
            pstat = cnn.prepareStatement("replace into deviceConfig set did = ?, pidx = ?, cmd = ?, avg = ?, used = ?",
                    Statement.RETURN_GENERATED_KEYS);
            pstat.setString(1, dconfig.getDid());
            pstat.setInt(2, dconfig.getPidx());
            pstat.setString(3, dconfig.getCmd());
            pstat.setString(4, dconfig.getAvg());
            pstat.setInt(5, dconfig.getUsed());
            pstat.executeUpdate();
            ResultSet rs = pstat.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            log.warn("Get connection error", e);
            return -1;
        } finally {
            DAOUtil.closeCnn(cnn, pstat);
        }
        return -1;
    }

    public int remove(DeviceConfig dconfig) {
        Connection cnn = null;
        PreparedStatement pstat = null;
        try {
            cnn = dataSource.getConnection();
            pstat = cnn.prepareStatement("select id from deviceConfig where did = ? and pidx = ?;");
            pstat.setString(1, dconfig.getDid());
            pstat.setInt(2, dconfig.getPidx());
            System.out.println(">>>>>>>>>>" + dconfig.getDid() + " >>> " + dconfig.getPidx());
            ResultSet rs = pstat.executeQuery();
            if (rs.next()) {
                int id = rs.getInt(1);
                pstat.close();
                pstat = cnn.prepareStatement("update deviceConfig set used = 0 where id = ? ;");
                pstat.setInt(1, id);
                if (pstat.executeUpdate() > 0) {
                    return id;
                }
            }
        } catch (SQLException e) {
            log.warn("Get connection error", e);
            return -1;
        } finally {
            DAOUtil.closeCnn(cnn, pstat);
        }
        return -1;
    }
}
