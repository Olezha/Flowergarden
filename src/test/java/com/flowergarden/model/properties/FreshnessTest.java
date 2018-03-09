package com.flowergarden.model.properties;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class FreshnessTest {

    @Mock
    private Freshness freshness;

    @Test(expected = UnsupportedOperationException.class)
    public void freshnessIntegerCompareToFreshnessTest() {
        FreshnessInteger freshnessInteger = new FreshnessInteger();
        freshnessInteger.compareTo(freshness);
    }
}