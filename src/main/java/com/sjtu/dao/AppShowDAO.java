package com.sjtu.dao;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.sjtu.pojo.App;
import com.sjtu.pojo.Show;
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
 * Created by xiaoke on 17-9-26.
 */
public class AppShowDAO {

    private static final Logger log = Logger.getLogger(AppShowDAO.class);

    @Autowired
    @Qualifier("dataSource")
    private ComboPooledDataSource dataSource;

    public Show add(App app, Show show) {
        Connection cnn = null;
        PreparedStatement pstat = null;
        try {
            cnn = dataSource.getConnection();
            pstat = cnn.prepareStatement("insert into s_show(sappid, stype, sdesc, slen, sdids, sports) values(?, ?, ?, ?, ?, ?)");
            pstat.setInt(1, app.getAppId());
            pstat.setString(2, show.getStype());
            pstat.setString(3, show.getSdesc());
            pstat.setInt(4, show.getSlen());
            pstat.setString(5, show.sdidStr());
            pstat.setString(6, show.sportStr());
            int updateCnt = pstat.executeUpdate();
            if (updateCnt == 1) {
                pstat = cnn.prepareStatement("SELECT LAST_INSERT_ID() as id from s_show;");
                ResultSet rs = pstat.executeQuery();
                if (rs.next()) {
                    show.setSid(rs.getInt("id"));
                }
                rs.close();
                return show;
            }
        } catch (SQLException e) {
            log.warn("Get connection error", e);
        } finally {
            DAOUtil.closeCnn(cnn, pstat);
        }
        return null;

    }

    public Show remove(App app, Show show) {
        Connection cnn = null;
        PreparedStatement pstat = null;
        try {
            cnn = dataSource.getConnection();
            pstat = cnn.prepareStatement("delete from s_show where sid = ?");
            pstat.setInt(1, show.getSid());
            pstat.executeUpdate();
            return show;
        } catch (SQLException e) {
            log.warn("Get connection error", e);
        } finally {
            DAOUtil.closeCnn(cnn, pstat);
        }
        return null;
    }

//    public Show addShow(Show show) {
//        Connection cnn = null;
//        PreparedStatement pstat = null;
//        try {
//            cnn = dataSource.getConnection();
//            pstat = cnn.prepareStatement("insert into s_show(stype, sdesc, slen, sdids, sports) values(?, ?, ?, ?, ?)");
//            pstat.setString(1, show.getStype());
//            pstat.setString(2, show.getSdesc());
//            pstat.setInt(3, show.getSlen());
//            pstat.setString(4, show.sdidStr());
//            pstat.setString(5, show.sportStr());
//            int updateCnt = pstat.executeUpdate();
//            if (updateCnt == 1) {
//                pstat = cnn.prepareStatement("SELECT LAST_INSERT_ID() as id from d_show;");
//                ResultSet rs = pstat.executeQuery();
//                if (rs.next()) {
//                    show.setSid(rs.getInt("id"));
//                }
//                rs.close();
//                return show;
//            }
//        } catch (SQLException e) {
//            log.warn("Get connection error", e);
//        } finally {
//            DAOUtil.closeCnn(cnn, pstat);
//        }
//        return null;
//    }

//    public Show delShow(Show show) {
//        Connection cnn = null;
//        PreparedStatement pstat = null;
//        try {
//            cnn = dataSource.getConnection();
//            if (show.getSid() > 0) {
//                try {
//                    cnn.setAutoCommit(false);
//                    pstat = cnn.prepareStatement("delete from s_app_show where sid = ?");
//                    pstat.setInt(1, show.getSid());
//                    pstat.executeUpdate();
//                    pstat = cnn.prepareStatement("delete from s_show where sid = ?");
//                    pstat.setInt(1, show.getSid());
//                    pstat.executeUpdate();
//                    cnn.commit();
//                    return show;
//                } catch (SQLException e) {
//                    cnn.rollback();
//                    log.warn("Rollback", e);
//                    throw e;
//                }
//            }
//        } catch (SQLException e) {
//            log.warn("Get connection error", e);
//        } finally {
//            DAOUtil.closeCnn(cnn, pstat);
//        }
//        return null;
//    }

    public List<Show> getAppShows(App app) {
        Connection cnn = null;
        PreparedStatement pstat = null;
        List<Show> dids = new ArrayList<Show>();
        try {
            cnn = dataSource.getConnection();
            if (app.getAppId() > 0) {
                pstat = cnn.prepareStatement("select " +
                        "ss.sid, ss.stype, ss.sdesc, ss.slen, ss.sdids, ss.sports, ss.stime, ss.sversion " +
                        "from s_show ss where ss.sappid = ?");
                pstat.setInt(1, app.getAppId());
            } else {
                pstat = cnn.prepareStatement("select " +
                        "ss.sid, ss.stype, ss.sdesc, ss.slen, ss.sdids, ss.ports, ss.stime, ss.sversion " +
                        "from d_app da, s_show ss " +
                        "where da.appid = ss.sappid and da.appName = ?");
                pstat.setString(1, app.getAppName());
            }
            ResultSet rs = pstat.executeQuery();
            while (rs.next()) {
                Show s = new Show();
                s.setSid(rs.getInt("sid"));
                s.setStype(rs.getString("stype"));
                s.setSdesc(rs.getString("sdesc"));
                s.setSlen(rs.getInt("slen"));
                s.setSdids(rs.getString("sdids"));
                s.setSports(rs.getString("sports"));
                s.setStime(rs.getString("stime"));
                s.setSversion(rs.getInt("sversion"));
                dids.add(s);
            }
        } catch (SQLException e) {
            log.warn("Get connection error", e);
        } finally {
            DAOUtil.closeCnn(cnn, pstat);
        }
        return dids;
    }
}
