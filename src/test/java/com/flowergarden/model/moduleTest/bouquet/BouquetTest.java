package com.flowergarden.model.moduleTest.bouquet;

import com.flowergarden.model.bouquet.Bouquet;
import com.flowergarden.model.bouquet.MarriedBouquet;
import com.flowergarden.model.flowers.Chamomile;
import com.flowergarden.model.flowers.Flower;
import com.flowergarden.model.flowers.Rose;
import com.flowergarden.model.properties.FreshnessInteger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Collection;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.junit.Assert.assertThat;

public class BouquetTest {

    private Bouquet<Flower> bouquet;
    private Rose rose = new Rose();
    private Chamomile chamomile =
            new Chamomile(0, 1,
                    BigDecimal.ZERO, new FreshnessInteger(1));

    @Before
    public void initBouquet() {
        bouquet = new MarriedBouquet();
        bouquet.addFlower(rose);
        bouquet.addFlower(chamomile);
    }

    @Test
    public void getPriceTest() {
        BigDecimal price = bouquet.getPrice();
        Assert.assertEquals(new BigDecimal(120), price);
    }

    @Test
    public void searchFlowersByLengthTest() {
        Collection<Flower> flowers = bouquet.searchFlowersByLength(0, 1);
        assertThat(flowers, hasItems(rose, chamomile));
    }
}
