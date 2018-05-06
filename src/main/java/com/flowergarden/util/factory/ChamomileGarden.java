package com.flowergarden.util.factory;

import com.flowergarden.model.flower.Chamomile;
import com.flowergarden.model.flower.Flower;

public class ChamomileGarden implements Flowergarden {

    @Override
    public Flower grow() {
        return new Chamomile();
    }
}
