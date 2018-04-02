package com.flowergarden.web;

import com.flowergarden.model.flower.Flower;
import com.flowergarden.repository.flower.FlowerRepository;
import com.flowergarden.service.BouquetService;
import lombok.Data;

import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import java.math.BigDecimal;
import java.util.Collection;

@Data
@Path("/bouquet")
public class BouquetRest {

    private BouquetService bouquetService;
    private FlowerRepository flowerRepository;

    @GET
    @Path("/{id}/price")
    public BigDecimal getBouquetPrice(@PathParam("id") int bouquetId) {
        return bouquetService.getBouquetPrice(bouquetId);
    }

    @PUT
    @Path("/{id}")
    public void reduceFreshness(int bouquetId) {
        // if freshness 0 return 5xx
    }

    @GET
    @Path("/flower/{id}")
    public Flower getFlower(int flowerId) {
        return flowerRepository.findOne(flowerId);
    }

    @GET
    @Path("/{id}")
    public Collection<Flower> getFlowers(int bouquetId) {
        return bouquetService.getBouquet(bouquetId).getFlowers();
    }
}
