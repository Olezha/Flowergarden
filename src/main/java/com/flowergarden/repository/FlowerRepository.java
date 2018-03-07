package com.flowergarden.repository;

import com.flowergarden.model.flowers.Flower;

import java.sql.SQLException;

public interface FlowerRepository {

    Flower saveOrUpdate(Flower flower) throws Exception;
    Flower findOne(int id) throws Exception;
    Iterable<Flower> findAll() throws Exception;
    void delete(int id) throws Exception;
    void delete(Flower flower) throws Exception;
    void deleteAll() throws Exception;
}
