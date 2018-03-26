package com.flowergarden;

import com.flowergarden.model.flowers.Flower;
import com.flowergarden.model.flowers.GeneralFlower;
import com.flowergarden.repository.flower.FlowerRepository;
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

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

@Slf4j
@EnableCaching
@SpringBootApplication
public class FlowergardenApplication implements CommandLineRunner {

    private BouquetService bouquetService;
    private FlowerRepository flowerRepository;

    @Autowired
    public FlowergardenApplication(
            BouquetService bouquetService,
            FlowerRepository flowerRepository) {
        this.bouquetService = bouquetService;
        this.flowerRepository = flowerRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(FlowergardenApplication.class, args);
    }

    @Override
    public void run(String... strings) throws JAXBException {
        System.out.println("Bouquet id1 price is " + bouquetService.getBouquetPrice(1));

        JAXBContext jaxbContext = JAXBContext.newInstance(GeneralFlower.class);
        Marshaller marshaller = jaxbContext.createMarshaller();

        Iterable<Flower> flowers = flowerRepository.findAll();

        for (Flower flower : flowers) {
            System.out.print(System.lineSeparator() + flower.getId() + " ");
            marshaller.marshal((GeneralFlower) flower, System.out);
        }
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
