package com.flowergarden.repository.bouquet;

import com.flowergarden.model.bouquet.Bouquet;
import com.flowergarden.repository.CrudRepository;

import java.math.BigDecimal;

public interface BouquetRepository extends CrudRepository<Bouquet, Integer> {

    BigDecimal getBouquetPrice(int id);
}
