package com.flowergarden.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping
    public String home() {
        return "<a href=\"/rest/bouquet/1/flowers\">Bouquet1 flowers</a><br>" +
                "<a href=\"/rest/bouquet/1/price\">Bouquet1 price</a><br>" +
                "<a href=\"/rest/bouquet/flower/1\">Flower1</a><br>" +
                "Use PUT method for reduce Bouquet freshness; URL: http://localhost:8080/rest/bouquet/1/freshness-reduce";
    }
}
