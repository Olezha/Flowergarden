package com.flowergarden;

import com.flowergarden.service.BouquetService;
import lombok.extern.slf4j.Slf4j;
import org.flywaydb.core.Flyway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@EnableCaching
@SpringBootApplication
@ImportResource("classpath:application-context.xml")
@EnableAutoConfiguration(exclude = FlywayAutoConfiguration.class)
public class FlowergardenApplication implements CommandLineRunner {

    private BouquetService bouquetService;

    @Value("${datasource.url}")
    private String datasourceUrl;

    @Autowired
    public FlowergardenApplication(BouquetService bouquetService) {
        this.bouquetService = bouquetService;
    }

    public static void main(String[] args) {
        SpringApplication.run(FlowergardenApplication.class, args);
    }

    @Override
    public void run(String... strings) {
        Flyway flyway = new Flyway();
        flyway.setDataSource(datasourceUrl, null, null);
        flyway.migrate();

        System.out.println("Bouquet id1 price is " + bouquetService.getBouquetPrice(1));
    }
}

@Slf4j
@Component
@Profile("default")
class BeanPostProcessorImpl implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Class<?> clazz = bean.getClass();
        log.info("-- {} {}", clazz.getSimpleName(), beanName);
        // TODO: additional BeanPostProcessor Proxy
        // Object proxy = Proxy.newProxyInstance() ...
        // return proxy;
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
}
