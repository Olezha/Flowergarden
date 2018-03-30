package com.flowergarden.model.flower;

import com.flowergarden.model.property.Freshness;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import java.math.BigDecimal;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public interface Flower<T> {

    Integer getId();
    void setId(Integer id);
    Freshness<T> getFreshness();
    int getLength();
    BigDecimal getPrice();
}
