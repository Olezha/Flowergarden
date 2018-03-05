package com.flowergarden.bouquet;

import com.flowergarden.flowers.Flower;
import com.flowergarden.flowers.GeneralFlower;

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
        if (price == null) {
            BigDecimal price = assemblePrice;
            for (Flower flower : flowerList) {
                price = price.add(flower.getPrice());
            }
            this.price = price;
        }
        return price;
    }

    @Override
    public void addFlower(Flower flower) {
        flowerList.add(flower);
    }

    @Override
    public Collection<Flower> searchFlowersByLenght(int start, int end) {
        List<Flower> searchResult = new ArrayList<>();
        for (Flower flower : flowerList) {
            if (flower.getLenght() >= start && flower.getLenght() <= end) {
                searchResult.add(flower);
            }
        }
        return searchResult;
    }

    @Override
    public void sortByFreshness() {
        Collections.sort(flowerList, new Comparator<Flower>() {
            @Override
            public int compare(Flower flower, Flower compareFlower) {
                GeneralFlower generalFlower = (GeneralFlower) flower;
                GeneralFlower compareGeneralFlower = (GeneralFlower) compareFlower;

                int compareFresh = compareGeneralFlower.getFreshness().getFreshness();
                return generalFlower.getFreshness().getFreshness() - compareFresh;
            }
        });
    }

    @Override
    public Collection<Flower> getFlowers() {
        return flowerList;
    }

    public void setAssembledPrice(BigDecimal price) {
        assemblePrice = price;
    }
}
