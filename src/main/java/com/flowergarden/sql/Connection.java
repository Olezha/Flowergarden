package com.flowergarden.sql;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

public class Connection implements AutoCloseable {

    public static final int TRANSACTION_SERIALIZABLE = java.sql.Connection.TRANSACTION_SERIALIZABLE;
    public static final int TRANSACTION_REPEATABLE_READ = java.sql.Connection.TRANSACTION_REPEATABLE_READ;
    private final java.sql.Connection connection;
    private final ConnectionPoolJdbcImpl pool;
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    Connection(java.sql.Connection connection, ConnectionPoolJdbcImpl pool) {
        this.connection = connection;
        this.pool = pool;
    }

    public Statement createStatement() {
        try {
            return connection.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public PreparedStatement prepareStatement(String s) {
        try {
            return connection.prepareStatement(s);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void close() {
        pool.returnConnectionInPool(this);
    }

    void closeConnection() {
        try {
            connection.close();
        } catch (SQLException e) {
            log.warn("{}", e);
        }
    }

    boolean isClosed() {
        try {
            return connection.isClosed();
        } catch (SQLException e) {
            log.warn("{}", e);
        }
        return true;
    }

    public void setAutoCommit(boolean autoCommit) throws SQLException {
        connection.setAutoCommit(autoCommit);
    }

    public int getTransactionIsolation() throws SQLException {
        return connection.getTransactionIsolation();
    }

    public DatabaseMetaData getMetaData() throws SQLException {
        return connection.getMetaData();
    }

    public void setTransactionIsolation(int transactionIsolation) throws SQLException {
        connection.setTransactionIsolation(transactionIsolation);
    }

    public void commit() throws SQLException {
        connection.commit();
    }

    public void rollback() throws SQLException {
        connection.rollback();
    }
}
