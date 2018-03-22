package com.flowergarden.service;

import com.flowergarden.repository.bouquet.BouquetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class BouquetServiceImpl implements BouquetService {

    private final BouquetRepository bouquetRepository;

    @Autowired
    public BouquetServiceImpl(BouquetRepository bouquetRepository) {
        this.bouquetRepository = bouquetRepository;
    }

    @Override
    public BigDecimal getBouquetPrice(Integer id) {
        return bouquetRepository.getBouquetPrice(1);
    }
}
