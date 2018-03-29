package com.flowergarden;

import com.flowergarden.model.bouquet.Bouquet;
import com.flowergarden.service.BouquetService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Component;

@EnableCaching
@SpringBootApplication
public class FlowergardenApplication implements CommandLineRunner {

    private BouquetService bouquetService;

    @Autowired
    public FlowergardenApplication(
            BouquetService bouquetService) {
        this.bouquetService = bouquetService;
    }

    public static void main(String[] args) {
        SpringApplication.run(FlowergardenApplication.class, args);
    }

    @Override
    public void run(String... strings) {
        System.out.println("Bouquet id1 price is " + bouquetService.getBouquetPrice(1));

        Bouquet bouquet = bouquetService.getBouquet(1);
        System.out.println("     " + bouquet);
        bouquetService.saveToJsonFile(bouquet);
        Bouquet bouquetFromJson = bouquetService.readFromJsonFile();
        System.out.println("json " + bouquetFromJson);
        System.out.println("Bouquet id1 from json price is " + bouquetFromJson.getPrice());
    }
}

@Slf4j
@Component
class BeanPostProcessorImpl implements BeanPostProcessor {

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        log.debug(bean.getClass().getSimpleName());
        return bean;
    }
}
