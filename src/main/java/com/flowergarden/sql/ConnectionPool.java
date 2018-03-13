package com.flowergarden.sql;

import java.sql.Connection;

public interface ConnectionPool extends AutoCloseable {

    Connection getConnection();
}
