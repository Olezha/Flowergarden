package com.flowergarden;

import com.flowergarden.service.BouquetService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.dbcp2.BasicDataSource;
import org.flywaydb.core.Flyway;
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
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@EnableCaching
@SpringBootApplication
@EnableAutoConfiguration(exclude = FlywayAutoConfiguration.class)
public class FlowergardenApplication implements CommandLineRunner {

    private BouquetService bouquetService;

    @Autowired
    public FlowergardenApplication(BouquetService bouquetService) {
        this.bouquetService = bouquetService;
    }

    public static void main(String[] args) {
        SpringApplication.run(FlowergardenApplication.class, args);
    }

    @Override
    public void run(String... strings) {
        System.out.println("Bouquet id1 price is " + bouquetService.getBouquetPrice(1));
    }
}

@Configuration
class FlywayConfiguration {

    public FlywayConfiguration(@Value("${datasource.url}") String datasourceUrl) {
        Flyway flyway = new Flyway();
        flyway.setDataSource(datasourceUrl, null, null);
        flyway.migrate();
    }
}

@Configuration
class ContextConfiguration {

    @Value("${datasource.url}")
    private String datasourceUrl;

    @Value("${connection.pool.size.init}")
    private int connectionPoolInitialSize;

    @Value("${connection.pool.size.max}")
    private int connectionPoolMaxTotal;

    @Bean(destroyMethod = "")
    DataSource dataSource() {
        BasicDataSource basicDataSource = new BasicDataSource();
        basicDataSource.setUrl(datasourceUrl);
        basicDataSource.setInitialSize(connectionPoolInitialSize);
        basicDataSource.setMaxTotal(connectionPoolMaxTotal);
        return basicDataSource;
    }
}

@Slf4j
@Component
class BeanPostProcessorImpl implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Class<?> clazz = bean.getClass();
        log.debug("-- {} {}", clazz.getSimpleName(), beanName);
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
