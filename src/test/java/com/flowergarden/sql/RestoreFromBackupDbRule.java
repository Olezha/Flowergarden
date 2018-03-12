package com.flowergarden.sql;

import org.junit.rules.ExternalResource;

import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class RestoreFromBackupDbRule extends ExternalResource {

    public static void main(String[] args) throws IOException, SQLException {
        try (Statement statement = DriverManager
                .getConnection("jdbc:sqlite:")
                .createStatement()) {
            statement.executeUpdate("restore from backup.db");
        }
    }
}
