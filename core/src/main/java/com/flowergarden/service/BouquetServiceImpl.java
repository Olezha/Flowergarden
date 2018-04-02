package com.flowergarden.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.flowergarden.model.bouquet.Bouquet;
import com.flowergarden.model.flower.Chamomile;
import com.flowergarden.model.flower.Flower;
import com.flowergarden.model.flower.Rose;
import com.flowergarden.model.flower.Tulip;
import com.flowergarden.repository.bouquet.BouquetRepository;
import com.flowergarden.repository.bouquet.SingleBouquetRepository;
import org.codehaus.jettison.mapped.Configuration;
import org.codehaus.jettison.mapped.MappedNamespaceConvention;
import org.codehaus.jettison.mapped.MappedXMLStreamWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import java.io.Writer;
import java.math.BigDecimal;

@Service
public class BouquetServiceImpl implements BouquetService {

    private final BouquetRepository bouquetRepository;

    private final SingleBouquetRepository singleBouquetRepository;

    @Autowired
    public BouquetServiceImpl(
            BouquetRepository bouquetRepository,
            @Qualifier("SingleBouquetRepositoryJettisonJsonFile") SingleBouquetRepository singleBouquetRepository) {
        this.bouquetRepository = bouquetRepository;
        this.singleBouquetRepository = singleBouquetRepository;
    }

    @Override
    public Bouquet getBouquet(Integer id) {
        return bouquetRepository.findOneEager(id);
    }

    @Override
    public Iterable<Bouquet> findAll() {
        return bouquetRepository.findAll();
    }

    @Override
    public String getBouquetJson(Integer id) {
        try {
            return new ObjectMapper().writeValueAsString(bouquetRepository.findOneEager(id));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void save(Bouquet<Flower> bouquet) {
        bouquetRepository.saveOrUpdate(bouquet);
    }

    @Override
    public void writeBouquetJson(Integer id, Writer writer) {
        try {
            JAXBContext
                    .newInstance(Bouquet.class, Chamomile.class, Rose.class, Tulip.class)
                    .createMarshaller()
                    .marshal(bouquetRepository.findOneEager(id),
                            new MappedXMLStreamWriter(new MappedNamespaceConvention(new Configuration()), writer));
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public BigDecimal getBouquetPrice(Integer id) {
        return bouquetRepository.getBouquetPrice(1);
    }

    @Override
    public void saveToJsonFile(Bouquet bouquet) {
        singleBouquetRepository.save(bouquet);
    }

    @Override
    public Bouquet readFromJsonFile() {
        return singleBouquetRepository.read();
    }

    @Override
    public boolean exists(Integer id) {
        return bouquetRepository.exists(id);
    }
}
