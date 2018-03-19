package com.flowergarden;

import com.flowergarden.service.BouquetService;
import org.flywaydb.core.Flyway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ImportResource;

@EnableCaching
@SpringBootApplication
@ImportResource("classpath:application-context.xml")
public class FlowergardenApplication implements CommandLineRunner {

    private BouquetService bouquetService;
    private static final Logger log = LoggerFactory.getLogger(FlowergardenApplication.class);

//    @Value("${datasource.url}")
//    private String datasourceUrl;

    @Autowired
    public FlowergardenApplication(BouquetService bouquetService) {
        this.bouquetService = bouquetService;
    }

    public static void main(String[] args) {
        SpringApplication.run(FlowergardenApplication.class, args);
    }

    @Override
    public void run(String... strings) {
//        Flyway flyway = new Flyway();
//        flyway.setDataSource(datasourceUrl, null, null);
//        flyway.migrate();

        System.out.println("Bouquet id1 price is " + bouquetService.getBouquetPrice(1));
    }
}
