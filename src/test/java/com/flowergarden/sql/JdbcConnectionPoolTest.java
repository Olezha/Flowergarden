package com.flowergarden.sql;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.util.List;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class JdbcConnectionPoolTest {

    @Autowired
    private JdbcConnectionPool jdbcConnectionPool;

    @Ignore
    @Test
    public void poolTest() throws Exception {
        Field connectionsPoolField = jdbcConnectionPool.getClass().getDeclaredField("connectionsPool");
        connectionsPoolField.setAccessible(true);
        List<Connection> connectionsPool = (List) connectionsPoolField.get(jdbcConnectionPool);

        Field inUseConnectionsField = jdbcConnectionPool.getClass().getDeclaredField("inUseConnections");
        inUseConnectionsField.setAccessible(true);
        List<Connection> inUseConnections = (List) inUseConnectionsField.get(jdbcConnectionPool);

        int totalConnections = connectionsPool.size() + inUseConnections.size();

        for (int i = 0; i < totalConnections + 2; i++)
            jdbcConnectionPool.getConnection();

        assertSame(totalConnections + 2, inUseConnections.size());
        assertTrue(connectionsPool.isEmpty());

        while (inUseConnections.size() > 0)
            inUseConnections.get(0).close();

        for (Connection connection : connectionsPool)
            assertFalse(connection.isClosed());

        assertSame(totalConnections + 2, connectionsPool.size());
        assertTrue(inUseConnections.isEmpty());

        for (Connection connection : connectionsPool) {
            JdbcConnectionForPool jdbcConnectionForPool = (JdbcConnectionForPool) connection;
            jdbcConnectionForPool.closeConnection();
        }

        jdbcConnectionPool.getConnection();

        assertSame(0, connectionsPool.size());
        assertSame(1, inUseConnections.size());

        for (int i = 0; i < 25; i++) {
            jdbcConnectionPool.getConnection();
        }

        while (inUseConnections.size() > 10)
            inUseConnections.get(0).close();

        jdbcConnectionPool.close();

        assertTrue(connectionsPool.size() == 16);

        for (Connection connection : connectionsPool)
            assertTrue(connection.isClosed());

        assertTrue(inUseConnections.size() == 10);

        for (Connection connection : inUseConnections)
            assertTrue(connection.isClosed());
    }
}
