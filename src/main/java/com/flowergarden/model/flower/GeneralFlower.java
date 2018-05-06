package com.flowergarden.model.flower;

import com.flowergarden.model.bouquet.MarriedBouquet;
import com.flowergarden.model.property.FreshnessInteger;
import com.flowergarden.util.observer.Observable;
import com.flowergarden.util.observer.Observer;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@ToString(exclude = "bouquet")
@Entity
@Table(name = "flower")
@DiscriminatorColumn(name = "name")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class GeneralFlower implements Flower<Integer>, Comparable<GeneralFlower>, Observable {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(unique = true, nullable = false, updatable = false)
    private Integer id;

    private FreshnessInteger freshness;

    private int length;

    private BigDecimal price;

    @ManyToOne
    private MarriedBouquet bouquet;

    @Transient
    private List<Observer> observers;

    @Override
    public int compareTo(GeneralFlower compareFlower) {
        int compareFresh = compareFlower.getFreshness().getFreshness();
        return this.getFreshness().getFreshness() - compareFresh;
    }

    public void setFreshness(FreshnessInteger freshness) {
        this.freshness = freshness;
        notifyObserver();
    }

    @Override
    public void addObserver(Observer observer) {
        if (observers == null)
            observers = new ArrayList<>();
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObserver() {
        if (observers == null)
            return;
        for (Observer observer : observers)
            observer.handleEvent(this);
    }
}
