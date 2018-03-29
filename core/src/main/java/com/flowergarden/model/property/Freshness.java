package com.flowergarden.model.property;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public interface Freshness<E> extends Comparable<Freshness> {

    E getFreshness();
}
