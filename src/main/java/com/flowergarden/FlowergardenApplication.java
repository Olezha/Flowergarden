package com.flowergarden;

import com.flowergarden.repository.BouquetRepository;
import com.flowergarden.repository.FlowerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FlowergardenApplication implements CommandLineRunner {

    private FlowerRepository flowerRepository;
    private BouquetRepository bouquetRepository;

    @Autowired
    public FlowergardenApplication(
            FlowerRepository flowerRepository,
            BouquetRepository bouquetRepository) {
        this.flowerRepository = flowerRepository;
        this.bouquetRepository = bouquetRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(FlowergardenApplication.class, args);
    }

    @Override
    public void run(String... strings) throws Exception {
        System.out.println(bouquetRepository);
        System.out.println(flowerRepository);
    }
}
