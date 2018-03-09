package com.flowergarden.model.flowers;

import com.flowergarden.model.properties.Freshness;

import java.math.BigDecimal;

public interface Flower<T> {

    Freshness<T> getFreshness();

    BigDecimal getPrice();

    int getLength();

    Integer getId();

    void setId(Integer id);
}
