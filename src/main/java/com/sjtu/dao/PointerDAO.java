package com.sjtu.dao;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.sjtu.pojo.Pointer;
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
 * Created by xiaoke on 17-11-10.
 */
public class PointerDAO {

    private static final Logger log = Logger.getLogger(PointerDAO.class);

    @Autowired
    @Qualifier("dataSource")
    private ComboPooledDataSource dataSource;

    public Pointer create(Pointer pointer) {
        Connection cnn = null;
        PreparedStatement pstat = null;
        try {
            cnn = dataSource.getConnection();
            pstat = cnn.prepareStatement("insert into pointer(pname, pdcode, pbindhash, pbindattr, pcalculate," +
                    "poutdesc, pouttype, poutunit, pdesc, puid, pavg) values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            pstat.setString(1, pointer.getPname());
            pstat.setString(2, pointer.getPdcode());
            pstat.setString(3, pointer.getPbindhash());
            pstat.setString(4, pointer.getPbindattr());
            pstat.setString(5, pointer.getPcalculate());
            pstat.setString(6, pointer.getPoutdesc());
            pstat.setString(7, pointer.getPouttype());
            pstat.setString(8, pointer.getPoutunit());
            pstat.setString(9, pointer.getPdesc());
            pstat.setInt(10, pointer.getPuid());
            pstat.setString(11, pointer.getPavg());
            int cn = pstat.executeUpdate();
            if(cn == 1) {
                pstat = cnn.prepareStatement("SELECT LAST_INSERT_ID() as id from pointer;");
                ResultSet rs = pstat.executeQuery();
                if (rs.next()) {
                    pointer.setPid(rs.getInt("id"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DAOUtil.closeCnn(cnn, pstat);
        }
        return pointer;
    }

    public Pointer get(Pointer pointer) {
        Connection cnn = null;
        PreparedStatement pstat = null;
        try {
            cnn = dataSource.getConnection();
            pstat = cnn.prepareStatement("select * from pointer left join scene on psid = sid left join app on said = aid where pid = ?");
            pstat.setInt(1, pointer.getPid());
            ResultSet rs = pstat.executeQuery();
            if (rs.next()) {
                pointer.setPid(rs.getInt("pid"));
                pointer.setPname(rs.getString("pname"));
                pointer.setPdcode(rs.getString("pdcode"));
                pointer.setPbindhash(rs.getString("pbindhash"));
                pointer.setPcalculate(rs.getString("pcalculate"));
                pointer.setPoutdesc(rs.getString("poutdesc"));
                pointer.setPouttype(rs.getString("pouttype"));
                pointer.setPoutunit(rs.getString("poutunit"));
                pointer.setPdesc(rs.getString("pdesc"));
                pointer.setPsid(rs.getInt("psid"));
                pointer.setPuid(rs.getInt("puid"));
                pointer.setPavg(rs.getString("pavg"));
                pointer.setSname(rs.getString("sname"));
                pointer.setAppname(rs.getString("appname"));
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DAOUtil.closeCnn(cnn, pstat);
        }
        return pointer;
    }

    public List<Pointer> list(User user, int p) {
        Connection cnn = null;
        PreparedStatement pstat = null;
        List<Pointer> res = new ArrayList<Pointer>();
        try {
            cnn = dataSource.getConnection();
            pstat = cnn.prepareStatement("select * from pointer left join scene on psid = sid left join app on said = aid where puid = ? limit ?, 10");
            pstat.setInt(1, user.getUid());
            pstat.setInt(2, p * 10);
            ResultSet rs = pstat.executeQuery();
            while (rs.next()) {
                Pointer pointer = new Pointer();
                pointer.setPid(rs.getInt("pid"));
                pointer.setPname(rs.getString("pname"));
                pointer.setPdcode(rs.getString("pdcode"));
                pointer.setPbindhash(rs.getString("pbindhash"));
                pointer.setPcalculate(rs.getString("pcalculate"));
                pointer.setPoutdesc(rs.getString("poutdesc"));
                pointer.setPouttype(rs.getString("pouttype"));
                pointer.setPoutunit(rs.getString("poutunit"));
                pointer.setPdesc(rs.getString("pdesc"));
                pointer.setPsid(rs.getInt("psid"));
                pointer.setPuid(rs.getInt("puid"));
                pointer.setPavg(rs.getString("pavg"));
                pointer.setSname(rs.getString("sname"));
                pointer.setAppname(rs.getString("appname"));
                res.add(pointer);
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DAOUtil.closeCnn(cnn, pstat);
        }
        return res;
    }

    public List<Pointer> listScene(Scene scene) {
        Connection cnn = null;
        PreparedStatement pstat = null;
        List<Pointer> res = new ArrayList<Pointer>();
        try {
            cnn = dataSource.getConnection();
            pstat = cnn.prepareStatement("select * from pointer where psid = ?");
            pstat.setInt(1, scene.getSid());
            ResultSet rs = pstat.executeQuery();
            while (rs.next()) {
                Pointer pointer = new Pointer();
                pointer.setPid(rs.getInt("pid"));
                pointer.setPname(rs.getString("pname"));
                pointer.setPdcode(rs.getString("pdcode"));
                pointer.setPbindhash(rs.getString("pbindhash"));
                pointer.setPcalculate(rs.getString("pcalculate"));
                pointer.setPoutdesc(rs.getString("poutdesc"));
                pointer.setPouttype(rs.getString("pouttype"));
                pointer.setPoutunit(rs.getString("poutunit"));
                pointer.setPdesc(rs.getString("pdesc"));
                pointer.setPsid(rs.getInt("psid"));
                pointer.setPuid(rs.getInt("puid"));
                pointer.setPavg(rs.getString("pavg"));
                res.add(pointer);
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DAOUtil.closeCnn(cnn, pstat);
        }
        return res;
    }

    public int cnt(User user) {
        Connection cnn = null;
        PreparedStatement pstat = null;
        int res = 0;
        try {
            cnn = dataSource.getConnection();
            pstat = cnn.prepareStatement("select count(pid) as cnt from pointer where puid = ?");
            pstat.setInt(1, user.getUid());
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
}
