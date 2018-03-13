package com.flowergarden.model.moduleTest.bouquet;

import com.flowergarden.model.bouquet.MarriedBouquet;
import com.flowergarden.model.flowers.*;

import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class MarriedBouquetTest {

    private MarriedBouquet marriedBouquet = new MarriedBouquet();

    @Test
    public void GivenBouquet_WhenAllOk_ThenBouquetIsWorthSomething() {
        assertTrue(marriedBouquet.getPrice().signum() > 0);
    }

    @Test(expected = ArithmeticException.class)
    public void GivenBouquet_WhenPriceIsLessThanZero_ThenArithmeticException() {
        MarriedBouquet marriedBouquet = new MarriedBouquet(new ArrayList<>(), new BigDecimal(-1));
        marriedBouquet.getPrice();
    }

    @Test
    public void sortByFreshnessTest() {
        marriedBouquet.sortByFreshness();

        Flower previousFlower = null;
        for (Flower<Integer> flower : marriedBouquet.getFlowers()) {
            if (previousFlower != null)
                assertTrue((int) previousFlower.getFreshness().getFreshness() <= flower.getFreshness().getFreshness());
            previousFlower = flower;
        }
    }
}
