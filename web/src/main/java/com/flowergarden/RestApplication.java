package com.flowergarden;

import com.flowergarden.repository.flower.FlowerRepository;
import com.flowergarden.service.BouquetService;
import com.flowergarden.web.BouquetRest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.annotation.PostConstruct;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

@Component
public class RestApplication extends Application {

    @Autowired
    private BouquetService bouquetService;

    @Autowired
    private FlowerRepository flowerRepository;

    private static Set<Object> singletons = new HashSet<>();

    public RestApplication() {
    }

    @PostConstruct
    public void postConstruct() {
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
        BouquetRest bouquetRest = new BouquetRest();
        bouquetRest.setBouquetService(bouquetService);
        bouquetRest.setFlowerRepository(flowerRepository);
        singletons.add(bouquetRest);
    }

    @Override
    public Set<Object> getSingletons() {
        return singletons;
    }
}
