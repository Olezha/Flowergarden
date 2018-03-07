package com.flowergarden.repository;

import com.flowergarden.model.flowers.Flower;

public interface FlowerRepository {

    Flower saveOrUpdate(Flower flower);
    Flower findOne(int id);
    Iterable<Flower> findAll();
    void delete(int id);
    void delete(Flower flower);
    void deleteAll();
}
