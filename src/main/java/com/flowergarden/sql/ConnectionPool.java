package com.flowergarden.sql;

public interface ConnectionPool extends AutoCloseable {

    java.sql.Connection getConnection();
}
