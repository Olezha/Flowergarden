package com.flowergarden.repository.concreteflower;

import com.flowergarden.model.flower.Rose;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface RoseRepository extends JpaRepository<Rose, Integer> {
}
