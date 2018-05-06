package com.flowergarden.util.factory;

import com.flowergarden.model.flower.Flower;
import com.flowergarden.model.flower.Rose;

public class RoseGarden implements Flowergarden {

    @Override
    public Flower grow() {
        return new Rose();
    }
}
