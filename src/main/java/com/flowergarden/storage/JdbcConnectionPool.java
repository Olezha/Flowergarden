package com.flowergarden.storage;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JdbcConnectionPool {

    private List<JdbcConnectionFromPool> connections = new ArrayList<>();
    private int size;

    public JdbcConnectionPool() throws SQLException {
        for (int i = 0; i < size; i++)
            connections.add(new JdbcConnectionFromPool(DriverManager.getConnection("jdbc:sqlite:./flowergarden.db")));
    }

    public JdbcConnectionFromPool getConnection() throws SQLException {
        return null;
    }
}
