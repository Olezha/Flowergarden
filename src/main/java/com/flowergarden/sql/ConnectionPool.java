package com.flowergarden.sql;

import javax.sql.DataSource;

public interface ConnectionPool extends DataSource, AutoCloseable {
}
