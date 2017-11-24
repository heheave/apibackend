package com.sjtu.dao;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.sjtu.pojo.App;
import com.sjtu.pojo.Device;
import com.sjtu.pojo.Port;
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
public class AppDeviceDAO {

    private static final Logger log = Logger.getLogger(AppDeviceDAO.class);

    @Autowired
    @Qualifier("dataSource")
    private ComboPooledDataSource dataSource;

    public boolean add(int appId, int did) {
        Connection cnn = null;
        PreparedStatement pstat = null;
        try {
            cnn = dataSource.getConnection();
            pstat = cnn.prepareStatement("insert into d_app_did(appid, did) values(?, ?)");
            pstat.setInt(1, appId);
            pstat.setInt(2, did);
            return pstat.executeUpdate() > 0;
        } catch (SQLException e) {
            log.warn("Get connection error", e);
        } finally {
            DAOUtil.closeCnn(cnn, pstat);
        }
        return false;

    }

    public boolean remove(int appId, int did) {
        Connection cnn = null;
        PreparedStatement pstat = null;
        try {
            cnn = dataSource.getConnection();
            pstat = cnn.prepareStatement("delete from d_app_did where appid = ? and did = ?");
            pstat.setInt(1, appId);
            pstat.setInt(2, did);
            return pstat.executeUpdate() > 0;
        } catch (SQLException e) {
            log.warn("Get connection error", e);
        } finally {
            DAOUtil.closeCnn(cnn, pstat);
        }
        return false;
    }

    public Device addDevice(Device device) {
        Connection cnn = null;
        PreparedStatement pstat = null;
        try {
            cnn = dataSource.getConnection();
            cnn.setAutoCommit(false);
            pstat = cnn.prepareStatement("insert into d_device(dappid, dmark, dtype, dcompany, dloc_x, dloc_y, ddes, dportnum) values(?, ?, ?, ?, ?, ?, ?, ?)");
            pstat.setInt(1, device.getDappid());
            pstat.setString(2, device.getDmark());
            pstat.setString(3, device.getDtype());
            pstat.setString(4, device.getDcompany());
            pstat.setDouble(5, device.getDlocX());
            pstat.setDouble(6, device.getDlocY());
            pstat.setString(7, device.getDdes());
            pstat.setInt(8, device.getDportnum());
            int updateCnt = pstat.executeUpdate();
            if (updateCnt == 1) {
                pstat = cnn.prepareStatement("SELECT LAST_INSERT_ID() as id from d_device;");
                ResultSet rs = pstat.executeQuery();
                int did = -1;
                if (rs.next()) {
                    did = rs.getInt("id");
                }
                rs.close();
                device.setDid(did);
                if (did > 0 && device.getDportnum() > 0) {
                    pstat = cnn.prepareStatement("insert into d_device_port(did, dport, ddes, dunit) values(?,?,?,?)");
                    for (int i = 0; i < device.getDportnum(); i++) {
                        Port tmp = device.getDports().get(i);
                        pstat.setInt(1, did);
                        pstat.setInt(2, tmp.getDport());
                        pstat.setString(3, tmp.getDdes());
                        pstat.setString(4, tmp.getDunit());
                        pstat.addBatch();
                    }
                    pstat.executeBatch();
                    cnn.commit();
                    return device;
                }
            }
        } catch (SQLException e) {
            if (cnn != null) {
                try {
                    cnn.rollback();
                } catch (SQLException e1) {
                    log.warn("Rollback error", e);
                }
            }
        } finally {
            DAOUtil.closeCnn(cnn, pstat);
        }
        return null;
    }

    public Device delDevice(Device device) {
        Connection cnn = null;
        PreparedStatement pstat = null;
        try {
            cnn = dataSource.getConnection();
            int did = device.getDid();
            if (did <= 0 && device.getDmark() != null) {
                pstat = cnn.prepareStatement("select did from d_device where dappid = ? and dmark = ?");
                pstat.setInt(1, device.getDappid());
                pstat.setString(2, device.getDmark());
                ResultSet rs = pstat.executeQuery();
                if (rs.next()) {
                    did = rs.getInt("did");
                }
            }
            if (did > 0) {
                try {
                    cnn.setAutoCommit(false);
                    pstat = cnn.prepareStatement("update c_config set cused = 0 where cdmark = ?");
                    pstat.setString(1, device.getDmark());
                    pstat.executeUpdate();
                    pstat = cnn.prepareStatement("delete from d_device_port where did = ?");
                    pstat.setInt(1, did);
                    pstat.executeUpdate();
                    pstat = cnn.prepareStatement("delete from d_device where did = ?");
                    pstat.setInt(1, did);
                    pstat.executeUpdate();
                    cnn.commit();
                    device.setDid(did);
                    return device;
                } catch (SQLException e) {
                    cnn.rollback();
                    log.warn("Rollback", e);
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

    public List<Device> getAppDevices(int aid) {
        Connection cnn = null;
        PreparedStatement pstat = null;
        List<Device> dids = new ArrayList<Device>();
        try {
            cnn = dataSource.getConnection();
            pstat = cnn.prepareStatement("select " +
                    "dd.did, dd.dappid, dd.dmark, dd.dtype, dd.dcompany, dd.dloc_x, dd.dloc_y, dd.ddes, dd.dportnum " +
                    "from d_device dd where dd.dappid = ?");
            pstat.setInt(1, aid);
            ResultSet rs = pstat.executeQuery();
            while (rs.next()) {
                Device d = new Device();
                d.setDid(rs.getInt("did"));
                d.setDappid(rs.getInt("dappid"));
                d.setDmark(rs.getString("dmark"));
                d.setDtype(rs.getString("dtype"));
                d.setDcompany(rs.getString("dcompany"));
                d.setDlocX(rs.getDouble("dloc_x"));
                d.setDlocY(rs.getDouble("dloc_y"));
                d.setDdes(rs.getString("ddes"));
                if (d.getDid() > 0) {
                    List<Port> ports = new ArrayList<Port>();
                    try {
                        PreparedStatement pstat0 = cnn.prepareStatement("select dport, ddes, dunit from d_device_port where did = ?");
                        pstat0.setInt(1, d.getDid());
                        ResultSet rs0 = pstat0.executeQuery();
                        while (rs0.next()) {
                            Port p = new Port();
                            p.setDport(rs0.getInt("dport"));
                            p.setDdes(rs0.getString("ddes"));
                            p.setDunit(rs0.getString("dunit"));
                            ports.add(p);
                        }
                        rs0.close();
                        pstat0.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                        // do nothing
                    }
                    d.setDports(ports);
                }
                dids.add(d);
            }
        } catch (SQLException e) {
            log.warn("Get connection error", e);
        } finally {
            DAOUtil.closeCnn(cnn, pstat);
        }
        return dids;
    }

    public List<Device> getAppDevices(String appName) {
        Connection cnn = null;
        PreparedStatement pstat = null;
        List<Device> dids = new ArrayList<Device>();
        try {
            cnn = dataSource.getConnection();
            pstat = cnn.prepareStatement("select " +
                    "dd.did, dd.dappid, dd.dmark, dd.dtype, dd.dcompany, dd.dloc_x, dd.dloc_y, dd.ddes, dd.dportnum " +
                    "from d_app da, d_device dd " +
                    "where da.appid = dd.dappid and da.appname = ?");
            pstat.setString(1, appName);
            ResultSet rs = pstat.executeQuery();
            while (rs.next()) {
                Device d = new Device();
                d.setDid(rs.getInt("did"));
                d.setDappid(rs.getInt("dappid"));
                d.setDmark(rs.getString("dmark"));
                d.setDtype(rs.getString("dtype"));
                d.setDcompany(rs.getString("dcompany"));
                d.setDlocX(rs.getDouble("dloc_x"));
                d.setDlocY(rs.getDouble("dloc_y"));
                d.setDdes(rs.getString("ddes"));
                d.setDportnum(rs.getInt("dportnum"));
                if (d.getDid() > 0) {
                    List<Port> ports = new ArrayList<Port>();
                    try {
                        PreparedStatement pstat0 = cnn.prepareStatement("select dport, ddes, dunit from d_device_port where did = ?");
                        pstat0.setInt(1, d.getDid());
                        ResultSet rs0 = pstat0.executeQuery();
                        while (rs0.next()) {
                            Port p = new Port();
                            p.setDport(rs0.getInt("dport"));
                            p.setDdes(rs0.getString("ddes"));
                            p.setDunit(rs0.getString("dunit"));
                            ports.add(p);
                        }
                        rs0.close();
                        pstat0.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                        // do nothing
                    }
                    d.setDports(ports);
                }
                dids.add(d);
            }
        } catch (SQLException e) {
            log.warn("Get connection error", e);
        } finally {
            DAOUtil.closeCnn(cnn, pstat);
        }
        return dids;
    }

    public Device getDeviceByDid(int did) {
        Connection cnn = null;
        PreparedStatement pstat = null;
        try {
            cnn = dataSource.getConnection();
            pstat = cnn.prepareStatement("select " +
                    "dd.did, dd.dappid, dd.dmark, dd.dtype, dd.dcompany, dd.dloc_x, dd.dloc_y, dd.des " +
                    "from d_device dd where dd.did = ?");
            pstat.setInt(1, did);
            ResultSet rs = pstat.executeQuery();
            Device d = new Device();
            if (rs.next()) {
                d.setDid(rs.getInt("did"));
                d.setDappid(rs.getInt("dappid"));
                d.setDmark(rs.getString("dmark"));
                d.setDtype(rs.getString("dtype"));
                d.setDcompany(rs.getString("dcompany"));
                d.setDlocX(rs.getDouble("dloc_x"));
                d.setDlocY(rs.getDouble("dloc_y"));
                d.setDdes(rs.getString("ddes"));
                d.setDportnum(rs.getInt("dportnum"));
                if (d.getDid() > 0) {
                    List<Port> ports = new ArrayList<Port>();
                    try {
                        PreparedStatement pstat0 = cnn.prepareStatement("select dport, ddes, dunit from d_device_port where did = ?");
                        pstat0.setInt(1, d.getDid());
                        ResultSet rs0 = pstat0.executeQuery();
                        while (rs0.next()) {
                            Port p = new Port();
                            p.setDport(rs0.getInt("dport"));
                            p.setDdes(rs0.getString("ddes"));
                            p.setDunit(rs0.getString("dunit"));
                            ports.add(p);
                        }
                        rs0.close();
                        pstat0.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                        // do nothing
                    }
                    d.setDports(ports);
                }
            }
            rs.close();
            return d;
        } catch (SQLException e) {
            log.warn("Get connection error", e);
        } finally {
            DAOUtil.closeCnn(cnn, pstat);
        }
        return null;
    }

    public Device getAppDevice(App app, Device device) {
        Connection cnn = null;
        PreparedStatement pstat = null;
        try {
            cnn = dataSource.getConnection();
            if (app.getAppId() > 0) {
                if (device.getDid() > 0) {
                    pstat = cnn.prepareStatement("select " +
                            "dd.did, dd.dappid, dd.dmark, dd.dtype, dd.dcompany, dd.dloc_x, dd.dloc_y, dd.ddes, dd.dportnum " +
                            "from d_device dd where dd.did = ?");
                    pstat.setInt(1, device.getDid());
                } else if (device.getDmark() != null) {
                    pstat = cnn.prepareStatement("select " +
                            "dd.did, dd.dappid, dd.dmark, dd.dtype, dd.dcompany, dd.dloc_x, dd.dloc_y, dd.ddes, dd.dportnum " +
                            "from d_device dd where  dad.appid = ? and dd.dmark = ?");
                    pstat.setInt(1, app.getAppId());
                    pstat.setString(2, device.getDmark());
                } else {
                    return device;
                }
            } else if (app.getAppName() != null) {
                if (device.getDid() > 0) {
                    pstat = cnn.prepareStatement("select " +
                            "dd.did, dd.dappid, dd.dmark, dd.dtype, dd.dcompany, dd.dloc_x, dd.dloc_y, dd.ddes, dd.dportnum " +
                            "from d_app da, d_device dd " +
                            "where da.appid = dd.dappid and da.appname = ? and dd.did = ?");
                    pstat.setInt(1, app.getAppId());
                    pstat.setInt(2, device.getDid());
                } else if (device.getDmark() != null) {
                    pstat = cnn.prepareStatement("select " +
                            "dd.did, dd.dappid, dd.dmark, dd.dtype, dd.dcompany, dd.dloc_x, dd.dloc_y, dd.ddes, dd.dportnum " +
                            "from d_app da, d_device dd " +
                            "where da.appid = dd.dappid and da.appname = ? and dd.dmark = ?");
                    pstat.setInt(1, app.getAppId());
                    pstat.setString(2, device.getDmark());
                } else {
                    return device;
                }
            } else {
                return device;
            }
            ResultSet rs = pstat.executeQuery();
            if (rs.next()) {
                device.setDid(rs.getInt("did"));
                device.setDappid(rs.getInt("dappid"));
                device.setDmark(rs.getString("dmark"));
                device.setDtype(rs.getString("dtype"));
                device.setDcompany(rs.getString("dcompany"));
                device.setDlocX(rs.getDouble("dloc_x"));
                device.setDlocY(rs.getDouble("dloc_y"));
                device.setDdes(rs.getString("ddes"));
                device.setDportnum(rs.getInt("dportnum"));
                if (device.getDid() > 0) {
                    List<Port> ports = new ArrayList<Port>();
                    try {
                        PreparedStatement pstat0 = cnn.prepareStatement("select dport, ddes, dunit from d_device_port where did = ?");
                        pstat0.setInt(1, device.getDid());
                        ResultSet rs0 = pstat0.executeQuery();
                        while (rs0.next()) {
                            Port p = new Port();
                            p.setDport(rs0.getInt("dport"));
                            p.setDdes(rs0.getString("ddes"));
                            p.setDunit(rs0.getString("dunit"));
                            ports.add(p);
                        }
                        rs0.close();
                        pstat0.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                        // do nothing
                    }
                    device.setDports(ports);
                }
            }
            rs.close();
        } catch (SQLException e) {
            log.warn("Get connection error", e);
        } finally {
            DAOUtil.closeCnn(cnn, pstat);
        }
        return device;
    }

    public List<Device> getAppDevices(App app) {
        Connection cnn = null;
        PreparedStatement pstat = null;
        List<Device> res = new ArrayList<Device>();
        try {
            cnn = dataSource.getConnection();
            if (app.getAppId() > 0) {
                pstat = cnn.prepareStatement("select " +
                        "dd.did, dd.dappid, dd.dmark, dd.dtype, dd.dcompany, dd.dloc_x, dd.dloc_y, dd.ddes, dd.dportnum " +
                        "from d_device dd where dd.dappid = ?");
                pstat.setInt(1, app.getAppId());
            } else if (app.getAppName() != null) {
                pstat = cnn.prepareStatement("select " +
                        "dd.did, dd.dappid, dd.dmark, dd.dtype, dd.dcompany, dd.dloc_x, dd.dloc_y, dd.ddes, dd.dportnum " +
                        "from d_app da, d_device dd where da.appid = dd.dappid and da.appname = ?");
                pstat.setString(1, app.getAppName());
            }
            ResultSet rs = pstat.executeQuery();
            while (rs.next()) {
                Device device = new Device();
                device.setDid(rs.getInt("did"));
                device.setDappid(rs.getInt("dappid"));
                device.setDmark(rs.getString("dmark"));
                device.setDtype(rs.getString("dtype"));
                device.setDcompany(rs.getString("dcompany"));
                device.setDlocX(rs.getDouble("dloc_x"));
                device.setDlocY(rs.getDouble("dloc_y"));
                device.setDdes(rs.getString("ddes"));
                device.setDportnum(rs.getInt("dportnum"));
                if (device.getDid() > 0) {
                    List<Port> ports = new ArrayList<Port>();
                    try {
                        PreparedStatement pstat0 = cnn.prepareStatement("select dport, ddes, dunit from d_device_port where did = ?");
                        pstat0.setInt(1, device.getDid());
                        ResultSet rs0 = pstat0.executeQuery();
                        while (rs0.next()) {
                            Port p = new Port();
                            p.setDport(rs0.getInt("dport"));
                            p.setDdes(rs0.getString("ddes"));
                            p.setDunit(rs0.getString("dunit"));
                            ports.add(p);
                        }
                        rs0.close();
                        pstat0.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                        // do nothing
                    }
                    device.setDports(ports);
                }
                res.add(device);
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
