package com.flowergarden.repository.bouquet;

import com.flowergarden.model.bouquet.Bouquet;

public interface SingleBouquetRepository {

    void save(Bouquet bouquet);
    Bouquet read();
}
