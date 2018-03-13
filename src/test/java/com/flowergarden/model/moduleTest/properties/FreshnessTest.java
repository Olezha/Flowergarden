package com.flowergarden.model.moduleTest.properties;

import com.flowergarden.model.properties.Freshness;
import com.flowergarden.model.properties.FreshnessInteger;
import org.junit.Test;

public class FreshnessTest {

    @Test(expected = UnsupportedOperationException.class)
    public void freshnessIntegerCompareToFreshnessTest() {
        FreshnessInteger freshnessInteger = new FreshnessInteger();
        freshnessInteger.compareTo(new Freshness() {
            @Override
            public Object getFreshness() {
                return null;
            }

            @Override
            public int compareTo(Object o) {
                return 0;
            }
        });
    }
}
