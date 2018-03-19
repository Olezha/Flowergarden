package com.flowergarden.sql;

import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.*;
import java.sql.Connection;

public class ConnectionPoolDbcp2BasicDataSourceImpl implements ConnectionPool {

    private BasicDataSource basicDataSource;

    public ConnectionPoolDbcp2BasicDataSourceImpl(BasicDataSource basicDataSource) {
        this.basicDataSource = basicDataSource;
    }

    @Override
    public Connection getConnection() {
        try {
            return basicDataSource.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void close() throws Exception {
        basicDataSource.close();
    }
}
