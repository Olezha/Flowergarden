package com.flowergarden.flowers;

import com.flowergarden.properties.FreshnessInteger;

import java.math.BigDecimal;

public class Chamomile extends GeneralFlower {
	
	private int petals;
	
	public Chamomile(int petals, int lenght, BigDecimal price, FreshnessInteger fresh){
		this.petals = petals;
		this.lenght = lenght;
		this.price = price;
		this.freshness = fresh;
	}
	
	public boolean getPetal(){
		if (petals <=0) return false;
		petals =-1;
		return true;
	}
	
	public int getPetals(){
		return petals;
	}
}
