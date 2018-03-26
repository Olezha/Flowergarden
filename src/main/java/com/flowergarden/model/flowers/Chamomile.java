package com.flowergarden.model.flowers;

import com.flowergarden.model.properties.FreshnessInteger;
import lombok.ToString;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.math.BigDecimal;

@ToString(callSuper = true)
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Chamomile extends GeneralFlower {

    private int petals;

    public Chamomile() {}

    public Chamomile(int petals, int length, BigDecimal price, FreshnessInteger fresh) {
        this.petals = petals;
        this.length = length;
        this.price = price;
        this.freshness = fresh;
    }

    public boolean getPetal() {
        if (petals <= 0) return false;
        petals = -1;
        return true;
    }

    public int getPetals() {
        return petals;
    }
}
