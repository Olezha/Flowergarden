package com.flowergarden.repository;

import com.flowergarden.model.flowers.Flower;

import java.sql.SQLException;

public interface FlowerRepository {

    Flower saveOrUpdate(Flower flower) throws SQLException;
    Flower findOne(int id) throws SQLException;
    Iterable<Flower> findAll() throws SQLException;
    void delete(int id) throws SQLException;
    void delete(Flower flower) throws SQLException;
    void deleteAll() throws SQLException;
}
