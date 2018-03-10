package com.flowergarden.service;

import com.flowergarden.model.bouquet.Bouquet;
import com.flowergarden.model.flowers.Flower;
import com.flowergarden.repository.BouquetRepository;
import com.flowergarden.repository.FlowerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.SQLException;

@Service
public class BouquetServiceImpl implements BouquetService {

    private FlowerRepository flowerRepository;
    private BouquetRepository bouquetRepository;

    @Autowired
    public BouquetServiceImpl(
            FlowerRepository flowerRepository,
            BouquetRepository bouquetRepository) {
        this.flowerRepository = flowerRepository;
        this.bouquetRepository = bouquetRepository;
    }

    @Override
    public BigDecimal getBouquetPrice(Integer id) {
        Bouquet<Flower> bouquet;
        Iterable<Flower> flowers;

        try {
            bouquet = bouquetRepository.findOne(1);
            flowers = flowerRepository.findBouquetFlowers(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        for (Flower flower : flowers)
            bouquet.addFlower(flower);

        return bouquet.getPrice();
    }
}
