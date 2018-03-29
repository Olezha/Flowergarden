package com.flowergarden.model.moduleTest.flowers;

import com.flowergarden.model.flower.Chamomile;
import com.flowergarden.model.flower.Rose;
import com.flowergarden.model.property.FreshnessInteger;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class FlowersTest {

    @Test
    public void chamomilePetalsTest() {
        Chamomile chamomile = new Chamomile(3, 15, BigDecimal.ONE, new FreshnessInteger(2));
        assertEquals(3, chamomile.getPetals());
        assertTrue(chamomile.getPetal());
        assertEquals(-1, chamomile.getPetals());
        assertFalse(chamomile.getPetal());
    }

    @Test
    public void roseTest() {
        Rose rose = new Rose(true, 25, BigDecimal.TEN, new FreshnessInteger(3));
        assertTrue(rose.getSpike());
    }
}
