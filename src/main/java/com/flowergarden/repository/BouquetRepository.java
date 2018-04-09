package com.flowergarden.repository;

import com.flowergarden.model.bouquet.Bouquet;
import com.flowergarden.model.bouquet.MarriedBouquet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceProcessor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponents;

@RepositoryRestResource(path = "bouquet", collectionResourceRel = "bouquets")
public interface BouquetRepository extends JpaRepository<MarriedBouquet, Integer> {

    @Component
    class BouquetResourceProcessor implements ResourceProcessor<Resource<Bouquet>> {

        @Override
        public Resource<Bouquet> process(Resource<Bouquet> bouquetResource) {
            UriComponents uriComponents = ServletUriComponentsBuilder
                    .fromCurrentContextPath()
                    .path("/rest/bouquet/{id}/freshness-reduce")
                    .buildAndExpand(Long.toString(bouquetResource.getContent().getId()));
            bouquetResource.add(new Link(uriComponents.toUriString(), "freshness-reduce"));
            return bouquetResource;
        }
    }
}
