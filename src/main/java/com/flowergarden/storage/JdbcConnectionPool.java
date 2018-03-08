package com.flowergarden.storage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcConnectionPool implements AutoCloseable {

    private List<JdbcConnectionForPool> connectionsPool = new ArrayList<>();
    private String datasourceUrl;

    @Autowired
    public JdbcConnectionPool(Environment environment) throws SQLException {
        this.datasourceUrl = environment.getRequiredProperty("datasource.url");

        int poolSize = 10;
        try {
            String poolSizePropertyString = environment.getProperty("connection.pool.size");
            int poolSizeProperty = Integer.parseInt(poolSizePropertyString);
            if (poolSizeProperty > 0)
                poolSize = poolSizeProperty;
        } catch (NumberFormatException e) {
            // ok
        }

        for (int i = 0; i < poolSize; i++)
            putConnection(newConnection());
    }

    public Connection getConnection() throws SQLException {
        if (!connectionsPool.isEmpty())
            return connectionsPool.remove(0);

        return newConnection();
    }

    void putConnection(JdbcConnectionForPool connection) {
        connectionsPool.add(connection);
    }

    private JdbcConnectionForPool newConnection() throws SQLException {
        return new JdbcConnectionForPool(DriverManager.getConnection(datasourceUrl), this);
    }

    @Override
    public void close() throws Exception {
        for (JdbcConnectionForPool connection : connectionsPool) {
            connection.closeConnection();
        }
    }
}
