package com.flowergarden.repository;

import com.flowergarden.model.bouquet.Bouquet;
import com.flowergarden.model.bouquet.MarriedBouquet;
import com.flowergarden.repository.bouquet.BouquetRepository;
import org.flywaydb.core.Flyway;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class BouquetRepositoryTest {

    @Autowired
    private BouquetRepository bouquetRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeClass
    public static void beforeClass() {
        Flyway flyway = new Flyway();
        flyway.setDataSource("jdbc:sqlite:test-base.db", null, null);
        flyway.migrate();
    }

    @Before
    public void before() {
        jdbcTemplate.update("restore from test-base.db");
    }

    @Test(expected = IllegalArgumentException.class)
    public void nullBouquetSaveTest() {
        bouquetRepository.saveOrUpdate(null);
    }

    @Test
    public void bouquetSaveTest() {
        Bouquet bouquet = new MarriedBouquet();
        assertNotNull(bouquetRepository.saveOrUpdate(bouquet).getId());
    }

    @Test
    public void bouquetUpdateTest() {
        Bouquet bouquet = new MarriedBouquet();
        bouquet.setId(1);
        bouquetRepository.saveOrUpdate(bouquet);
    }

    @Test
    public void bouquetFindAllTest() {
        Iterable<Bouquet> bouquets = bouquetRepository.findAll();
        int i = 0;
        for (Bouquet bouquet : bouquets) {
            i++;
            assertNotNull(bouquet.getName());
        }
        assertTrue(i > 0);
    }

    @Test
    public void bouquetDeleteByIdTest() {
        assertTrue(bouquetRepository.exists(1));
        bouquetRepository.delete(1);
        assertFalse(bouquetRepository.exists(1));
    }

    @Test
    public void bouquetDeleteByInstanceTest() {
        bouquetRepository.delete(bouquetRepository.findOne(1));
        assertFalse(bouquetRepository.exists(1));
    }

    @Test
    public void bouquetDeleteAllTest() {
        assertTrue(bouquetRepository.count() > 0);
        bouquetRepository.deleteAll();
        assertTrue(bouquetRepository.count() == 0);
    }

    @Test
    public void bouquetPriceTest() {
        Bouquet bouquet = bouquetRepository.findOne(1);
        assertNotNull(bouquet);
        assertEquals(bouquet.getPrice(), bouquetRepository.getBouquetPrice(1));
    }
}
