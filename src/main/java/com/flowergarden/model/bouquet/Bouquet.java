package com.flowergarden.model.bouquet;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.flowergarden.model.flowers.Flower;

import javax.xml.bind.annotation.*;
import java.math.BigDecimal;
import java.util.Collection;

@XmlTransient
@XmlSeeAlso(MarriedBouquet.class)
@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS, include=JsonTypeInfo.As.PROPERTY, property="@class")
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

    BigDecimal getAssemblePrice();
}
