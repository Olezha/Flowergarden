package com.flowergarden;

import org.jboss.resteasy.plugins.server.servlet.HttpServletDispatcher;
import org.jboss.resteasy.plugins.server.servlet.ResteasyBootstrap;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jsonb.JsonbAutoConfiguration;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;

import javax.servlet.ServletContextListener;

@EnableCaching
@SpringBootApplication(exclude = JsonbAutoConfiguration.class)
public class FlowergardenApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(FlowergardenApplication.class, args);
    }

    @Bean
    public ServletContextInitializer initializer() {
        return servletContext -> {
            servletContext.setInitParameter("resteasy.scan", "true");
            servletContext.setInitParameter("resteasy.servlet.mapping.prefix", "/rest");
        };
    }

    @Bean
    public ServletContextListener restEasyBootstrap() {
        return new ResteasyBootstrap();
    }

    @Bean
    public ServletRegistrationBean restEasyServlet() {
        final ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean();
        servletRegistrationBean.setServlet(new HttpServletDispatcher());
        servletRegistrationBean.setName("resteasy-servlet");
        servletRegistrationBean.addUrlMappings("/rest/*");
        servletRegistrationBean.addInitParameter("javax.ws.rs.Application", "com.flowergarden.RestApplication");
        return servletRegistrationBean;
    }
}
