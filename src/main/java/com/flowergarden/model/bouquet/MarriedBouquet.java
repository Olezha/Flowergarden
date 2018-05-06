package com.flowergarden.model.bouquet;

import com.flowergarden.model.flower.Flower;
import com.flowergarden.model.flower.GeneralFlower;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.*;

@Data
@Entity
@Table(name = "bouquet")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "name")
@DiscriminatorValue(value = "married")
public class MarriedBouquet implements Bouquet<GeneralFlower>, Cloneable, Iterable<GeneralFlower> {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(unique = true, nullable = false, updatable = false)
    private Integer id;

    private BigDecimal assemblePrice = BigDecimal.ZERO;

    @Transient
    private BigDecimal price;

    @OneToMany(mappedBy = "bouquet")
    private List<GeneralFlower> flowers = new ArrayList<>();

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
    public void addFlower(GeneralFlower flower) {
        flowers.add(flower);
    }

    @Override
    public Collection<GeneralFlower> searchFlowersByLength(int start, int end) {
        List<GeneralFlower> searchResult = new ArrayList<>();
        for (GeneralFlower flower : flowers) {
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

    public MarriedBouquet clone() throws CloneNotSupportedException {
        return (MarriedBouquet) super.clone();
    }

    @Override
    public Iterator<GeneralFlower> iterator() {
        return new GeneralFlowerIterator();
    }

    public ListIterator<GeneralFlower> iterator(int index) {
        if (index >= 0 && index <= flowers.size()) {
            GeneralFlowerIterator iterator = new GeneralFlowerIterator();
            iterator.index = index;
            return iterator;
        } else {
            throw new IndexOutOfBoundsException("Index: " + index);
        }
    }

    private class GeneralFlowerIterator implements ListIterator<GeneralFlower> {

        int index;

        @Override
        public boolean hasNext() {
            return index < flowers.size();
        }

        @Override
        public GeneralFlower next() {
            if (this.hasNext())
                return flowers.get(index++);
            return null;
        }

        @Override
        public boolean hasPrevious() {
            return index > 0;
        }

        @Override
        public GeneralFlower previous() {
            if (this.hasPrevious())
                return flowers.get(--index);
            return null;
        }

        @Override
        public int nextIndex() {
            throw new UnsupportedOperationException();
        }

        @Override
        public int previousIndex() {
            throw new UnsupportedOperationException();
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

        @Override
        public void set(GeneralFlower generalFlower) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void add(GeneralFlower generalFlower) {
            throw new UnsupportedOperationException();
        }
    }
}
