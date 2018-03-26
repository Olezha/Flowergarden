package com.flowergarden.model.flowers;

import com.flowergarden.model.properties.FreshnessInteger;
import lombok.ToString;

import java.math.BigDecimal;

@ToString(callSuper = true)
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
