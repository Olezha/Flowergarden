package com.flowergarden.web;

import com.flowergarden.model.bouquet.MarriedBouquet;
import com.flowergarden.model.flower.Flower;
import com.flowergarden.repository.BouquetRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/rest")
public class BouquetRestController {

    private final BouquetRepository bouquetRepository;

    public BouquetRestController(BouquetRepository bouquetRepository) {
        this.bouquetRepository = bouquetRepository;
    }

    @PutMapping("/bouquet/{id}/freshness-reduce")
    public void reduceFreshness(@PathVariable Integer id, HttpServletResponse response) {
        try {
            MarriedBouquet bouquet = bouquetRepository.findById(id).get();
            for (Flower flower : bouquet.getFlowers())
                flower.getFreshness().reduce();
            bouquetRepository.save(bouquet);
        }
        catch (NoSuchElementException e) {
            response.setStatus(HttpStatus.NOT_FOUND.value());
        }
        catch (UnsupportedOperationException e) {
            response.setStatus(551);
        }
    }
}
