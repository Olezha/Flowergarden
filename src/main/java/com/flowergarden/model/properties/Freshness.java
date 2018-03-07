package com.flowergarden.model.properties;

public interface Freshness<E> extends Comparable<Freshness> {

    E getFreshness();
}
