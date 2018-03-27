package com.flowergarden.repository.bouquet;

import com.flowergarden.model.bouquet.Bouquet;
import com.flowergarden.model.flower.Flower;
import com.flowergarden.repository.CrudRepository;

import java.math.BigDecimal;

public interface BouquetRepository extends CrudRepository<Bouquet, Integer> {

    Bouquet<Flower> findOneEager(Integer id);
    BigDecimal getBouquetPrice(int id);
}
