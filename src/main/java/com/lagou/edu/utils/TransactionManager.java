package com.lagou.edu.utils;

import java.sql.SQLException;

/**
 * manager for trxs, responsible for start, commit, and rollback
 */
public class TransactionManager {
    private ConnectionUtils connectionUtils;

    public void setConnectionUtils(ConnectionUtils connectionUtils) {
        this.connectionUtils = connectionUtils;
    }

    // singleton
    /*private TransactionManager(){}
    private static TransactionManager transactionManager = new TransactionManager();
    public static TransactionManager getInstance(){
        return transactionManager;
    }*/
    public void beginTransaction() throws SQLException {
        connectionUtils.getCurrentThreadConn().setAutoCommit(false);
    }

    public void commit() throws SQLException {
        connectionUtils.getCurrentThreadConn().commit();
    }

    public void rollback() throws SQLException {
        connectionUtils.getCurrentThreadConn().rollback();
    }
}
