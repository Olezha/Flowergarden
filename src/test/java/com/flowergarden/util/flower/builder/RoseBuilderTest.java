package com.flowergarden.util.flower.builder;

import com.flowergarden.model.flower.Rose;
import org.junit.Assert;
import org.junit.Test;

public class RoseBuilderTest {

    @Test
    public void roseBuilderTest() {
        Rose rose = Rose.builder()
                .setFreshness(1)
                .setLength(80)
                .setSpike(true)
                .setPrice(6000)
                .build();
        Assert.assertNotNull(rose);
    }
}
