package com.flowergarden.bouquet;

import com.flowergarden.flowers.GeneralFlower;
import com.flowergarden.flowers.Tulip;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.mockito.Mockito.*;

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

    // TODO: assertThat greaterThan
    @Test
    public void GivenBouquetWhenAllOkThenBouquetIsWorthSomething() {
        Assert.assertTrue(marriedBouquet.getPrice() >= 0);
    }

    @Test
    public void searchFlowersByLenghtTest() {
        Assert.assertTrue(marriedBouquet.searchFlowersByLenght(1, 2).isEmpty());
        Assert.assertEquals(4, marriedBouquet.searchFlowersByLenght(0, 0).size());
    }

    @Test
    public void testFloerList() {
        // Given
        List list = mock(List.class);
        Tulip tulip = new Tulip();
        MarriedBouquet marriedBouquet = new MarriedBouquet();
        marriedBouquet.setMockedFlowerList(list);

        // When
        marriedBouquet.addFlower(tulip);

        // Then
        verify(list).add(tulip);
    }
}
