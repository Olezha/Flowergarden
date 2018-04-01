package com.flowergarden.repository;

import com.flowergarden.model.flower.GeneralFlower;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "flower", collectionResourceRel = "flowers")
public interface FlowerRepository extends JpaRepository<GeneralFlower, Integer> {
}
