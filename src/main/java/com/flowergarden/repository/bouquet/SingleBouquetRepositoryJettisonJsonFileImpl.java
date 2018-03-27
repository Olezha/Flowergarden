package com.flowergarden.repository.bouquet;

import com.flowergarden.model.bouquet.Bouquet;
import com.flowergarden.model.bouquet.MarriedBouquet;
import com.flowergarden.model.flowers.Chamomile;
import com.flowergarden.model.flowers.Rose;
import com.flowergarden.model.flowers.Tulip;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.codehaus.jettison.mapped.Configuration;
import org.codehaus.jettison.mapped.MappedNamespaceConvention;
import org.codehaus.jettison.mapped.MappedXMLStreamReader;
import org.codehaus.jettison.mapped.MappedXMLStreamWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

@Repository("SingleBouquetRepositoryJettisonJsonFile")
public class SingleBouquetRepositoryJettisonJsonFileImpl implements SingleBouquetRepository {

    private final String fileName;
    private final MappedNamespaceConvention mappedNamespaceConvention;
    private final Marshaller marshaller;
    private final Unmarshaller unmarshaller;

    public SingleBouquetRepositoryJettisonJsonFileImpl(
            @Value("${single-bouquet-repository.json.file.name}") String fileName)
            throws JAXBException {
        this.fileName = fileName;

        JAXBContext jaxbContext = JAXBContext.newInstance(Bouquet.class, Chamomile.class, Rose.class, Tulip.class);

        Configuration configuration = new Configuration();
        mappedNamespaceConvention = new MappedNamespaceConvention(configuration);

        marshaller = jaxbContext.createMarshaller();
        unmarshaller = jaxbContext.createUnmarshaller();
    }

    @Override
    public void save(Bouquet bouquet) {
        try(OutputStream outputStream = new FileOutputStream(fileName)) {
            Writer writer = new OutputStreamWriter(outputStream);
            XMLStreamWriter xmlStreamWriter = new MappedXMLStreamWriter(mappedNamespaceConvention, writer);
            marshaller.marshal(bouquet, xmlStreamWriter);
        } catch (IOException | JAXBException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Bouquet read() {
        try {
            byte[] encoded = Files.readAllBytes(Paths.get(fileName));
            JSONObject jsonObject = new JSONObject(new String(encoded, Charset.defaultCharset()));
            XMLStreamReader xmlStreamReader = new MappedXMLStreamReader(jsonObject, mappedNamespaceConvention);
            return (MarriedBouquet) unmarshaller.unmarshal(xmlStreamReader);
        } catch (IOException | JSONException | XMLStreamException | JAXBException e) {
            throw new RuntimeException(e);
        }
    }
}
