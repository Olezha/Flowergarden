package com.flowergarden.sql;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class ConnectionPoolJdbcImpl implements ConnectionPool {

    private List<Connection> connectionsPool = new ArrayList<>();
    private List<Connection> inUseConnections = new ArrayList<>();
    private String datasourceUrl;
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public ConnectionPoolJdbcImpl(Environment environment) {
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
        Connection connection = null;
        while (!connectionsPool.isEmpty()) {
            connection = connectionsPool.remove(0);
            if (connection.isClosed())
                connection = null;
            else break;
        }
        if (connection == null) {
            connection = newConnection();
            log.debug("Additional connection created (total {})", inUseConnections.size() + 1);
        }

        inUseConnections.add(connection);
        return connection;
    }

    void returnConnectionInPool(Connection connection) {
        inUseConnections.remove(connection);
        connectionsPool.add(connection);
    }

    private Connection newConnection() {
        try {
            return new Connection(DriverManager.getConnection(datasourceUrl), this);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void close() {
        for (Connection connection : connectionsPool) {
            connection.closeConnection();
        }
        for (Connection connection : inUseConnections) {
            connection.closeConnection();
        }
    }
}
