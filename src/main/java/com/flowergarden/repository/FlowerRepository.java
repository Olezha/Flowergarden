package com.flowergarden.repository;

import com.flowergarden.model.flower.Flower;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface FlowerRepository extends JpaRepository<Flower, Long> {
}
