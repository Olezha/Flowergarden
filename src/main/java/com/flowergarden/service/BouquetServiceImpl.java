package com.flowergarden.service;

import com.flowergarden.model.bouquet.Bouquet;
import com.flowergarden.model.flowers.Flower;
import com.flowergarden.repository.BouquetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.SQLException;

@Service
public class BouquetServiceImpl implements BouquetService {

    private BouquetRepository bouquetRepository;

    @Autowired
    public BouquetServiceImpl(BouquetRepository bouquetRepository) {
        this.bouquetRepository = bouquetRepository;
    }

    @Override
    public BigDecimal getBouquetPrice(Integer id) {
        Bouquet<Flower> bouquet;
        try {
            bouquet = bouquetRepository.findOne(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return bouquet.getPrice();
    }
}
