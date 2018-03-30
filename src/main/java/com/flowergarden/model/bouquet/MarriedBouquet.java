package com.flowergarden.model.bouquet;

import com.flowergarden.model.flower.Flower;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.data.annotation.Id;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.*;

@Data
@Entity
public class MarriedBouquet implements Bouquet<Flower> {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Integer id;

    private BigDecimal assemblePrice = BigDecimal.ZERO;

    @Transient
    private BigDecimal price;

    @OneToMany
    private List<Flower> flowers = new ArrayList<>();

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
    public void addFlower(Flower flower) {
        flowers.add(flower);
    }

    @Override
    public Collection<Flower> searchFlowersByLength(int start, int end) {
        List<Flower> searchResult = new ArrayList<>();
        for (Flower flower : flowers) {
            if (flower.getLength() >= start && flower.getLength() <= end)
                searchResult.add(flower);
        }
        return searchResult;
    }

    @Override
    public void sortByFreshness() {
        flowers.sort(Comparator.comparing(Flower::getFreshness));
    }

    private BigDecimal calcPrice() {
        BigDecimal price = assemblePrice;
        for (Flower flower : flowers)
            if (flower.getPrice() != null)
                price = price.add(flower.getPrice());
        return price;
    }
}
