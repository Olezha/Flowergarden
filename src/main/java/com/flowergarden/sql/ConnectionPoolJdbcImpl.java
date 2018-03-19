package com.flowergarden.sql;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.PrintWriter;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.ArrayList;
import java.util.List;

public class ConnectionPoolJdbcImpl implements ConnectionPool {

    private final List<Connection> connectionsPool = new ArrayList<>();
    private final List<Connection> inUseConnections = new ArrayList<>();
    private final String datasourceUrl;
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    public ConnectionPoolJdbcImpl(String datasourceUrl, int poolSize) {
        this.datasourceUrl = datasourceUrl;

        for (int i = 0; i < poolSize; i++)
            connectionsPool.add(newConnection());
    }

    @Override
    public java.sql.Connection getConnection() {
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

    /*
     * and more other wrapper methods for Connection
     */

    @Override
    public java.sql.Connection getConnection(String username, String password) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public PrintWriter getLogWriter() throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setLogWriter(PrintWriter out) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setLoginTimeout(int seconds) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public int getLoginTimeout() throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public java.util.logging.Logger getParentLogger() throws SQLFeatureNotSupportedException {
        throw new UnsupportedOperationException();
    }
}
