package com.flowergarden.model.flower;

import com.flowergarden.util.flower.builder.RoseBuilder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = "rose")
@Data
@ToString(callSuper=true)
@EqualsAndHashCode(callSuper = true)
public class Rose extends GeneralFlower {

    private boolean spike;

    public static RoseBuilder builder() {
        return new RoseBuilder();
    }
}
