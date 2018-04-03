package com.flowergarden.web;

import com.flowergarden.model.bouquet.Bouquet;
import com.flowergarden.model.flower.Flower;
import com.flowergarden.repository.flower.FlowerRepository;
import com.flowergarden.service.BouquetService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
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
    public Response reduceFreshness(@PathParam("id") int bouquetId) {
        Bouquet<Flower> bouquet = bouquetService.getBouquet(bouquetId);
        try {
            for (Flower flower : bouquet.getFlowers()) {
                flower.getFreshness().reduce();
            }
        } catch (UnsupportedOperationException e) {
            return Response.status(551).entity(e.getMessage()).build();
        }
        bouquetService.save(bouquet);
        return Response.status(HttpStatus.OK.value()).entity("OK").build();
    }

    @GET
    @Path("/flower/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Flower getFlower(@PathParam("id") int flowerId) {
        return flowerRepository.findOne(flowerId);
    }

    @GET
    @Path("/{id}/flowers")
    @Produces(MediaType.APPLICATION_JSON)
    public Collection<Flower> getFlowers(@PathParam("id") int bouquetId) {
        Bouquet bouquet = bouquetService.getBouquet(bouquetId);
        if (bouquet == null)
            return null;

        return bouquet.getFlowers();
    }
}
