package com.flowergarden;

import com.flowergarden.repository.flower.FlowerRepository;
import com.flowergarden.service.BouquetService;
import com.flowergarden.web.BouquetRest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.annotation.PostConstruct;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

@Slf4j
@Component
public class RestApplication extends Application {

    @Autowired
    private BouquetService bouquetService;

    @Autowired
    private FlowerRepository flowerRepository;

    private static BouquetRest bouquetRest = new BouquetRest();

    public RestApplication() {
    }

    @PostConstruct
    public void postConstruct() {
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
        bouquetRest.setBouquetService(bouquetService);
        bouquetRest.setFlowerRepository(flowerRepository);
        log.info("{}", bouquetRest);
    }

    @Override
    public Set<Object> getSingletons() {
        Set<Object> singletons = new HashSet<>();
        singletons.add(bouquetRest);
        return singletons;
    }
}
