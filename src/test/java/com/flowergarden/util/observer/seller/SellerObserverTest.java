package com.flowergarden.util.observer.seller;

import com.flowergarden.model.flower.Chamomile;
import com.flowergarden.model.property.FreshnessInteger;
import com.flowergarden.model.seller.Seller;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class SellerObserverTest {

    @Spy
    private Seller seller = new Seller();

    private Chamomile chamomile = new Chamomile();

    @Before
    public void context() {
        chamomile.addObserver(seller);
    }

    @Test
    public void observeTest() {
        chamomile.setFreshness(new FreshnessInteger());
        Mockito.verify(seller).handleEvent(chamomile);
    }
}
