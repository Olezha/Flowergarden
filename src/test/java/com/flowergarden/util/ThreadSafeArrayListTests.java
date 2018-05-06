package com.flowergarden.util;

import com.anarsoft.vmlens.concurrent.junit.ConcurrentTestRunner;
import com.anarsoft.vmlens.concurrent.junit.ThreadCount;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

@RunWith(ConcurrentTestRunner.class)
public class ThreadSafeArrayListTests {

    private ThreadSafeArrayList<String> threadSafeArrayList = new ThreadSafeArrayList<>();

    private final static int THREAD_COUNT = 10_000;

    @Test
    @ThreadCount(THREAD_COUNT)
    public void add() {
        threadSafeArrayList.add("A");
    }

    @Test
    @ThreadCount(THREAD_COUNT)
    public void addAll() {
        threadSafeArrayList.addAll(new ArrayList<String>(){{
            add("B");
            add("C");
        }});
    }

    @After
    public void sizeTest() {
        assertEquals(30_000, threadSafeArrayList.size());
    }
}
