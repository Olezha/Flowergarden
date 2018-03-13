package com.flowergarden.sql;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcConnectionPool implements ConnectionPool {

    private List<JdbcConnectionForPool> connectionsPool = new ArrayList<>();
    private List<JdbcConnectionForPool> inUseConnections = new ArrayList<>();
    private String datasourceUrl;
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public JdbcConnectionPool(Environment environment) {
        this.datasourceUrl = environment.getRequiredProperty("datasource.url");

        int poolSize = 10;
        String poolSizePropertyString = environment.getProperty("connection.pool.size");
        if (poolSizePropertyString != null) {
            try {
                poolSize = Integer.parseUnsignedInt(poolSizePropertyString);
            } catch (NumberFormatException e) {
                log.warn("Wrong property \"connection.pool.size\"");
            }
        }

        for (int i = 0; i < poolSize; i++)
            connectionsPool.add(newConnection());
    }

    @Override
    public Connection getConnection() {
        JdbcConnectionForPool connection = null;
        while (!connectionsPool.isEmpty()) {
            connection = connectionsPool.remove(0);
            try {
                if (connection.isClosed())
                    connection = null;
                else break;
            } catch (SQLException e) {
                log.debug("{}", e);
                connection = null;
            }
        }
        if (connection == null) {
            connection = newConnection();
            log.debug("Additional connection created (total {})", inUseConnections.size() + 1);
        }

        inUseConnections.add(connection);
        return connection;
    }

    void returnConnectionInPool(JdbcConnectionForPool connection) {
        inUseConnections.remove(connection);
        connectionsPool.add(connection);
    }

    private JdbcConnectionForPool newConnection() {
        try {
            return new JdbcConnectionForPool(DriverManager.getConnection(datasourceUrl), this);
        } catch (SQLException e) {
            log.debug("{}", e);
            log.warn("");
            // TODO
        }
        return null;
    }

    @Override
    public void close() {
        for (JdbcConnectionForPool connection : connectionsPool) {
            closeConnection(connection);
        }
        for (JdbcConnectionForPool connection : inUseConnections) {
            closeConnection(connection);
        }
    }

    private void closeConnection(JdbcConnectionForPool connection) {
        try {
            connection.closeConnection();
        } catch (SQLException e) {
            log.debug("{}", e);
            log.warn("");
            // TODO
        }
    }
}
