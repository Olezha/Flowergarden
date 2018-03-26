package com.flowergarden.service;

import com.flowergarden.model.bouquet.Bouquet;

import java.math.BigDecimal;

public interface BouquetService {

    Bouquet getBouquet(Integer id);
    BigDecimal getBouquetPrice(Integer bouquetId);
    void saveToJsonFile(Bouquet bouquet);
    Bouquet readFromJsonFile();
}
