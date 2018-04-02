package com.flowergarden.web;

import com.flowergarden.model.bouquet.Bouquet;
import com.flowergarden.model.flower.Flower;
import com.flowergarden.repository.flower.FlowerRepository;
import com.flowergarden.service.BouquetService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;
import java.util.Collection;

@Slf4j
@Data
@Path("/bouquet")
public class BouquetRest {

    private BouquetService bouquetService;
    private FlowerRepository flowerRepository;

    @GET
    @Path("/{id}/price")
    public BigDecimal getBouquetPrice(@PathParam("id") int bouquetId) {
        log.info("bouquetId {}", bouquetId);
        if (!bouquetService.exists(bouquetId))
            return null;
        return bouquetService.getBouquetPrice(bouquetId);
    }

    @PUT
    @Path("/{id}/freshness-reduce")
    public Response reduceFreshness(int bouquetId) {
        Bouquet<Flower> bouquet = bouquetService.getBouquet(bouquetId);
        try {
            for (Flower flower : bouquet.getFlowers()) {
                flower.getFreshness().reduce();
            }
        } catch (UnsupportedOperationException e) {
            return Response.status(551).build();
        }
        bouquetService.save(bouquet);
        return Response.status(411).build();
    }

    @GET
    @Path("/flower/{id}")
    public Flower getFlower(int flowerId) {
        return flowerRepository.findOne(flowerId);
    }

    @GET
    @Path("/{id}/flowers")
    public Collection<Flower> getFlowers(int bouquetId) {
        return bouquetService.getBouquet(bouquetId).getFlowers();
    }
}
