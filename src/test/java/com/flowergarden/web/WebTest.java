package com.flowergarden.web;

import com.flowergarden.model.bouquet.Bouquet;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.BDDAssertions.then;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
public class WebTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    public void shouldReturn200_WhenSendingRequestToHomeController() throws Exception {
        @SuppressWarnings("rawtypes")
        ResponseEntity<String> entity = this.testRestTemplate.getForEntity(
                "http://localhost:" + port, String.class);

        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void shouldReturn200_WhenSendingRequestToBouquetServlet() throws Exception {
        @SuppressWarnings("rawtypes")
        ResponseEntity<String> entity = this.testRestTemplate.getForEntity(
                "http://localhost:" + port +"/bouquet", String.class);

        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void shouldReturn200_WhenSendingRequestToBouquetServletWithGetParameterId1() throws Exception {
        @SuppressWarnings("rawtypes")
        ResponseEntity<Bouquet> entity = this.testRestTemplate.getForEntity(
                "http://localhost:" + port +"/bouquet?id=1", Bouquet.class);

        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void shouldReturn422_WhenSendingRequestToBouquetServletWithBsdGetParameterId() throws Exception {
        @SuppressWarnings("rawtypes")
        ResponseEntity<Bouquet> entity = this.testRestTemplate.getForEntity(
                "http://localhost:" + port +"/bouquet?id=0", Bouquet.class);
        then(entity.getStatusCode()).isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY);

        entity = this.testRestTemplate.getForEntity(
                "http://localhost:" + port +"/bouquet?id=-1", Bouquet.class);
        then(entity.getStatusCode()).isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY);

        entity = this.testRestTemplate.getForEntity(
                "http://localhost:" + port +"/bouquet?id=1a", Bouquet.class);
        then(entity.getStatusCode()).isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY);

        entity = this.testRestTemplate.getForEntity(
                "http://localhost:" + port +"/bouquet?id=abc", Bouquet.class);
        then(entity.getStatusCode()).isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY);
    }
}
