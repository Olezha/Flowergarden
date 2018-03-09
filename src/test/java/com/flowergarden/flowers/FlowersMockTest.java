package com.flowergarden.flowers;

import com.flowergarden.properties.FreshnessInteger;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class FlowersMockTest {

    @Test
    public void chamomilePetalsTest() {
        Chamomile chamomile = new Chamomile(3, 15, mock(BigDecimal.class), mock(FreshnessInteger.class));
        assertEquals(3, chamomile.getPetals());
        assertTrue(chamomile.getPetal());
        assertEquals(-1, chamomile.getPetals());
        assertFalse(chamomile.getPetal());
    }

    @Test
    public void roseTest() {
        Rose rose = new Rose(true, 25, mock(BigDecimal.class), mock(FreshnessInteger.class));
        assertTrue(rose.getSpike());
    }
}
