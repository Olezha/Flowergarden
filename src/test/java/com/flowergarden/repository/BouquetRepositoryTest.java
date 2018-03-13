package com.flowergarden.repository;

import com.flowergarden.model.bouquet.Bouquet;
import com.flowergarden.sql.ConnectionPoolJdbcImpl;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExternalResource;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class BouquetRepositoryTest {

    @Autowired
    private BouquetRepository bouquetRepository;

    @Autowired
    private ConnectionPoolJdbcImpl jdbcConnectionPool;

    @Rule
    public ExternalResource resource= new ExternalResource() {
        @Override
        protected void before() throws Throwable {
            try (Statement statement = jdbcConnectionPool.getConnection().createStatement()) {
                statement.executeUpdate("restore from flowergarden.db");
            }
        }
    };

    @Test
    public void bouquetPriceTest() throws SQLException {
        Bouquet bouquet = bouquetRepository.findOne(1);
        assertNotNull(bouquet);
        assertEquals(bouquet.getPrice(), bouquetRepository.getBouquetPrice(1));
    }
}
