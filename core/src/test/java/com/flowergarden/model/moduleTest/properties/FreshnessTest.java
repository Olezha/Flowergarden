package com.flowergarden.model.moduleTest.properties;

import com.flowergarden.model.property.Freshness;
import com.flowergarden.model.property.FreshnessInteger;
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
            public void reduce() throws UnsupportedOperationException {
            }

            @Override
            public int compareTo(Object o) {
                return 0;
            }
        });
    }
}
