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
    private List<JdbcConnectionForPool> inUseConnections = new ArrayList<>();
    private String datasourceUrl;

    @Autowired
    public JdbcConnectionPool(Environment environment) throws SQLException {
        this.datasourceUrl = environment.getRequiredProperty("datasource.url");

        int poolSize = 10;
        String poolSizePropertyString = environment.getProperty("connection.pool.size");
        if (poolSizePropertyString != null) {
            try {
                poolSize = Integer.parseUnsignedInt(poolSizePropertyString);
            } catch (NumberFormatException e) {
                System.out.println("Wrong property \"connection.pool.size\"");
            }
        }

        for (int i = 0; i < poolSize; i++)
            connectionsPool.add(newConnection());
    }

    public Connection getConnection() throws SQLException {
        JdbcConnectionForPool connection = null;
        while (!connectionsPool.isEmpty()) {
            connection = connectionsPool.remove(0);
            if (connection.isClosed())
                connection = null;
            else break;
        }
        if (connection == null) {
            connection = newConnection();
            System.out.println("Additional connection created (total " + (inUseConnections.size() + 1) + ")");
        }

        inUseConnections.add(connection);
        return connection;
    }

    void returnConnectionInPool(JdbcConnectionForPool connection) {
        inUseConnections.remove(connection);
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
        for (JdbcConnectionForPool connection : inUseConnections) {
            connection.closeConnection();
        }
    }
}
