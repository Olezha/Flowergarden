package com.flowergarden.model.flower;

import lombok.ToString;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@ToString(callSuper = true)
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Tulip extends GeneralFlower {
}
