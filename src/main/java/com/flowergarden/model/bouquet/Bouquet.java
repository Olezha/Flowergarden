package com.flowergarden.model.bouquet;

import com.flowergarden.model.flowers.Flower;

import java.math.BigDecimal;
import java.util.Collection;

public interface Bouquet<T> {

    BigDecimal getPrice();

    String getName();

    void setAssemblePrice(BigDecimal assemblePrice);

    void addFlower(T flower);

    Collection<Flower> searchFlowersByLength(int start, int end);

    void sortByFreshness();

    Collection<Flower> getFlowers();

    Integer getId();

    void setId(Integer id);
}
