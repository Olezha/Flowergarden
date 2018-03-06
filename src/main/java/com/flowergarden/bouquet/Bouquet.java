package com.flowergarden.bouquet;

import com.flowergarden.flowers.Flower;

import java.math.BigDecimal;
import java.util.Collection;

public interface Bouquet<T> {

    BigDecimal getPrice();

    void addFlower(T flower);

    Collection<Flower> searchFlowersByLength(int start, int end);

    void sortByFreshness();

    Collection<Flower> getFlowers();
}
