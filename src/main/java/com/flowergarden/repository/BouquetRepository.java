package com.flowergarden.repository;

import com.flowergarden.model.bouquet.Bouquet;

import java.sql.SQLException;

public interface BouquetRepository {

    Bouquet saveOrUpdate(Bouquet flower) throws SQLException;
    Bouquet findOne(int id) throws SQLException;
    Iterable<Bouquet> findAll() throws SQLException;
    void delete(int id) throws SQLException;
    void delete(Bouquet flower) throws SQLException;
    void deleteAll() throws SQLException;
}
