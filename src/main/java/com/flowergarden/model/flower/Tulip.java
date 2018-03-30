package com.flowergarden.model.flower;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Entity;

@Entity
@Data
@ToString(callSuper=true)
@EqualsAndHashCode(callSuper = true)
public class Tulip extends GeneralFlower {
}
