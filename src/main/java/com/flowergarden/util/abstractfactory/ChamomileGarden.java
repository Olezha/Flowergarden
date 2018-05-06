package com.flowergarden.util.abstractfactory;

import com.flowergarden.model.flower.Chamomile;
import com.flowergarden.model.flower.Flower;
import com.flowergarden.model.property.FreshnessInteger;

public class ChamomileGarden implements Flowergarden {

    @Override
    public Flower growWithFreshness5() {
        FreshnessInteger freshness = new FreshnessInteger();
        freshness.setFreshness(5);
        Chamomile chamomile = new Chamomile();
        chamomile.setFreshness(freshness);
        return chamomile;
    }

    @Override
    public Flower growWithFreshness10() {
        FreshnessInteger freshness = new FreshnessInteger();
        freshness.setFreshness(10);
        Chamomile chamomile = new Chamomile();
        chamomile.setFreshness(freshness);
        return chamomile;
    }

    @Override
    public Flower growWithFreshness15() {
        FreshnessInteger freshness = new FreshnessInteger();
        freshness.setFreshness(15);
        Chamomile chamomile = new Chamomile();
        chamomile.setFreshness(freshness);
        return chamomile;
    }
}
