package com.flowergarden.run;

import java.sql.*;

public class Run {

    private static final String SELECT_FLOWERS = "select * from flower";

    public static void main(String[] args) throws SQLException {
        Run run = new Run();
        try (Statement statement = run.getConnection().createStatement()) {
            ResultSet flowersResultSet = statement.executeQuery(SELECT_FLOWERS);
            while (flowersResultSet.next())
                System.out.println(flowersResultSet.getInt("id") + " " + flowersResultSet.getString("name"));
        }
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:sqlite:./flowergarden.db");
    }
}
