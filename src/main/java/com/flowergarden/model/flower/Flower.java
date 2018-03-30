package com.flowergarden.model.flower;

import com.flowergarden.model.property.Freshness;

import java.math.BigDecimal;

public interface Flower<T> {

    Integer getId();
    void setId(Integer id);
    Freshness<T> getFreshness();
    int getLength();
    BigDecimal getPrice();
}
