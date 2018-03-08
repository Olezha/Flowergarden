package com.flowergarden.storage;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class JdbcConnectionPool implements AutoCloseable {

    private List<JdbcConnectionFromPool> connectionsPool = new ArrayList<>();
    private String datasourceUrl;
    private int connectionPoolSize = 10;

    public JdbcConnectionPool() throws SQLException {
        loadProperties();

        for (int i = 0; i < connectionPoolSize; i++)
            putConnection(newConnection());
    }

    public Connection getConnection() throws SQLException {
        if (!connectionsPool.isEmpty())
            return connectionsPool.remove(0);

        return newConnection();
    }

    void putConnection(JdbcConnectionFromPool connection) {
        connectionsPool.add(connection);
    }

    private JdbcConnectionFromPool newConnection() throws SQLException {
        return new JdbcConnectionFromPool(DriverManager.getConnection(datasourceUrl), this);
    }

    @Override
    public void close() throws Exception {
        for (JdbcConnectionFromPool connection : connectionsPool) {
            connection.closeConnection();
        }
    }

    private void loadProperties() {
        try (InputStream propertiesStream =
                     Thread.currentThread().getContextClassLoader()
                             .getResourceAsStream("application.properties")) {
            Properties properties = new Properties();
            properties.load(propertiesStream);

            if (properties.containsKey("datasource.url"))
                datasourceUrl = properties.getProperty("datasource.url");
            if (properties.containsKey("connection.pool.size")) {
                try {
                    connectionPoolSize = Integer.parseInt(properties.getProperty("connection.pool.size"));
                } catch (NumberFormatException e) {
                    System.out.println("Parameter \"connection.pool.size\" has a wrong format");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
