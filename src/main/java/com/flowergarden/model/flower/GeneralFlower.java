package com.flowergarden.model.flower;

import com.flowergarden.model.bouquet.MarriedBouquet;
import com.flowergarden.model.property.FreshnessInteger;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "flower")
@DiscriminatorColumn(name = "name")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class GeneralFlower implements Flower<Integer>, Comparable<GeneralFlower> {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(unique = true, nullable = false, updatable = false)
    private Integer id;

    private FreshnessInteger freshness;

    private int length;

    private BigDecimal price;

    @ManyToOne
    private MarriedBouquet bouquet;

    @Override
    public int compareTo(GeneralFlower compareFlower) {
        int compareFresh = compareFlower.getFreshness().getFreshness();
        return this.getFreshness().getFreshness() - compareFresh;
    }
}
