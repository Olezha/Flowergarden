package com.flowergarden.repository;

import com.flowergarden.model.bouquet.Bouquet;
import com.flowergarden.repository.bouquet.SingleBouquetRepository;
import com.flowergarden.service.BouquetService;
import org.flywaydb.core.Flyway;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class SingleBouquetRepositoryTest {

    @Autowired
    @Qualifier("SingleBouquetRepositoryJacksonJsonFile")
    private SingleBouquetRepository jacksonSingleBouquetRepository;

    @Autowired
    @Qualifier("SingleBouquetRepositoryJettisonJsonFile")
    private SingleBouquetRepository jettisonSingleBouquetRepository;

    @Autowired
    private BouquetService bouquetService;

    @BeforeClass
    public static void beforeClass() {
        Flyway flyway = new Flyway();
        flyway.setDataSource("jdbc:sqlite:test-base.db", null, null);
        flyway.migrate();
    }

    @Test
    public void singleBouquetRepositoryJettisonTest() {
        singleBouquetRepositoryTest(jettisonSingleBouquetRepository);
    }

    @Test
    public void singleBouquetRepositoryJacksonTest() {
        singleBouquetRepositoryTest(jacksonSingleBouquetRepository);
    }

    private void singleBouquetRepositoryTest(SingleBouquetRepository singleBouquetRepository) {
        Bouquet bouquet = bouquetService.getBouquet(1);
        singleBouquetRepository.save(bouquet);
        Bouquet bouquetFromJson = singleBouquetRepository.read();

        assertEquals(bouquet, bouquetFromJson);
        assertEquals(bouquet.getPrice(), bouquetFromJson.getPrice());
    }
}
