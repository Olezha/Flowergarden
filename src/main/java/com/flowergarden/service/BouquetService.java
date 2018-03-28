package com.flowergarden.service;

import com.flowergarden.model.bouquet.Bouquet;

import java.math.BigDecimal;

public interface BouquetService {

    Bouquet getBouquet(Integer id);
    Iterable<Bouquet> findAll();
    BigDecimal getBouquetPrice(Integer bouquetId);
    String getBouquetJson(Integer id);
    Bouquet readFromJsonFile();
    void saveToJsonFile(Bouquet bouquet);
    boolean exists(Integer id);
}
