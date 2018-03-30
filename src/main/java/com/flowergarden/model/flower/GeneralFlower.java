package com.flowergarden.model.flower;

import com.flowergarden.model.property.FreshnessInteger;
import lombok.Data;
import lombok.ToString;

import javax.persistence.Entity;
import java.math.BigDecimal;

@Data
@Entity
public abstract class GeneralFlower implements Flower<Integer>, Comparable<GeneralFlower> {

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
