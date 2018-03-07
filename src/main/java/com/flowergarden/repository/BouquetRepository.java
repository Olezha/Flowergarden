package com.flowergarden.repository;

import com.flowergarden.model.bouquet.Bouquet;

import java.sql.SQLException;

public interface BouquetRepository {

    Bouquet saveOrUpdate(Bouquet flower) throws Exception;
    Bouquet findOne(int id) throws Exception;
    Iterable<Bouquet> findAll() throws Exception;
    void delete(int id) throws Exception;
    void delete(Bouquet flower) throws Exception;
    void deleteAll() throws Exception;
}
