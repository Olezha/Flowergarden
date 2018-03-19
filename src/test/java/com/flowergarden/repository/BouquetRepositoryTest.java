package com.flowergarden.repository;

import com.flowergarden.model.bouquet.Bouquet;
import com.flowergarden.repository.bouquet.BouquetRepository;
import org.flywaydb.core.Flyway;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class BouquetRepositoryTest {

    @Autowired
    private BouquetRepository bouquetRepository;

    @Autowired
    private DataSource dataSource;

    @BeforeClass
    public static void beforeClass() {
        Flyway flyway = new Flyway();
        flyway.setDataSource("jdbc:sqlite:test-base.db", null, null);
        flyway.migrate();
    }

    @Before
    public void before() throws Throwable {
        try (Statement statement = dataSource.getConnection().createStatement()) {
            statement.executeUpdate("restore from test-base.db");
        }
    }

    @Test
    public void bouquetPriceTest() throws SQLException {
        Bouquet bouquet = bouquetRepository.findOne(1);
        assertNotNull(bouquet);
        assertEquals(bouquet.getPrice(), bouquetRepository.getBouquetPrice(1));
    }
}
