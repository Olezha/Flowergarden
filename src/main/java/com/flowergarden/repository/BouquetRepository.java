package com.flowergarden.repository;

import com.flowergarden.model.bouquet.Bouquet;

public interface BouquetRepository {

    Bouquet saveOrUpdate(Bouquet flower);
    Bouquet findOne(int id);
    Iterable<Bouquet> findAll();
    void delete(int id);
    void delete(Bouquet flower);
    void deleteAll();
}
