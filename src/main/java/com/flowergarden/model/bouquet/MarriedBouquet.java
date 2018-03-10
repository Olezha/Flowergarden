package com.flowergarden.model.bouquet;

import com.flowergarden.model.flowers.Flower;

import java.math.BigDecimal;
import java.util.*;

public class MarriedBouquet implements Bouquet<Flower> {

    private Integer id;

    private BigDecimal assemblePrice = new BigDecimal(120);

    private List<Flower> flowerList = new ArrayList<>();

    private BigDecimal price;

    public MarriedBouquet() {
    }

    public MarriedBouquet(List<Flower> flowers, BigDecimal price) {
        flowerList = flowers;
        this.price = price;
    }

    @Override
    public BigDecimal getPrice() {
        if (price == null)
            this.price = calcPrice();

        if (price.signum() < 0)
            throw new ArithmeticException("Price cannot be less than zero");

        return price;
    }

    @Override
    public String getName() {
        return "married";
    }

    @Override
    public void setAssemblePrice(BigDecimal assemblePrice) {
        this.assemblePrice = assemblePrice;
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

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public BigDecimal getAssemblePrice() {
        return assemblePrice;
    }

    private BigDecimal calcPrice() {
        BigDecimal price = assemblePrice;
        for (Flower flower : flowerList)
            if (flower.getPrice() != null)
                price = price.add(flower.getPrice());
        return price;
    }
}
