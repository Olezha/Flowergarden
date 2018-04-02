package com.flowergarden.web;

import com.flowergarden.model.flower.Flower;
import com.flowergarden.service.BouquetService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import java.math.BigDecimal;
import java.util.List;

@Path("/bouquet")
public class BouquetRest {

    private final BouquetService bouquetService;

    @Autowired
    public BouquetRest(BouquetService bouquetService) {
        this.bouquetService = bouquetService;
    }

    @GET
    @Path("/{id}/price")
    public BigDecimal getBouquetPrice(@PathParam("id") int bouquetId) {
        return bouquetService.getBouquetPrice(bouquetId);
    }

    @PUT
    @Path("/{id}")
    public void downFreshness(int bouquetId) {
        // if freshness 0 return 5xx
    }

    @GET
    @Path("/flower/{id}")
    public Flower getFlower(int flowerId) {
        return null;
    }

    @GET
    @Path("/{id}")
    public List<Flower> getFlowers(int bouquetId) {
        return null;
    }
}
