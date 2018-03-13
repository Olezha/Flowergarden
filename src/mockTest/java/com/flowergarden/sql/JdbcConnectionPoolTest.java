package com.flowergarden.sql;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.core.env.Environment;

import java.lang.reflect.Field;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class JdbcConnectionPoolTest {

    @Mock
    private Environment environment;

    @Test
    public void poolGetAndReturnConnectionsWhiteTest() throws Exception {
        when(environment.getRequiredProperty("datasource.url")).thenReturn("jdbc:sqlite:");
        ConnectionPoolJdbcImpl jdbcConnectionPool = new ConnectionPoolJdbcImpl(environment);

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
            Connection jdbcConnectionForPool = (Connection) connection;
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
