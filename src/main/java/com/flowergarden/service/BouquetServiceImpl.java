package com.flowergarden.service;

import com.flowergarden.model.bouquet.Bouquet;
import com.flowergarden.repository.bouquet.BouquetRepository;
import com.flowergarden.repository.bouquet.SingleBouquetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class BouquetServiceImpl implements BouquetService {

    private final BouquetRepository bouquetRepository;

    private final SingleBouquetRepository singleBouquetRepository;

    @Autowired
    public BouquetServiceImpl(
            BouquetRepository bouquetRepository,
            SingleBouquetRepository singleBouquetRepository) {
        this.bouquetRepository = bouquetRepository;
        this.singleBouquetRepository = singleBouquetRepository;
    }

    @Override
    public Bouquet getBouquet(Integer id) {
        return bouquetRepository.findOne(id, true);
    }

    @Override
    public BigDecimal getBouquetPrice(Integer id) {
        return bouquetRepository.getBouquetPrice(1);
    }

    @Override
    public void saveToJsonFile(Bouquet bouquet) {
        singleBouquetRepository.save(bouquet);
    }

    @Override
    public Bouquet readFromJsonFile() {
        return singleBouquetRepository.read();
    }
}
