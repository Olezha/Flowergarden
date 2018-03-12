package com.flowergarden.sql;

import org.junit.rules.ExternalResource;

import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class BackupRule extends ExternalResource {

    public static void main(String[] args) throws IOException, SQLException {
        Properties properties = new Properties();
        properties.load(ClassLoader.getSystemResourceAsStream("application.properties"));

        try (Statement statement = DriverManager
                .getConnection(properties.getProperty("datasource.url"))
                .createStatement()) {
            statement.executeUpdate("backup to backup.db");
        }
    }
}
