package com.flowergarden.repository;

import com.flowergarden.model.flowers.Flower;
import com.flowergarden.sql.ConnectionPoolJdbcImpl;
import org.junit.Rule;
import org.junit.Test;
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

    @Rule
    public ExternalResource resource= new ExternalResource() {
        @Override
        protected void before() throws Throwable {
            try (Statement statement = jdbcConnectionPool.getConnection().createStatement()) {
                statement.executeUpdate("restore from flowergarden.db");
            }

            cacheManager.getCacheNames().stream()
                    .map(cacheManager::getCache).forEach(Cache::clear);
        }
    };

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

        assertTrue(totalPrice.equals(flower1v2.getPrice().add(flower2v2.getPrice())));
    }
}
