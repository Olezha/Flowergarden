package com.flowergarden.repository;

import com.flowergarden.model.bouquet.Bouquet;
import com.flowergarden.model.flowers.Flower;

import java.sql.SQLException;

public interface BouquetRepository {

    Bouquet saveOrUpdate(Bouquet flower) throws SQLException;
    Bouquet<Flower> findOne(int id) throws SQLException;
    Iterable<Bouquet> findAll() throws SQLException;
    void delete(int id) throws SQLException;
    void delete(Bouquet bouquet) throws SQLException;
    void deleteAll() throws SQLException;
}
