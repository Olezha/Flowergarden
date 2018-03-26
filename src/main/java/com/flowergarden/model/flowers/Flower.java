package com.flowergarden.model.flowers;

import com.flowergarden.model.properties.Freshness;

import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlTransient;
import java.math.BigDecimal;

@XmlTransient
@XmlSeeAlso(GeneralFlower.class)
public interface Flower<T> {

    Integer getId();

    void setId(Integer id);

    Freshness<T> getFreshness();

    BigDecimal getPrice();

    int getLength();
}
