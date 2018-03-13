package com.flowergarden.sql;

public interface ConnectionPool extends AutoCloseable {

    Connection getConnection();
}
