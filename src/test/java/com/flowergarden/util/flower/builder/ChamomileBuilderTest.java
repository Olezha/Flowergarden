package com.flowergarden.util.flower.builder;

import com.flowergarden.model.flower.Chamomile;
import org.junit.Assert;
import org.junit.Test;

public class ChamomileBuilderTest {

    @Test
    public void roseBuilderTest() {
        Chamomile chamomile = Chamomile.builder()
                .setFreshness(1)
                .setPrice(900)
                .build();
        Assert.assertNotNull(chamomile);
    }
}
