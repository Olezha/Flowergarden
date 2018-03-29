package com.flowergarden;

import com.flowergarden.service.BouquetService;
import com.flowergarden.web.BouquetServlet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;

@EnableCaching
@SpringBootApplication
public class FlowergardenApplication {

    private BouquetService bouquetService;

    @Autowired
    public FlowergardenApplication(
            BouquetService bouquetService) {
        this.bouquetService = bouquetService;
    }

    public static void main(String[] args) {
        SpringApplication.run(FlowergardenApplication.class, args);
    }

    @Bean
    public ServletRegistrationBean servletRegistrationBean(){
        return new ServletRegistrationBean<>(new BouquetServlet(bouquetService),"/bouquet/*");
    }
}
