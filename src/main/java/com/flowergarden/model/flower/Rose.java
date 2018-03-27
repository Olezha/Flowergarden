package com.flowergarden.model.flower;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.flowergarden.model.property.FreshnessInteger;
import lombok.ToString;

import java.math.BigDecimal;

@ToString(callSuper = true)
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Rose extends GeneralFlower {

    private boolean spike;

    public Rose() {
    }

    public Rose(boolean spike, int length, BigDecimal price, FreshnessInteger fresh) {
        this.spike = spike;
        this.length = length;
        this.price = price;
        this.freshness = fresh;
    }

    public boolean getSpike() {
        return spike;
    }
}
