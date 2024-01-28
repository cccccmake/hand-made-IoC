package com.lagou.edu.utils;

import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionUtils {

    // hungry singleton

    /*private ConnectionUtils(){}

    private static ConnectionUtils connectionUtils = new ConnectionUtils();

    public static ConnectionUtils getInstance(){
        return connectionUtils;
    }*/

    // storing the connection of the current thread
    private ThreadLocal<Connection> threadLocal = new ThreadLocal<>();

    /**
     * query connection from the current thread
     */
    public Connection getCurrentThreadConn() throws SQLException {
        // check whether there is a bounded connection of the current
        // if not => query a connection from Druid
        Connection connection = threadLocal.get();
        if (connection == null) {
            connection = DruidUtils.getInstance().getConnection();
            // bind with the current thread
            threadLocal.set(connection);
        }
        return connection;
    }
}
