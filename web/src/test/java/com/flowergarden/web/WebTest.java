package com.flowergarden.web;

import com.flowergarden.repository.bouquet.BouquetRepository;
import org.flywaydb.core.Flyway;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.PostConstruct;

import static org.assertj.core.api.BDDAssertions.then;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
public class WebTest {

    @LocalServerPort
    private int port;

    @Autowired
    RestTemplateBuilder restTemplateBuilder;

    @Autowired
    private BouquetRepository bouquetRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeClass
    public static void beforeClass() {
        Flyway flyway = new Flyway();
        flyway.setDataSource("jdbc:sqlite:test-base.db", null, null);
        flyway.migrate();
    }

    @Before
    public void before() {
        jdbcTemplate.update("restore from test-base.db");
    }

    private TestRestTemplate testRestTemplate;

    @PostConstruct
    public void initialize() {
        testRestTemplate = new TestRestTemplate(restTemplateBuilder.rootUri("http://localhost:"+port));
    }

    @Test
    public void shouldReturn200_WhenSendingRequestToHomeController() throws Exception {
        ResponseEntity<String> entity = testRestTemplate.getForEntity("/", String.class);

        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void shouldReturn200_WhenSendingRequestToBouquetRest_Id1Price() throws Exception {
        ResponseEntity<String> entity = testRestTemplate.getForEntity("/rest/bouquet/1/price", String.class);
        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void shouldReturn204_WhenSendingRequestToBouquetRest_Id0Price() throws Exception {
        ResponseEntity<String> entity = testRestTemplate.getForEntity("/rest/bouquet/0/price", String.class);
        then(entity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }
}
