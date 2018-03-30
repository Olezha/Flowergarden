package com.flowergarden.model.bouquet;

import com.flowergarden.model.flower.Flower;

import java.math.BigDecimal;
import java.util.Collection;

public interface Bouquet<T> {

    void setId(Integer id);
    Integer getId();
    String getName();
    void addFlower(T flower);
    Collection<Flower> getFlowers();
    Collection<Flower> searchFlowersByLength(int start, int end);
    void sortByFreshness();
    void setAssemblePrice(BigDecimal assemblePrice);
    BigDecimal getPrice();
    BigDecimal getAssemblePrice();
}
