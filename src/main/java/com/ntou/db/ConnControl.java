package com.ntou.db;

import com.ntou.tool.Common;
import lombok.extern.log4j.Log4j2;

import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

@Log4j2
public class ConnControl {
    static final String PROPERTIES_FILE = "application.properties";
    private static final Properties properties = new Properties();

    static {
        try (InputStream input = ConnControl.class.getClassLoader().getResourceAsStream(PROPERTIES_FILE)) {
            if (input == null)
                log.error("unable to find " + PROPERTIES_FILE);
            properties.load(input);
        } catch (Exception e) {
            log.error(Common.EXCEPTION, e);
        }
    }

    Connection conn = null;

    public Connection getConnection() {
        try {
            if (conn == null || conn.isClosed()) {
                Class.forName(properties.getProperty("jdbc.driver"));
                conn = DriverManager.getConnection(
                        properties.getProperty("jdbc.url"),
                        properties.getProperty("jdbc.username"),
                        properties.getProperty("jdbc.password")
                );
            }
        } catch (Exception e) {
            log.error(Common.EXCEPTION, e);
        }
        return conn;
    }

    public void closeConn(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                log.error("Error closing connection: ", e);
            }
        }
    }

    public void closePS(PreparedStatement ps) throws Exception {
        if(ps != null) ps.close();
    }

    public void closeRS(ResultSet rs) throws Exception {
        if(rs != null) rs.close();
    }
}
