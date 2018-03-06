package com.flowergarden.bouquet;

import com.flowergarden.flowers.Flower;
import com.flowergarden.flowers.GeneralFlower;
import com.flowergarden.flowers.Tulip;
import com.flowergarden.properties.FreshnessInteger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;

public class MarriedBouquetTest {

    private MarriedBouquet marriedBouquet;

    @Before
    public void context() {
        marriedBouquet = new MarriedBouquet();
        GeneralFlower flower1 = new Tulip();
        flower1.setFreshness(new FreshnessInteger(15));
        marriedBouquet.addFlower(flower1);
        GeneralFlower flower2 = new Tulip();
        flower2.setFreshness(new FreshnessInteger(10));
        marriedBouquet.addFlower(flower2);
        GeneralFlower flower3 = new Tulip();
        flower3.setFreshness(new FreshnessInteger(5));
        marriedBouquet.addFlower(flower3);
        GeneralFlower flower4 = new Tulip();
        flower4.setFreshness(new FreshnessInteger(1));
        marriedBouquet.addFlower(flower4);
    }

    @Test
    public void getPriceTest() {
        Assert.assertEquals(new BigDecimal(120), marriedBouquet.getPrice());
    }

    @Test
    public void GivenBouquet_WhenAllOk_ThenBouquetIsWorthSomething() {
        Assert.assertTrue(marriedBouquet.getPrice().compareTo(BigDecimal.ZERO) > 0);
    }

    @Test
    public void searchFlowersByLenghtTest() {
        Assert.assertTrue(marriedBouquet.searchFlowersByLength(1, 2).isEmpty());
        Assert.assertEquals(4, marriedBouquet.searchFlowersByLength(0, 0).size());
    }

    @Ignore
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

        Assert.assertTrue(firstFlower.getFreshness().compareTo(lastFlower.getFreshness()) <=0);
    }
}
