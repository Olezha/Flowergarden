package com.flowergarden.repository.bouquet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flowergarden.model.bouquet.Bouquet;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;

@Repository("SingleBouquetRepositoryJacksonJsonFile")
public class SingleBouquetRepositoryJacksonJsonFileImpl implements SingleBouquetRepository {

    private String fileName;

    private ObjectMapper objectMapper;

    public SingleBouquetRepositoryJacksonJsonFileImpl(
            @Value("${single-bouquet-repository.json.file.name}") String fileName) {
        this.fileName = fileName;
        objectMapper = new ObjectMapper();
    }

    @Override
    public void save(Bouquet bouquet) {
        try {
            objectMapper.writeValue(new File(fileName), bouquet);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Bouquet read() {
        try {
            return objectMapper.readValue(new File(fileName), Bouquet.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
