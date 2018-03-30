package com.flowergarden.repository;

import com.flowergarden.model.bouquet.Bouquet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface BouquetRepository extends JpaRepository<Bouquet, Long> {
}
