package com.flowergarden.util.flower.abstractfactory;

import com.flowergarden.model.flower.Flower;
import com.flowergarden.model.flower.Rose;
import com.flowergarden.model.property.FreshnessInteger;

public class RoseGarden implements Flowergarden {

    @Override
    public Flower growWithFreshness5() {
        FreshnessInteger freshness = new FreshnessInteger();
        freshness.setFreshness(5);
        Rose rose = new Rose();
        rose.setFreshness(freshness);
        return rose;
    }

    @Override
    public Flower growWithFreshness10() {
        FreshnessInteger freshness = new FreshnessInteger();
        freshness.setFreshness(10);
        Rose rose = new Rose();
        rose.setFreshness(freshness);
        return rose;
    }

    @Override
    public Flower growWithFreshness15() {
        FreshnessInteger freshness = new FreshnessInteger();
        freshness.setFreshness(15);
        Rose rose = new Rose();
        rose.setFreshness(freshness);
        return rose;
    }
}
