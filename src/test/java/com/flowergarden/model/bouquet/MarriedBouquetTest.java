package com.flowergarden.model.bouquet;

import com.flowergarden.model.flower.Chamomile;
import com.flowergarden.model.flower.GeneralFlower;
import com.flowergarden.model.flower.Rose;
import com.flowergarden.model.flower.Tulip;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;

@RunWith(MockitoJUnitRunner.class)
public class MarriedBouquetTest {

    @Spy
    private MarriedBouquet marriedBouquet = new MarriedBouquet();

    @Spy
    private Rose rose = new Rose();

    @Spy
    private Chamomile chamomile = new Chamomile();

    @Spy
    private Tulip tulip = new Tulip();

    @Before
    public void context() {
        marriedBouquet.addFlower(rose);
        marriedBouquet.addFlower(chamomile);
        marriedBouquet.addFlower(tulip);
    }

    @Test
    public void cloneableTest() throws CloneNotSupportedException {
        MarriedBouquet clone = marriedBouquet.clone();
        Assert.assertNotSame(marriedBouquet, clone);
        Assert.assertEquals(marriedBouquet, clone);
    }

    @Test
    public void iterableTest() {
        for (GeneralFlower flower : marriedBouquet)
            flower.getPrice();

        Mockito.verify(rose).getPrice();
        Mockito.verify(chamomile).getPrice();
        Mockito.verify(tulip).getPrice();
    }

    @Test
    public void reverseIterableTest() {
        ListIterator<GeneralFlower> iterator = marriedBouquet.iterator(marriedBouquet.getFlowers().size());
        while (iterator.hasPrevious())
            iterator.previous().getPrice();

        Mockito.verify(rose).getPrice();
        Mockito.verify(chamomile).getPrice();
        Mockito.verify(tulip).getPrice();
    }
}
