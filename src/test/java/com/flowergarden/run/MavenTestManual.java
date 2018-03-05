package com.flowergarden.run;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class MavenTestManual {

    @Mock
    private List<String> mockList;

    @Test(expected = RuntimeException.class)
    public void test1() {
//        List<String> mockList = mock(List.class, withSettings().verboseLogging());

        when(mockList.get(anyInt())).thenReturn("first");
        when(mockList.get(1)).thenThrow(new RuntimeException());

        when(mockList.get(anyInt()))
                .thenReturn("first")
                .thenReturn("two", "three");

        mockList.add("one");
        mockList.clear();
        System.out.println("Get(0): " + mockList.get(0) + System.lineSeparator());

        verify(mockList).add("one");
        verify(mockList).clear();

        System.out.println("Get(999): " + mockList.get(999) + System.lineSeparator());

        verify(mockList, times(2)).get(anyInt());
        verify(mockList, atMost(5)).get(anyInt());

        InOrder inOrder = inOrder(mockList);
        inOrder.verify(mockList).add(anyString());
        inOrder.verify(mockList).clear();
        inOrder.verify(mockList).get(0);

        System.out.println("Get(1): " + mockList.get(1) + System.lineSeparator());
    }
}

// TODO
class Foo<T> {
    final Class<T> typeParameterClass;

    public Foo(Class<T> typeParameterClass) {
        this.typeParameterClass = typeParameterClass;
    }

    public void bar() {
        // you can access the typeParameterClass here and do whatever you like
    }
}