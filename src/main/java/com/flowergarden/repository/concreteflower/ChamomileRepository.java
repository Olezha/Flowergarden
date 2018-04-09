package com.flowergarden.repository.concreteflower;

import com.flowergarden.model.flower.Chamomile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface ChamomileRepository extends JpaRepository<Chamomile, Integer> {
}
