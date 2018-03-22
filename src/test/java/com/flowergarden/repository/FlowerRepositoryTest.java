package com.flowergarden.repository;

import com.flowergarden.model.flowers.Flower;
import com.flowergarden.repository.flower.FlowerRepository;
import org.flywaydb.core.Flyway;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class FlowerRepositoryTest {

    @Autowired
    FlowerRepository flowerRepository;

    @Autowired
    CacheManager cacheManager;

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

        clearCache();
    }

    @Test
    public void findOneFlowerTest() throws SQLException {
        assertNotNull(flowerRepository.findOne(1));
    }

    @Test(expected = EmptyResultDataAccessException.class)
    public void deleteFlowerTest() throws SQLException {
        flowerRepository.delete(flowerRepository.findOne(1));
        flowerRepository.findOne(1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void deleteFlowerWithoutIdTest() throws SQLException {
        Flower flower = flowerRepository.findOne(1);
        flower.setId(null);
        flowerRepository.delete(flower);
    }

    @Test(expected = EmptyResultDataAccessException.class)
    public void deleteAllTest() throws SQLException {
        flowerRepository.deleteAll();
        flowerRepository.findOne(1);
    }

    @Test
    public void shouldReturnCachedFlowerTest() throws SQLException {
        assertSame(flowerRepository.findOne(1), flowerRepository.findOne(1));
    }

    @Test
    public void shouldReturnNewFlowerAfterClearingCacheTest() throws SQLException {
        Flower flower = flowerRepository.findOne(1);
        clearCache();
        assertNotSame(flower, flowerRepository.findOne(1));
    }

    private void clearCache() {
        cacheManager.getCacheNames().stream()
                .map(cacheManager::getCache).forEach(Cache::clear);
    }
}
