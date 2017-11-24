package com.sjtu.dao;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.sjtu.pojo.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xiaoke on 17-9-26.
 */
public class AppDAO {

    private static final Logger log = Logger.getLogger(AppDAO.class);

    @Autowired
    @Qualifier("dataSource")
    private ComboPooledDataSource dataSource;

    public App createApp(App app) {
        Connection cnn = null;
        PreparedStatement pstat = null;
        try {
            cnn = dataSource.getConnection();
            pstat = cnn.prepareStatement("insert into app(appname, crttime, acompany, adesc, abyuid, abgurl) values(?, NOW(), ?, ?, ?, ?)");
            pstat.setString(1, app.getAppName());
            pstat.setString(2, app.getAcompany());
            pstat.setString(3, app.getAdesc());
            pstat.setInt(4, app.getAbyuid());
            pstat.setString(5, app.getAbgurl());
            int updateCnt = pstat.executeUpdate();
            if (updateCnt == 1) {
                pstat = cnn.prepareStatement("SELECT LAST_INSERT_ID() as id from app");
                ResultSet rs = pstat.executeQuery();
                if (rs.next()) {
                    app.setAppId(rs.getInt("id"));
                    return app;
                }
                rs.close();
            }
        } catch (SQLException e) {
            log.warn("Get connection error", e);
        } finally {
            DAOUtil.closeCnn(cnn, pstat);
        }
        return null;

    }

    public App deleteApp(App app) {
        getApp(app);
        Connection cnn = null;
        PreparedStatement pstat = null;
        try {
            cnn = dataSource.getConnection();
            int aid = app.getAppId();
            if (aid > 0) {
                try {
                    cnn.setAutoCommit(false);
                    pstat = cnn.prepareStatement("delete from app where aid = ?");
                    pstat.setInt(1, aid);
                    pstat.executeUpdate();
                    cnn.commit();
                    app.setAppId(aid);
                    return app;
                } catch (SQLException e) {
                    log.warn("Rollback", e);
                    cnn.rollback();
                    throw e;
                }
            }
        } catch (SQLException e) {
            log.warn("Get connection error", e);
        } finally {
            DAOUtil.closeCnn(cnn, pstat);
        }
        return null;
    }

    public App getApp(App app) {
        Connection cnn = null;
        PreparedStatement pstat = null;
        try {
            cnn = dataSource.getConnection();
            pstat = cnn.prepareStatement("select * from app where aid = ? or appname = ?");
            pstat.setInt(1, app.getAppId());
            pstat.setString(2, app.getAppName());
            ResultSet rs = pstat.executeQuery();
            if (rs.next()) {
                app.setAppId(rs.getInt("aid"));
                app.setAppName(rs.getString("appname"));
                app.setCrtTime(rs.getString("crttime"));
                app.setAcompany(rs.getString("acompany"));
                app.setAdesc(rs.getString("adesc"));
                app.setAbyuid(rs.getInt("abyuid"));
                app.setAbgurl(rs.getString("abgurl"));
            }
            rs.close();
        } catch (SQLException e) {
            log.warn("Get connection error", e);
        } finally {
            DAOUtil.closeCnn(cnn, pstat);
        }
        return app;
    }

    public List<App> getAllApp(User user) {
        Connection cnn = null;
        PreparedStatement pstat = null;
        List<App> res = new ArrayList<App>();
        try {
            cnn = dataSource.getConnection();
            pstat = cnn.prepareStatement("select * from app where abyuid = ?");
            pstat.setInt(1, user.getUid());
            ResultSet rs = pstat.executeQuery();
            while (rs.next()) {
                App app = new App();
                app.setAppId(rs.getInt("aid"));
                app.setAppName(rs.getString("appname"));
                app.setCrtTime(rs.getString("crttime"));
                app.setAcompany(rs.getString("acompany"));
                app.setAdesc(rs.getString("adesc"));
                app.setAbyuid(rs.getInt("abyuid"));
                app.setAbgurl(rs.getString("abgurl"));
                res.add(app);
            }
            rs.close();
        } catch (SQLException e) {
            log.warn("Get connection error", e);
        } finally {
            DAOUtil.closeCnn(cnn, pstat);
        }
        return res;
    }

    public List<App> getAppToUser(User user) {
        Connection cnn = null;
        PreparedStatement pstat = null;
        List<App> res = new ArrayList<App>();
        try {
            cnn = dataSource.getConnection();
            cnn.setAutoCommit(false);
            pstat = cnn.prepareStatement("select * from app, apptouser where aid = auaid and auuid = ?;");
            pstat.setInt(1, user.getUid());
            ResultSet rs = pstat.executeQuery();
            while (rs.next()) {
                App app = new App();
                app.setAppId(rs.getInt("aid"));
                app.setAppName(rs.getString("appname"));
                app.setCrtTime(rs.getString("crttime"));
                app.setAcompany(rs.getString("acompany"));
                app.setAdesc(rs.getString("adesc"));
                app.setAbyuid(rs.getInt("abyuid"));
                res.add(app);
            }
            rs.close();
        } catch (SQLException e) {
            log.warn("Get connection error", e);
        } finally {
            DAOUtil.closeCnn(cnn, pstat);
        }
        return res;
    }

    public boolean bindPapp(Scene scene, Pointer p, int x, int y) {
        Connection cnn = null;
        PreparedStatement pstat = null;
        List<App> res = new ArrayList<App>();
        try {
            cnn = dataSource.getConnection();
            cnn.setAutoCommit(false);
            pstat = cnn.prepareStatement("REPLACE INTO pointerinscene (ppid,psid,px, py) VALUES(?,?,?,?);");
            pstat.setInt(1, p.getPid());
            pstat.setInt(2, scene.getSid());
            pstat.setInt(3, x);
            pstat.setInt(4, y);
            if (pstat.executeUpdate() > 0) {
                pstat = cnn.prepareStatement("update pointer set psid = ? where pid = ?");
                pstat.setInt(1, scene.getSid());
                pstat.setInt(2, p.getPid());
                int num = pstat.executeUpdate();
                cnn.commit();
                return num > 0;
            }
        } catch (SQLException e) {
            try {
                cnn.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            log.warn("Get connection error", e);
        } finally {
            DAOUtil.closeCnn(cnn, pstat);
        }
        return false;
    }

    public Map<Integer, Point> getAppScenePoint(Scene scene) {
        Map<Integer, Point> points = new HashMap<Integer, Point>();
        Connection cnn = null;
        PreparedStatement pstat = null;
        List<App> res = new ArrayList<App>();
        try {
            cnn = dataSource.getConnection();
            pstat = cnn.prepareStatement("select *  from pointerinscene where psid = ?");
            pstat.setInt(1, scene.getSid());
            ResultSet rs = pstat.executeQuery();
            while (rs.next()) {
                int x = rs.getInt("px");
                int y = rs.getInt("py");
                int pid = rs.getInt("ppid");
                points.put(pid, Point.point(x, y));
            }
        } catch (SQLException e) {
            log.warn("Get connection error", e);
        } finally {
            DAOUtil.closeCnn(cnn, pstat);
        }
        return points;
    }

    public Scene addScene(Scene s) {
        Connection cnn = null;
        PreparedStatement pstat = null;
        try {
            cnn = dataSource.getConnection();
            pstat = cnn.prepareStatement("insert into scene(sname, said, sbyuid, sdesc, stime, sbgurl) values(?, ?, ?, ?, NOW(), ?)");
            pstat.setString(1, s.getSname());
            pstat.setInt(2, s.getSaid());
            pstat.setInt(3, s.getSbyuid());
            pstat.setString(4, s.getSdesc());
            pstat.setString(5, s.getSbgurl());
            pstat.executeUpdate();
            pstat = cnn.prepareStatement("SELECT LAST_INSERT_ID() as id from app");
            ResultSet rs = pstat.executeQuery();
            if (rs.next()) {
                s.setSid(rs.getInt("id"));
                return s;
            }
            rs.close();
        } catch (SQLException e) {
            log.warn("Get connection error", e);
        } finally {
            DAOUtil.closeCnn(cnn, pstat);
        }
        return s;
    }

    public App getAppByScene(Scene s) {
        Connection cnn = null;
        PreparedStatement pstat = null;
        App app = new App();
        try {
            cnn = dataSource.getConnection();
            if (s.getSaid() > 0) {
                pstat = cnn.prepareStatement("select * from app where aid = ?");
                pstat.setInt(1, s.getSaid());
            } else {
                pstat = cnn.prepareStatement("select * from app, scene where aid = said and sid = ?");
                pstat.setInt(1, s.getSid());
            }
            ResultSet rs = pstat.executeQuery();
            if (rs.next()) {
                app.setAppId(rs.getInt("aid"));
                app.setAppName(rs.getString("appname"));
                app.setCrtTime(rs.getString("crttime"));
                app.setAcompany(rs.getString("acompany"));
                app.setAdesc(rs.getString("adesc"));
                app.setAbyuid(rs.getInt("abyuid"));
                //app.setAbgurl(rs.getString("abgurl"));
            }
            rs.close();
        } catch (SQLException e) {
            log.warn("Get connection error", e);
        } finally {
            DAOUtil.closeCnn(cnn, pstat);
        }
        return app;
    }

    public Scene getScene(Scene s) {
        Connection cnn = null;
        PreparedStatement pstat = null;
        try {
            cnn = dataSource.getConnection();
            pstat = cnn.prepareStatement("select *  from scene where sid = ?");
            pstat.setInt(1, s.getSid());
            ResultSet rs = pstat.executeQuery();
            if (rs.next()) {
                s.setSid(rs.getInt("sid"));
                s.setSname(rs.getString("sname"));
                s.setSaid(rs.getInt("said"));
                s.setSbyuid(rs.getInt("sbyuid"));
                s.setSdesc(rs.getString("sdesc"));
                s.setStime(rs.getString("stime"));
                s.setSbgurl(rs.getString("sbgurl"));
            }
            rs.close();
        } catch (SQLException e) {
            log.warn("Get connection error", e);
        } finally {
            DAOUtil.closeCnn(cnn, pstat);
        }
        return s;
    }

    public List<Scene> getSceneList(App app) {
        List<Scene> res = new ArrayList<Scene>();
        Connection cnn = null;
        PreparedStatement pstat = null;
        try {
            cnn = dataSource.getConnection();
            pstat = cnn.prepareStatement("select *  from scene where said = ?");
            pstat.setInt(1, app.getAppId());
            ResultSet rs = pstat.executeQuery();
            while (rs.next()) {
                Scene s = new Scene();
                s.setSid(rs.getInt("sid"));
                s.setSname(rs.getString("sname"));
                s.setSaid(rs.getInt("said"));
                s.setSbyuid(rs.getInt("sbyuid"));
                s.setSdesc(rs.getString("sdesc"));
                s.setStime(rs.getString("stime"));
                s.setSbgurl(rs.getString("sbgurl"));
                res.add(s);
            }
            rs.close();
        } catch (SQLException e) {
            log.warn("Get connection error", e);
        } finally {
            DAOUtil.closeCnn(cnn, pstat);
        }
        return res;
    }


    public List<Scene> getAllScene(User user, int p) {
        List<Scene> res = new ArrayList<Scene>();
        Connection cnn = null;
        PreparedStatement pstat = null;
        try {
            cnn = dataSource.getConnection();
            pstat = cnn.prepareStatement("SELECT * FROM app, apptouser, scene where aid = auaid and said = aid and auuid = ? order by sid limit ?, 10;");
            pstat.setInt(1, user.getUid());
            pstat.setInt(2, p * 10);
            ResultSet rs = pstat.executeQuery();
            while (rs.next()) {
                Scene s = new Scene();
                s.setSid(rs.getInt("sid"));
                s.setSname(rs.getString("sname"));
                s.setSaid(rs.getInt("said"));
                s.setSbyuid(rs.getInt("sbyuid"));
                s.setSdesc(rs.getString("sdesc"));
                s.setStime(rs.getString("stime"));
                s.setSbgurl(rs.getString("sbgurl"));
                s.setAppname(rs.getString("appname"));
                res.add(s);
            }
            rs.close();
        } catch (SQLException e) {
            log.warn("Get connection error", e);
        } finally {
            DAOUtil.closeCnn(cnn, pstat);
        }
        return res;
    }

    public int getAllSceneCnt(User user) {
        List<Scene> res = new ArrayList<Scene>();
        Connection cnn = null;
        PreparedStatement pstat = null;
        try {
            cnn = dataSource.getConnection();
            pstat = cnn.prepareStatement("SELECT count(*) as cnt FROM app, apptouser, scene where aid = auaid and said = aid and auuid = ?");
            pstat.setInt(1, user.getUid());
            ResultSet rs = pstat.executeQuery();
            while (rs.next()) {
                return rs.getInt("cnt");
            }
            rs.close();
        } catch (SQLException e) {
            log.warn("Get connection error", e);
        } finally {
            DAOUtil.closeCnn(cnn, pstat);
        }
        return 0;
    }
}
