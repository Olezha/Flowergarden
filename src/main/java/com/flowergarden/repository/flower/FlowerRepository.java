package com.flowergarden.repository.flower;

import com.flowergarden.model.flowers.Flower;
import com.flowergarden.repository.CrudRepository;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Collection;

public interface FlowerRepository extends CrudRepository<Flower, Integer> {

    Flower saveOrUpdate(Flower flower, Integer bouquetId) throws SQLException;
    void saveOrUpdateBouquetFlowers(Collection<Flower> flowers, Integer bouquetId) throws SQLException;
    Iterable<Flower> findBouquetFlowers(int bouquetId) throws SQLException;
    void deleteBouquetFlowers(int bouquetId) throws SQLException;
    void transferPartOfPrice(Flower from, Flower to, BigDecimal amount) throws SQLException;
}
