package com.sjtu.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by xiaoke on 17-6-12.
 */
public class DAOUtil {

    public static void closeCnn(Connection cnn, PreparedStatement pstat) {
        if (pstat != null) {
            try {
                pstat.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }


        if (cnn != null) {
            try {
                cnn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }
}
