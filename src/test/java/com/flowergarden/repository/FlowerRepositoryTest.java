package com.flowergarden.repository;

import com.flowergarden.model.flowers.Flower;
import com.flowergarden.sql.ConnectionPoolJdbcImpl;
import org.flywaydb.core.Flyway;
import org.junit.*;
import org.junit.rules.ExternalResource;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class FlowerRepositoryTest {

    @Autowired
    FlowerRepository flowerRepository;

    @Autowired
    CacheManager cacheManager;

    @Autowired
    private ConnectionPoolJdbcImpl jdbcConnectionPool;

    @BeforeClass
    public static void beforeClass() {
        Flyway flyway = new Flyway();
        flyway.setDataSource("jdbc:sqlite:test-base.db", null, null);
        flyway.migrate();
    }

    @Before
    public void before() throws Throwable {
        try (Statement statement = jdbcConnectionPool.getConnection().createStatement()) {
            statement.executeUpdate("restore from test-base.db");
        }

        cacheManager.getCacheNames().stream()
                .map(cacheManager::getCache).forEach(Cache::clear);
    }

    @Test
    public void findOneFlowerTest() throws SQLException {
        assertNotNull(flowerRepository.findOne(1));
    }

    @Test
    public void deleteFlowerTest() throws SQLException {
        flowerRepository.delete(flowerRepository.findOne(1));
        assertNull(flowerRepository.findOne(1));
    }

    @Test(expected = IllegalArgumentException.class)
    public void deleteFlowerWithoutIdTest() throws SQLException {
        Flower flower = flowerRepository.findOne(1);
        flower.setId(null);
        flowerRepository.delete(flower);
    }

    @Test
    public void deleteAllTest() throws SQLException {
        flowerRepository.deleteAll();
        assertNull(flowerRepository.findOne(1));
    }

    @Test
    public void transferPartOfPriceTest() throws SQLException {
        Flower flower1 = flowerRepository.findOne(1);
        Flower flower2 = flowerRepository.findOne(2);
        BigDecimal totalPrice = flower1.getPrice().add(flower2.getPrice());

        flowerRepository.transferPartOfPrice(flower1, flower2, BigDecimal.ONE);

        Flower flower1v2 = flowerRepository.findOne(1);
        Flower flower2v2 = flowerRepository.findOne(2);

        assertEquals(totalPrice, flower1v2.getPrice().add(flower2v2.getPrice()));
    }

//    @Ignore // travis fail
    @Test
    public void cachingMakesSenseTest() throws SQLException {
        System.gc();
        long firstTime = 0, secondTime = 0;
        for (int i = 1; i < 6; i++) {
            long iFirstTime = System.nanoTime();
            flowerRepository.findOne(i);
            iFirstTime = System.nanoTime() - iFirstTime;
            firstTime += iFirstTime;

            long iSecondTime = System.nanoTime();
            flowerRepository.findOne(i);
            iSecondTime = System.nanoTime() - iSecondTime;
            secondTime += iSecondTime;
        }
        assertTrue(firstTime > secondTime * 2);
    }
}
