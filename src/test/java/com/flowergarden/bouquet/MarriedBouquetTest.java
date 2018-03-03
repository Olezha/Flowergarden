package com.flowergarden.bouquet;

import com.flowergarden.flowers.GeneralFlower;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

// TODO (Question): I don't see what I can mock
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
        Assert.assertEquals(120, marriedBouquet.getPrice(), 0);
    }

    @Test
    public void searchFlowersByLenghtTest() {
        Assert.assertTrue(marriedBouquet.searchFlowersByLenght(1, 2).isEmpty());
        Assert.assertEquals(4, marriedBouquet.searchFlowersByLenght(0, 0).size());
    }
}
