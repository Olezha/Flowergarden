package com.flowergarden;

import com.flowergarden.repository.BouquetRepository;
import com.flowergarden.repository.FlowerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FlowergardenApplication implements CommandLineRunner {

    @Autowired
    BouquetRepository bouquetRepository;

    @Autowired
    FlowerRepository flowerRepository;

    public static void main(String[] args) {
        SpringApplication.run(FlowergardenApplication.class, args);
    }

    @Override
    public void run(String... strings) throws Exception {
        System.out.println(bouquetRepository);
        System.out.println(flowerRepository);
    }
}
