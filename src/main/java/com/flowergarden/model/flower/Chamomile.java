package com.flowergarden.model.flower;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = "chamomile")
@Data
@ToString(callSuper=true)
@EqualsAndHashCode(callSuper = true)
public class Chamomile extends GeneralFlower {

    private int petals;

    public boolean getPetal() {
        if (petals <= 0) return false;
        petals -= 1;
        return true;
    }
}
