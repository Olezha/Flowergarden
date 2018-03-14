package com.flowergarden;

import com.flowergarden.service.BouquetService;
import org.flywaydb.core.Flyway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

import java.io.IOException;
import java.util.Properties;

@EnableCaching
@SpringBootApplication
public class FlowergardenApplication implements CommandLineRunner {

    private BouquetService bouquetService;
    private static final Logger log = LoggerFactory.getLogger(FlowergardenApplication.class);

    @Autowired
    public FlowergardenApplication(BouquetService bouquetService) {
        this.bouquetService = bouquetService;
    }

    public static void main(String[] args) {
        SpringApplication.run(FlowergardenApplication.class, args);
    }

    @Override
    public void run(String... strings) {
        try {
            Properties properties = new Properties();
            properties.load(getClass().getClassLoader().getResourceAsStream("application.properties"));

            Flyway flyway = new Flyway();
            flyway.setDataSource(properties.getProperty("datasource.url"), null, null);
            flyway.migrate();
        } catch (IOException | NullPointerException e) {
            log.debug("{}", e);
            log.warn("Unable to migrate db");
        }

        System.out.println("Bouquet id1 price is " + bouquetService.getBouquetPrice(1));
    }
}
