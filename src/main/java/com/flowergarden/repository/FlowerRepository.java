package com.flowergarden.repository;

import com.flowergarden.model.flowers.Flower;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Collection;

public interface FlowerRepository {

    Flower saveOrUpdate(Flower flower) throws SQLException;
    Flower saveOrUpdate(Flower flower, Integer bouquetId) throws SQLException;
    void saveOrUpdateBouquetFlowers(Collection<Flower> flowers, Integer bouquetId) throws SQLException;
    Flower findOne(int id) throws SQLException;
    Iterable<Flower> findAll() throws SQLException;
    Iterable<Flower> findBouquetFlowers(int bouquetId) throws SQLException;
    void delete(int id) throws SQLException;
    void delete(Flower flower) throws SQLException;
    void deleteAll() throws SQLException;
    void deleteBouquetFlowers(int bouquetId) throws SQLException;
    void movePartOfPrice(int fromFlowerId, int toFlowerId, BigDecimal val) throws SQLException;
}
