package com.flowergarden.model.flowers;

import javax.xml.bind.annotation.*;

import com.flowergarden.model.properties.FreshnessInteger;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.math.BigDecimal;

@EqualsAndHashCode
@ToString
@XmlRootElement
@XmlSeeAlso({Chamomile.class, Rose.class, Tulip.class})
@XmlAccessorType(XmlAccessType.FIELD)
public abstract class GeneralFlower implements Flower<Integer>, Comparable<GeneralFlower> {

    private Integer id;

    FreshnessInteger freshness;

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
