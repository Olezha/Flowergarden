package com.flowergarden;

import com.flowergarden.service.BouquetService;
import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

@SpringBootApplication
public class FlowergardenApplication implements CommandLineRunner {

    private BouquetService bouquetService;

    private String datasourceUrl;

    @Autowired
    public FlowergardenApplication(
            BouquetService bouquetService,
            Environment environment) {
        this.bouquetService = bouquetService;
        datasourceUrl = environment.getRequiredProperty("datasource.url");
    }

    public static void main(String[] args) {
        SpringApplication.run(FlowergardenApplication.class, args);
    }

    @Override
    public void run(String... strings) {
        // TODO
//        Flyway flyway = new Flyway();
//        flyway.setDataSource(datasourceUrl, null, null);
//        flyway.migrate();

        System.out.println("Bouquet id1 price is " + bouquetService.getBouquetPrice(1));
    }
}
