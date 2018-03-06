package com.flowergarden.bouquet;

import com.flowergarden.flowers.Flower;

import java.math.BigDecimal;
import java.util.*;

public class MarriedBouquet implements Bouquet<Flower> {

    private BigDecimal assemblePrice = new BigDecimal(120);

    private List<Flower> flowerList = new ArrayList<>();

    private BigDecimal price;

    public MarriedBouquet() {
    }

    public MarriedBouquet(ArrayList<Flower> flowers, BigDecimal price) {
        flowerList = flowers;
        this.price = price;
    }

    @Override
    public BigDecimal getPrice() {
        if (price == null)
            this.price = calcPrice();

        return price;
    }

    @Override
    public void addFlower(Flower flower) {
        flowerList.add(flower);
    }

    @Override
    public Collection<Flower> searchFlowersByLength(int start, int end) {
        List<Flower> searchResult = new ArrayList<>();
        for (Flower flower : flowerList) {
            if (flower.getLength() >= start && flower.getLength() <= end) {
                searchResult.add(flower);
            }
        }
        return searchResult;
    }

    @Override
    public void sortByFreshness() {
        flowerList.sort(Comparator.comparing(Flower::getFreshness));
    }

    @Override
    public Collection<Flower> getFlowers() {
        return flowerList;
    }

    public void setAssembledPrice(BigDecimal price) {
        assemblePrice = price;
    }

    private BigDecimal calcPrice() {
        BigDecimal price = assemblePrice;
        for (Flower flower : flowerList)
            if (flower.getPrice() != null)
                price = price.add(flower.getPrice());
        return price;
    }
}
