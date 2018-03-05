package com.flowergarden.bouquet;

import com.flowergarden.flowers.GeneralFlower;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;

public class MarriedBouquetTest {

    private MarriedBouquet marriedBouquet;

    @Before
    public void context() {
        marriedBouquet = new MarriedBouquet();
        GeneralFlower flower1 = new GeneralFlower();
        marriedBouquet.addFlower(flower1);
        GeneralFlower flower2 = new GeneralFlower();
        marriedBouquet.addFlower(flower2);
        GeneralFlower flower3 = new GeneralFlower();
        marriedBouquet.addFlower(flower3);
        GeneralFlower flower4 = new GeneralFlower();
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

    @Test(expected = ArithmeticException.class)
    public void GivenBouquet_WhenPriceIsLessThanZero_ThenArithmeticException() {
        MarriedBouquet marriedBouquet = new MarriedBouquet(new ArrayList(), new BigDecimal(-1));
        marriedBouquet.getPrice();
    }

    @Test
    public void searchFlowersByLenghtTest() {
        Assert.assertTrue(marriedBouquet.searchFlowersByLenght(1, 2).isEmpty());
        Assert.assertEquals(4, marriedBouquet.searchFlowersByLenght(0, 0).size());
    }
}
