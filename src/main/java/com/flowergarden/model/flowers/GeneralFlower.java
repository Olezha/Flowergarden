package com.flowergarden.model.flowers;

import javax.xml.bind.annotation.XmlElement;

import com.flowergarden.model.properties.FreshnessInteger;

import java.math.BigDecimal;

public abstract class GeneralFlower implements Flower<Integer>, Comparable<GeneralFlower> {

    private Integer id;

    FreshnessInteger freshness;

    @XmlElement
    BigDecimal price;

    @XmlElement
    int length;

    public void setFreshness(FreshnessInteger fr) {
        freshness = fr;
    }

    @Override
    public FreshnessInteger getFreshness() {
        return freshness;
    }

    @Override
    public BigDecimal getPrice() {
        return price;
    }

    @Override
    public int getLength() {
        return length;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public int compareTo(GeneralFlower compareFlower) {
        int compareFresh = compareFlower.getFreshness().getFreshness();
        return this.getFreshness().getFreshness() - compareFresh;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setLength(int length) {
        this.length = length;
    }
}
