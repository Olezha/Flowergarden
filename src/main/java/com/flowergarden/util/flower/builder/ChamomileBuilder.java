package com.flowergarden.util.flower.builder;

import com.flowergarden.model.flower.Chamomile;
import com.flowergarden.model.property.FreshnessInteger;

import java.math.BigDecimal;

public class ChamomileBuilder {

    private Chamomile chamomile = new Chamomile();

    public Chamomile build() {
        return chamomile;
    }

    public ChamomileBuilder setFreshness(int freshness) {
        FreshnessInteger freshnessInteger = new FreshnessInteger();
        freshnessInteger.setFreshness(freshness);
        chamomile.setFreshness(freshnessInteger);
        return this;
    }

    public ChamomileBuilder setLength(int length) {
        chamomile.setLength(length);
        return this;
    }

    public ChamomileBuilder setPrice(long priceKop) {
        chamomile.setPrice(BigDecimal.valueOf(priceKop).movePointLeft(2));
        return this;
    }

    public ChamomileBuilder setPetals(int petals) {
        chamomile.setPetals(petals);
        return this;
    }
}
