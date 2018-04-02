package com.flowergarden.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping
    public String home() {
        return "<a href=\"/rest/bouquet\">Bouquets</a>";
    }
}
