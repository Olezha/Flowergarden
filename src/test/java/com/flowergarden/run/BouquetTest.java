package com.flowergarden.run;

import java.util.Collection;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.flowergarden.bouquet.*;
import com.flowergarden.flowers.GeneralFlower;
import com.flowergarden.flowers.Rose;

public class BouquetTest {
	private Bouquet<GeneralFlower> bouquet;

	@Before
	public void initBouquet() {
		bouquet = new MarriedBouquet();
		bouquet.addFlower(new Rose());
	}

	@Test
	public void getPriceTest() {
		Float price = bouquet.getPrice();
		Assert.assertEquals(Float.valueOf(120), price);
	}
	
	@Test
	public void searchFlowersByLenghtTest() {
		Collection<GeneralFlower> flowers = bouquet.searchFlowersByLenght(0, 0);
		Assert.assertEquals(flowers.isEmpty(), false);
	}
}
