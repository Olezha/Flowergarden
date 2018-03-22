package com.flowergarden.repository.flower;

import com.flowergarden.model.flowers.Flower;
import com.flowergarden.repository.CrudRepository;

import java.util.Collection;

public interface FlowerRepository extends CrudRepository<Flower, Integer> {

    Flower saveOrUpdate(Flower flower, Integer bouquetId);
    void saveOrUpdateBouquetFlowers(Collection<Flower> flowers, Integer bouquetId);
    Iterable<Flower> findBouquetFlowers(int bouquetId);
    void deleteBouquetFlowers(int bouquetId);
}
