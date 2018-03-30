package com.flowergarden.model.property;

public interface Freshness<E> extends Comparable<Freshness> {

    E getFreshness();
}
