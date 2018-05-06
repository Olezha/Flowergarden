package com.flowergarden.util.factory;

import com.flowergarden.model.flower.Flower;
import org.junit.Assert;
import org.junit.Test;

public class FlowergardenFactoryMethodTest {

    private Flowergarden[] flowergardens = {
            new RoseGarden(), new TulipGarden(), new ChamomileGarden()
    };

    @Test
    public void factoryMethodTest() {
        for (Flowergarden flowergarden : flowergardens) {
            Flower flower = flowergarden.grow();
            Assert.assertNotNull(flower);
        }
    }
}
