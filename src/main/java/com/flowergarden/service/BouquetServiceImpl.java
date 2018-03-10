package com.flowergarden.service;

import com.flowergarden.repository.BouquetRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.SQLException;

@Service
public class BouquetServiceImpl implements BouquetService {

    private BouquetRepository bouquetRepository;
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public BouquetServiceImpl(BouquetRepository bouquetRepository) {
        this.bouquetRepository = bouquetRepository;
    }

    @Override
    public BigDecimal getBouquetPrice(Integer id) {
        try {
            return bouquetRepository.getBouquetPrice(1);
        } catch (SQLException e) {
            log.error("{}", e);
            return null;
        }
    }
}
