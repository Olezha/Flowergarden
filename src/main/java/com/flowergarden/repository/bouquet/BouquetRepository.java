package com.flowergarden.repository.bouquet;

import com.flowergarden.model.bouquet.Bouquet;
import com.flowergarden.model.flowers.Flower;
import com.flowergarden.repository.CrudRepository;

import java.math.BigDecimal;
import java.sql.SQLException;

public interface BouquetRepository extends CrudRepository<Bouquet<Flower>, Integer> {

    BigDecimal getBouquetPrice(int bouquetId) throws SQLException;
}
