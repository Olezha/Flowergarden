package com.flowergarden;

import com.flowergarden.service.BouquetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
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
        System.out.println("Bouquet price is " + bouquetService.getBouquetPrice(1));
    }
}
