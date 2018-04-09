package com.flowergarden.repository.concreteflower;

import com.flowergarden.model.flower.Tulip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface TulipRepository extends JpaRepository<Tulip, Integer> {
}
