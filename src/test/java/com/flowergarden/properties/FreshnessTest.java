package com.flowergarden.properties;

import org.junit.Test;

public class FreshnessTest {

    @Test(expected = UnsupportedOperationException.class)
    public void freshnessIntegerCompareToFreshnessTest() {
        FreshnessInteger freshnessInteger = new FreshnessInteger();
        Freshness<Integer> freshness = new Freshness<Integer>() {
            @Override
            public Integer getFreshness() {
                return null;
            }

            @Override
            public int compareTo(Freshness o) {
                return 0;
            }
        };

        freshnessInteger.compareTo(freshness);
    }
}
