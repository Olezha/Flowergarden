package com.flowergarden.sql.mockTest;

import com.flowergarden.sql.Connection;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.core.env.Environment;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ConnectionPoolJdbcImplWhiteMockTest {

    @Mock
    private Environment environment;

    @Test
    public void poolGetAndReturnConnectionsWhiteTest() throws Exception {
        when(environment.getRequiredProperty("datasource.url")).thenReturn("jdbc:sqlite:");
        com.flowergarden.sql.ConnectionPoolJdbcImpl jdbcConnectionPool = new com.flowergarden.sql.ConnectionPoolJdbcImpl(environment);

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
            assertFalse(isClosed(connection));

        assertSame(totalConnections + 2, connectionsPool.size());
        assertTrue(inUseConnections.isEmpty());

        for (Connection connection : connectionsPool) {
            closeConnection(connection);
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
            assertTrue(isClosed(connection));

        assertTrue(inUseConnections.size() == 10);

        for (Connection connection : inUseConnections)
            assertTrue(isClosed(connection));
    }

    private void closeConnection(Connection connection) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = connection.getClass().getDeclaredMethod("closeConnection");
        method.setAccessible(true);
        method.invoke(connection);
    }

    private boolean isClosed(Connection connection) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = connection.getClass().getDeclaredMethod("isClosed");
        method.setAccessible(true);
        return (boolean) method.invoke(connection);
    }
}
