package com.flowergarden.bouquet;

import com.flowergarden.flowers.*;
import com.flowergarden.properties.FreshnessInteger;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class MarriedBouquetTest {

    @Spy
    private MarriedBouquet marriedBouquet = new MarriedBouquet();

    @Before
    public void context() {
        GeneralFlower flower1 = new Tulip();
        flower1.setFreshness(new FreshnessInteger(15));
        marriedBouquet.addFlower(flower1);
        GeneralFlower flower2 = new Rose();
        flower2.setFreshness(new FreshnessInteger(10));
        marriedBouquet.addFlower(flower2);
        GeneralFlower flower3 = new Chamomile(1, 1, BigDecimal.ONE, new FreshnessInteger(5));
        marriedBouquet.addFlower(flower3);
    }

    @Test
    public void getPriceTest() {
        assertEquals(BigDecimal.valueOf(121), marriedBouquet.getPrice());
        verify(marriedBouquet).getPrice();
    }

    @Test
    public void GivenBouquet_WhenAllOk_ThenBouquetIsWorthSomething() {
        assertTrue(marriedBouquet.getPrice().signum() > 0);
    }

    @Test
    public void searchFlowersByLengthTest() {
        assertEquals(1, marriedBouquet.searchFlowersByLength(1, 2).size());
        assertEquals(2, marriedBouquet.searchFlowersByLength(0, 0).size());
    }

    @Test(expected = ArithmeticException.class)
    public void GivenBouquet_WhenPriceIsLessThanZero_ThenArithmeticException() {
        MarriedBouquet marriedBouquet = new MarriedBouquet(new ArrayList(), new BigDecimal(-1));
        marriedBouquet.getPrice();
    }

    @Test
    public void sortByFreshnessTest() {
        marriedBouquet.sortByFreshness();
        Iterator<Flower> iterator = marriedBouquet.getFlowers().iterator();
        Flower firstFlower = (GeneralFlower) iterator.next();
        Flower lastFlower = firstFlower;
        while (iterator.hasNext())
            lastFlower = (GeneralFlower) iterator.next();

        assertTrue(firstFlower.getFreshness().compareTo(lastFlower.getFreshness()) <= 0);
    }
}
