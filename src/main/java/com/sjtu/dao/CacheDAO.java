package com.sjtu.dao;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import javax.annotation.PostConstruct;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiaoke on 17-11-23.
 */
public class CacheDAO {

    private static final Logger log = Logger.getLogger(AppDAO.class);

    @Autowired
    @Qualifier("dataSource")
    private ComboPooledDataSource dataSource;

    private static CacheDAO cacheDAO;

    @PostConstruct
    public void init() {
        cacheDAO = this;
    }

    public static List<DE> queryKey(String key) {
        Connection cnn = null;
        PreparedStatement pstat = null;
        List<DE> res = new ArrayList<DE>();
        try {
            cnn = cacheDAO.dataSource.getConnection();
            pstat = cnn.prepareStatement("select puid,said,sid,pid,pcalculate,poutunit,pavg  from pointer left join scene on psid = sid where pdcode = ?");
            pstat.setString(1, key);
            ResultSet rs = pstat.executeQuery();
            while (rs.next()) {
                DE de = new DE();
                de.setUid(rs.getInt("puid"));
                de.setAid(rs.getInt("said"));
                de.setSid(rs.getInt("sid"));
                de.setPid(rs.getInt("pid"));
                de.setPcalculate(rs.getString("pcalculate"));
                de.setPoutunit(rs.getString("poutunit"));
                de.setPavg(rs.getString("pavg"));
                res.add(de);
            }
        } catch (SQLException e) {
            log.warn("Get connection error", e);
        } finally {
            DAOUtil.closeCnn(cnn, pstat);
        }
        return res;
    }
}
