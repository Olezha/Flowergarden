package com.flowergarden;

import com.flowergarden.model.bouquet.MarriedBouquet;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.PostConstruct;

import java.math.BigDecimal;

import static org.assertj.core.api.BDDAssertions.then;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class FlowergardenApplicationTests {

    @LocalServerPort
    private int port;

    @Autowired
    private RestTemplateBuilder restTemplateBuilder;

    private TestRestTemplate testRestTemplate;

    @PostConstruct
    public void initialize() {
        testRestTemplate = new TestRestTemplate(
                restTemplateBuilder.rootUri("http://localhost:" + port));
    }

    @Test
    public void shouldReturn200_WhenSendingRequestToRoot() throws Exception {
        ResponseEntity<String> entity =
                testRestTemplate.getForEntity("/", String.class);
        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void retrieveBouquetId1Test() {
        ResponseEntity<MarriedBouquet> responseEntity =
                testRestTemplate.getForEntity("/bouquet/1", MarriedBouquet.class);
        then(responseEntity.getBody().getPrice()).isEqualTo("95.0");
    }
}
