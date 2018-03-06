package com.flowergarden.properties;

public interface Freshness<E> extends Comparable<Freshness> {

    E getFreshness();
}
