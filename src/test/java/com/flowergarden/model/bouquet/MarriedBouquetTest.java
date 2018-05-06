package com.flowergarden.model.bouquet;

import org.junit.Assert;
import org.junit.Test;

public class MarriedBouquetTest {

    @Test
    public void cloneableTest() throws CloneNotSupportedException {
        MarriedBouquet marriedBouquet = new MarriedBouquet();
        MarriedBouquet marriedBouquetClone = marriedBouquet.clone();
        Assert.assertNotSame(marriedBouquet, marriedBouquetClone);
        Assert.assertEquals(marriedBouquet, marriedBouquetClone);
    }
}
