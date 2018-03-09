package com.flowergarden.bouquet;

import com.flowergarden.flowers.*;
import com.flowergarden.properties.FreshnessInteger;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class MarriedBouquetMockTest {

    @Mock
    private Flower flower;

    @Mock
    private GeneralFlower generalFlower;

    @Mock
    private Tulip tulip;

    @Mock
    private Rose rose;

    @Mock
    private Chamomile chamomile;

    @Spy
    private MarriedBouquet marriedBouquet = new MarriedBouquet();

    @Before
    public void context() {
        when(flower.getPrice()).thenReturn(BigDecimal.ONE);
        when(flower.getLength()).thenReturn(-1);
        when(flower.getFreshness()).thenReturn(mock(FreshnessInteger.class));
        marriedBouquet.addFlower(flower);

        when(generalFlower.getLength()).thenReturn(100);
        when(generalFlower.getFreshness()).thenReturn(mock(FreshnessInteger.class));
        marriedBouquet.addFlower(generalFlower);

        when(tulip.getFreshness()).thenReturn(new FreshnessInteger(15));
        when(tulip.getPrice()).thenReturn(BigDecimal.TEN);
        marriedBouquet.addFlower(tulip);

        when(rose.getFreshness()).thenReturn(new FreshnessInteger(10));
        when(rose.getPrice()).thenReturn(BigDecimal.valueOf(25));
        marriedBouquet.addFlower(rose);

        when(chamomile.getFreshness()).thenReturn(new FreshnessInteger(5));
        when(chamomile.getPrice()).thenReturn(BigDecimal.valueOf(3));
        when(chamomile.getLength()).thenReturn(25);
        marriedBouquet.addFlower(chamomile);
    }

    @Test
    public void getPriceTest() {
        assertEquals(BigDecimal.valueOf(159), marriedBouquet.getPrice());
        verify(marriedBouquet).getPrice();
    }

    @Test
    public void GivenBouquet_WhenAllOk_ThenBouquetIsWorthSomething() {
        assertTrue(marriedBouquet.getPrice().signum() > 0);
    }

    @Test
    public void searchFlowersInRangeTest() {
        assertEquals(1, marriedBouquet.searchFlowersByLength(20, 30).size());
        assertEquals(2, marriedBouquet.searchFlowersByLength(0, 0).size());
    }

    @Test(expected = ArithmeticException.class)
    public void GivenBouquet_WhenPriceIsLessThanZero_ThenArithmeticException() {
        MarriedBouquet marriedBouquet = new MarriedBouquet(mock(List.class), new BigDecimal(-1));
        marriedBouquet.getPrice();
    }

    @Test
    public void sortByFreshnessTest() {
        marriedBouquet.addFlower(new Chamomile(1, 1, BigDecimal.ONE, new FreshnessInteger(5)));
        marriedBouquet.addFlower(new Chamomile(1, 1, BigDecimal.ONE, new FreshnessInteger(25)));
        marriedBouquet.sortByFreshness();
        Iterator<Flower> iterator = marriedBouquet.getFlowers().iterator();
        Flower firstFlower = iterator.next();
        Flower lastFlower = firstFlower;
        while (iterator.hasNext())
            lastFlower = iterator.next();

        assertTrue(firstFlower.getFreshness().compareTo(lastFlower.getFreshness()) <= 0);
    }
}
