package com.flowergarden.repository;

import com.flowergarden.model.flowers.Flower;
import com.flowergarden.model.flowers.Tulip;
import com.flowergarden.repository.flower.FlowerRepository;
import org.flywaydb.core.Flyway;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

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
        clearCache();
    }

    @Test(expected = IllegalArgumentException.class)
    public void nullFlowerSaveTest() {
        flowerRepository.saveOrUpdate(null);
    }

    @Test
    public void flowerSaveTest() {
        Flower flower = new Tulip();
        assertNotNull(flowerRepository.saveOrUpdate(flower).getId());
    }

    @Test
    public void flowerUpdateTest() {
        Flower flower = new Tulip();
        flower.setId(1);
        flowerRepository.saveOrUpdate(flower);
    }

    @Test
    public void findOneFlowerTest() {
        assertNotNull(flowerRepository.findOne(1));
    }

    @Test
    public void flowerFindAllTest() {
        Iterable<Flower> flowers = flowerRepository.findAll();
        int i = 0;
        for (Flower flower : flowers) {
            i++;
            assertNotNull(flower.getId());
        }
        assertTrue(i > 0);
    }

    @Test
    public void deleteFlowerTest() {
        assertTrue(flowerRepository.exists(1));
        flowerRepository.delete(flowerRepository.findOne(1));
        assertFalse(flowerRepository.exists(1));
    }

    @Test(expected = IllegalArgumentException.class)
    public void deleteFlowerWithoutIdTest() {
        Flower flower = flowerRepository.findOne(1);
        flower.setId(null);
        flowerRepository.delete(flower);
    }

    @Test
    public void deleteAllTest() {
        assertTrue(flowerRepository.count() > 0);
        flowerRepository.deleteAll();
        assertTrue(flowerRepository.count() == 0);
    }

    @Test
    public void shouldReturnCachedFlowerTest() {
        assertSame(flowerRepository.findOne(1), flowerRepository.findOne(1));
    }

    @Test
    public void shouldReturnNewFlowerAfterClearingCacheTest() {
        Flower flower = flowerRepository.findOne(1);
        clearCache();
        assertNotSame(flower, flowerRepository.findOne(1));
    }

    private void clearCache() {
        cacheManager.getCacheNames().stream()
                .map(cacheManager::getCache).forEach(Cache::clear);
    }
}
