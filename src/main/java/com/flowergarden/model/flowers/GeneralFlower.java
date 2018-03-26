package com.flowergarden.model.flowers;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.flowergarden.model.properties.FreshnessInteger;
import lombok.ToString;

import java.math.BigDecimal;

@ToString
@XmlRootElement(name = "Flower")
@XmlAccessorType(XmlAccessType.NONE)
public abstract class GeneralFlower implements Flower<Integer>, Comparable<GeneralFlower> {

    @XmlElement
    private Integer id;

    FreshnessInteger freshness;

    @XmlElement
    BigDecimal price;

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
