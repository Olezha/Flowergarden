package com.flowergarden.flowers;

import com.flowergarden.properties.Freshness;

import java.math.BigDecimal;

public interface Flower<T> {

    Freshness<T> getFreshness();

    BigDecimal getPrice();

    int getLength();
}
