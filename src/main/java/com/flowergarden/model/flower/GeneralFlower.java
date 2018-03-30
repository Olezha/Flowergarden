package com.flowergarden.model.flower;

import com.flowergarden.model.property.FreshnessInteger;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "flower")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class GeneralFlower implements Flower<Integer>, Comparable<GeneralFlower> {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(unique = true, nullable = false, updatable = false)
    private Integer id;

    private FreshnessInteger freshness;

    private int length;

    private BigDecimal price;

    @Override
    public int compareTo(GeneralFlower compareFlower) {
        int compareFresh = compareFlower.getFreshness().getFreshness();
        return this.getFreshness().getFreshness() - compareFresh;
    }
}
