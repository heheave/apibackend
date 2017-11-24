package com.sjtu.dao;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * Created by xiaoke on 17-10-6.
 */
public class DeviceMonitorDAO {

    @Autowired
    @Qualifier("dataSource")
    private ComboPooledDataSource dataSource;

}
