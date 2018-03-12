package com.flowergarden.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.SQLException;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class BouquetRepositoryTest {

    @Autowired
    private BouquetRepository bouquetRepository;

    @Test
    public void bouquetPriceTest() throws SQLException {
        assertEquals(bouquetRepository.findOne(1).getPrice(),
                bouquetRepository.getBouquetPrice(1));
    }
}
