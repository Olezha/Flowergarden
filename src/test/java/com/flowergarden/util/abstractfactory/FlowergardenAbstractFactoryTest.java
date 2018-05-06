package com.flowergarden.util.abstractfactory;

import org.junit.Assert;
import org.junit.Test;

public class FlowergardenAbstractFactoryTest {

    private Flowergarden[] flowergardens = {
            new RoseGarden(), new ChamomileGarden()
    };

    @Test
    public void abstractFactoryTest() {
        for (Flowergarden flowergarden : flowergardens) {
            Assert.assertEquals(5, flowergarden.growWithFreshness5().getFreshness().getFreshness());
            Assert.assertEquals(10, flowergarden.growWithFreshness10().getFreshness().getFreshness());
            Assert.assertEquals(15, flowergarden.growWithFreshness15().getFreshness().getFreshness());
        }
    }
}
