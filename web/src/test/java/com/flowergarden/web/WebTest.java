package com.flowergarden.web;

import com.flowergarden.model.bouquet.Bouquet;
import com.flowergarden.model.flower.Chamomile;
import com.flowergarden.model.flower.Rose;
import com.flowergarden.model.flower.Tulip;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.codehaus.jettison.mapped.Configuration;
import org.codehaus.jettison.mapped.MappedNamespaceConvention;
import org.codehaus.jettison.mapped.MappedXMLStreamReader;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.PostConstruct;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.stream.XMLStreamException;

import static org.assertj.core.api.BDDAssertions.then;
import static org.junit.Assert.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
public class WebTest {

    @LocalServerPort
    private int port;

    @Autowired
    RestTemplateBuilder restTemplateBuilder;

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
    public void shouldReturn200_WhenSendingRequestToBouquetServlet() throws Exception {
        ResponseEntity<String> entity = testRestTemplate.getForEntity("/bouquet", String.class);

        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void shouldReturn200_WhenSendingRequestToBouquetServletWithGetParameterId1() throws Exception {
        ResponseEntity<String> entity = testRestTemplate.getForEntity("/bouquet?id=1", String.class);

        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void retrieveBouquetId1Test() throws JAXBException, JSONException, XMLStreamException {
        ResponseEntity<String> entity = testRestTemplate.getForEntity("/bouquet?id=1", String.class);
        Bouquet bouquet = (Bouquet) JAXBContext
                .newInstance(Bouquet.class, Chamomile.class, Rose.class, Tulip.class)
                .createUnmarshaller()
                .unmarshal(new MappedXMLStreamReader(
                        new JSONObject(entity.getBody()),
                        new MappedNamespaceConvention(new Configuration())));
        assertSame(1, bouquet.getId());
    }

    @Test
    public void shouldReturn422_WhenSendingRequestToBouquetServletWithBsdGetParameterId() throws Exception {
        ResponseEntity<Bouquet> entity = testRestTemplate.getForEntity("/bouquet?id=0", Bouquet.class);
        then(entity.getStatusCode()).isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY);

        entity = this.testRestTemplate.getForEntity("/bouquet?id=-1", Bouquet.class);
        then(entity.getStatusCode()).isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY);

        entity = this.testRestTemplate.getForEntity("/bouquet?id=1a", Bouquet.class);
        then(entity.getStatusCode()).isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY);

        entity = this.testRestTemplate.getForEntity("/bouquet?id=abc", Bouquet.class);
        then(entity.getStatusCode()).isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY);
    }
}
