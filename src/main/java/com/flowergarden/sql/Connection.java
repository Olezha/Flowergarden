package com.flowergarden.sql;

import java.sql.*;

public class Connection implements AutoCloseable {

    private java.sql.Connection connection;
    private ConnectionPoolJdbcImpl pool;

    Connection(java.sql.Connection connection, ConnectionPoolJdbcImpl pool) {
        this.connection = connection;
        this.pool = pool;
    }

    public Statement createStatement() {
        try {
            return connection.createStatement();
        } catch (SQLException e) {
            // TODO
            e.printStackTrace();
        }
        return null;
    }

    public PreparedStatement prepareStatement(String s) {
        try {
            return connection.prepareStatement(s);
        } catch (SQLException e) {
            // TODO
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void close() {
        pool.returnConnectionInPool(this);
    }

    void closeConnection() {
        try {
            connection.close();
        } catch (SQLException e) {
            // TODO
            e.printStackTrace();
        }
    }

    boolean isClosed() {
        try {
            return connection.isClosed();
        } catch (SQLException e) {
            // TODO
            e.printStackTrace();
        }
        return true;
    }
}
