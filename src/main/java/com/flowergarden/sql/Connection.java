package com.flowergarden.sql;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

public class Connection implements AutoCloseable {

    private java.sql.Connection connection;
    private ConnectionPoolJdbcImpl pool;
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
}
