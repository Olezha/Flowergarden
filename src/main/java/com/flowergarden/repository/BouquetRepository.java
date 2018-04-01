package com.flowergarden.repository;

import com.flowergarden.model.bouquet.MarriedBouquet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "bouquet", collectionResourceRel = "bouquets")
public interface BouquetRepository extends JpaRepository<MarriedBouquet, Integer> {
}
