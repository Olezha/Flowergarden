package com.flowergarden.util;

import java.io.Serializable;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

public class ThreadSafeArrayList<E> implements List<E>, RandomAccess, Cloneable, Serializable {

    private ArrayList<E> arrayList = null;

    public ThreadSafeArrayList() {
        arrayList = new ArrayList<>();
    }

    public ThreadSafeArrayList(int initialCapacity) {
        arrayList = new ArrayList<>(initialCapacity);
    }

    public ThreadSafeArrayList(Collection<? extends E> c) {
        arrayList = new ArrayList<>(c);
    }

    @Override
    public int size() {
        return arrayList.size();
    }

    @Override
    public boolean isEmpty() {
        return arrayList.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return arrayList.contains(o);
    }

    @Override
    public int indexOf(Object o) {
        return arrayList.indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {
        return arrayList.lastIndexOf(o);
    }

    @Override
    public Object clone() {
        return null;
    }

    @Override
    public Object[] toArray() {
        return arrayList.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return arrayList.toArray(a);
    }

    @Override
    public E get(int index) {
        return arrayList.get(index);
    }

    @Override
    public E set(int index, E element) {
        return arrayList.set(index, element);
    }

    @Override
    public boolean add(E e) {
        return arrayList.add(e);
    }

    @Override
    public void add(int index, E element) {
        arrayList.add(index, element);
    }

    @Override
    public E remove(int index) {
        return arrayList.remove(index);
    }

    @Override
    public boolean remove(Object o) {
        return arrayList.remove(o);
    }

    @Override
    public boolean containsAll(Collection<?> collection) {
        return false;
    }

    @Override
    public void clear() {
        arrayList.clear();
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        return arrayList.addAll(c);
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        return arrayList.addAll(index, c);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return arrayList.removeAll(c);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return arrayList.retainAll(c);
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        return arrayList.listIterator(index);
    }

    @Override
    public ListIterator<E> listIterator() {
        return arrayList.listIterator();
    }

    @Override
    public Iterator<E> iterator() {
        return arrayList.iterator();
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        return arrayList.subList(fromIndex, toIndex);
    }

    @Override
    public void forEach(Consumer<? super E> action) {
        arrayList.forEach(action);
    }

    @Override
    public Spliterator<E> spliterator() {
        return arrayList.spliterator();
    }

    @Override
    public boolean removeIf(Predicate<? super E> filter) {
        return arrayList.removeIf(filter);
    }

    @Override
    public void replaceAll(UnaryOperator<E> operator) {
        arrayList.replaceAll(operator);
    }

    @Override
    public void sort(Comparator<? super E> c) {
        arrayList.sort(c);
    }
}
