package com.flowergarden.storage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class JdbcConnectionPool {

    public static void main(String[] args) throws SQLException {
        JdbcConnectionPool jdbcConnectionPool = new JdbcConnectionPool();
    }

    private List<JdbcConnectionFromPool> connections = new ArrayList<>();
    private String datasourceUrl = "jdbc:sqlite:./flowergarden.db";
    private int connectionPoolSize = 10;

    public JdbcConnectionPool() throws SQLException {
        loadProperties("application.properties");

        for (int i = 0; i < connectionPoolSize; i++)
            connections.add(
                    new JdbcConnectionFromPool(
                            DriverManager.getConnection(datasourceUrl)));
    }

    public JdbcConnectionFromPool getConnection() throws SQLException {
        return null;
    }

    private void loadProperties(String propertiesFileName) {
        try (InputStream propertiesStream =
                     Thread.currentThread().getContextClassLoader()
                             .getResourceAsStream(propertiesFileName)) {
            Properties properties = new Properties();
            properties.load(propertiesStream);

            if (properties.containsKey("datasource.url"))
                System.out.println("properties.contains(\"datasource.url\")");
            if (properties.containsKey("connection.pool.size"))
                try {
                    connectionPoolSize = Integer.parseInt(properties.getProperty("connection.pool.size"));
                } catch (NumberFormatException e) {
                    System.out.println("Parameter \"connection.pool.size\" has a wrong format");
                }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
