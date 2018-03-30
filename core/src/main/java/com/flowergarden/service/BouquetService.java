package com.flowergarden.service;

import com.flowergarden.model.bouquet.Bouquet;

import javax.xml.bind.JAXBException;
import java.io.Writer;
import java.math.BigDecimal;

public interface BouquetService {

    Bouquet getBouquet(Integer id);
    Iterable<Bouquet> findAll();
    BigDecimal getBouquetPrice(Integer bouquetId);
    String getBouquetJson(Integer id);
    void writeBouquetJson(Integer id, Writer writer);
    Bouquet readFromJsonFile();
    void saveToJsonFile(Bouquet bouquet);
    boolean exists(Integer id);
}
