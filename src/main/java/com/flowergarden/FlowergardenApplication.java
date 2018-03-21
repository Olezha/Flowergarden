package com.flowergarden;

import com.flowergarden.service.BouquetService;
import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;

@EnableCaching
@SpringBootApplication
@EnableAutoConfiguration(exclude = FlywayAutoConfiguration.class)
public class FlowergardenApplication implements CommandLineRunner {

    private BouquetService bouquetService;

    @Autowired
    public FlowergardenApplication(BouquetService bouquetService) {
        this.bouquetService = bouquetService;
    }

    public static void main(String[] args) {
        SpringApplication.run(FlowergardenApplication.class, args);
    }

    @Override
    public void run(String... strings) {
        System.out.println("Bouquet id1 price is " + bouquetService.getBouquetPrice(1));
    }
}

@Configuration
class FlywayConfiguration {

    public FlywayConfiguration(@Value("${datasource.url}") String datasourceUrl) {
        Flyway flyway = new Flyway();
        flyway.setDataSource(datasourceUrl, null, null);
        flyway.migrate();
    }
}
