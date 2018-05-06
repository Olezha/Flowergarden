package com.flowergarden.util.flower.builder;

import com.flowergarden.model.flower.Rose;
import com.flowergarden.model.property.FreshnessInteger;

import java.math.BigDecimal;

public class RoseBuilder {

    private Rose rose = new Rose();

    public Rose build() {
        return rose;
    }

    public RoseBuilder setFreshness(int freshness) {
        FreshnessInteger freshnessInteger = new FreshnessInteger();
        freshnessInteger.setFreshness(freshness);
        rose.setFreshness(freshnessInteger);
        return this;
    }

    public RoseBuilder setLength(int length) {
        rose.setLength(length);
        return this;
    }

    public RoseBuilder setPrice(long priceKop) {
        rose.setPrice(BigDecimal.valueOf(priceKop).movePointLeft(2));
        return this;
    }

    public RoseBuilder setSpike(boolean spike) {
        rose.setSpike(spike);
        return this;
    }
}
