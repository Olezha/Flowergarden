package com.flowergarden.bouquet;

import com.flowergarden.flowers.Chamomile;
import com.flowergarden.flowers.Flower;
import com.flowergarden.flowers.Rose;
import com.flowergarden.properties.FreshnessInteger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Collection;

import static org.mockito.Mockito.mock;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class BouquetTest {

    private Bouquet<Flower> bouquet;
    private Rose rose = new Rose();
    private Chamomile chamomile = new Chamomile(0, 0, mock(BigDecimal.class), mock(FreshnessInteger.class));

    @Before
    public void initBouquet() {
        bouquet = new MarriedBouquet();
        bouquet.addFlower(rose);
        bouquet.addFlower(chamomile);
    }

    @Test
    public void getPriceTest() {
        BigDecimal price = bouquet.getPrice();
        assertEquals(new BigDecimal(120), price);
    }

    @Test
    public void searchFlowersByLengthTest() {
        Collection<Flower> flowers = bouquet.searchFlowersByLength(0, 0);
        assertThat(bouquet.getFlowers(), hasItems(rose, chamomile));
    }
}
