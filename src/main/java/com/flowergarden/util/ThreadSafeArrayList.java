package com.flowergarden.util;

import java.io.Serializable;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

public class ThreadSafeArrayList<E>
        extends AbstractList<E>
        implements List<E>, RandomAccess, Cloneable, Serializable {

    private static final long serialVersionUID = 7594353573113795179L;

    private final ArrayList<E> arrayList;

    public ThreadSafeArrayList() {
        super();
        arrayList = new ArrayList<>();
    }

    public ThreadSafeArrayList(int initialCapacity) {
        super();
        arrayList = new ArrayList<>(initialCapacity);
    }

    public ThreadSafeArrayList(Collection<? extends E> collection) {
        super();
        arrayList = new ArrayList<>(collection);
    }

    @Override
    public int size() {
        synchronized (arrayList) {
            return arrayList.size();
        }
    }

    @Override
    public boolean isEmpty() {
        synchronized (arrayList) {
            return arrayList.isEmpty();
        }
    }

    @Override
    public boolean contains(Object object) {
        synchronized (arrayList) {
            return arrayList.contains(object);
        }
    }

    @Override
    public int indexOf(Object object) {
        synchronized (arrayList) {
            return arrayList.indexOf(object);
        }
    }

    @Override
    public int lastIndexOf(Object object) {
        synchronized (arrayList) {
            return arrayList.lastIndexOf(object);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public synchronized Object clone() {
        synchronized (arrayList) {
            return new ThreadSafeArrayList<E>((Collection<? extends E>) arrayList.clone());
        }
    }

    @Override
    public Object[] toArray() {
        synchronized (arrayList) {
            return arrayList.toArray();
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T[] toArray(T[] array) {
        synchronized (arrayList) {
            int arrayListSize = arrayList.size();
            T[] arrayListData = (T[]) arrayList.toArray();

            if (array.length < arrayList.size())
                return (T[]) Arrays.copyOf(arrayListData, arrayListSize, array.getClass());

            System.arraycopy(arrayListData, 0, array, 0, arrayListSize);

            if (array.length > arrayListSize)
                array[arrayListSize] = null;

            return array;
        }
    }

    @Override
    public E get(int index) {
        synchronized (arrayList) {
            return arrayList.get(index);
        }
    }

    @Override
    public E set(int index, E element) {
        synchronized (arrayList) {
            return arrayList.set(index, element);
        }
    }

    @Override
    public boolean add(E element) {
        synchronized (arrayList) {
            return arrayList.add(element);
        }
    }

    @Override
    public void add(int index, E element) {
        synchronized (arrayList) {
            arrayList.add(index, element);
        }
    }

    @Override
    public E remove(int index) {
        synchronized (arrayList) {
            return arrayList.remove(index);
        }
    }

    @Override
    public boolean remove(Object object) {
        synchronized (arrayList) {
            return arrayList.remove(object);
        }
    }

    @Override
    public boolean containsAll(Collection<?> collection) {
        synchronized (arrayList) {
            return arrayList.containsAll(collection);
        }
    }

    @Override
    public void clear() {
        synchronized (arrayList) {
            arrayList.clear();
        }
    }

    @Override
    public boolean addAll(Collection<? extends E> collection) {
        synchronized (arrayList) {
            return arrayList.addAll(collection);
        }
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> collection) {
        synchronized (arrayList) {
            return arrayList.addAll(index, collection);
        }
    }

    @Override
    public boolean removeAll(Collection<?> collection) {
        synchronized (arrayList) {
            return arrayList.removeAll(collection);
        }
    }

    @Override
    public boolean retainAll(Collection<?> collection) {
        synchronized (arrayList) {
            return arrayList.retainAll(collection);
        }
    }

    /**
     * Must be manually synchronized by user
      */
    @Override
    public ListIterator<E> listIterator(int index) {
        return arrayList.listIterator(index);
    }

    /**
     * Must be manually synchronized by user
     */
    @Override
    public ListIterator<E> listIterator() {
        return arrayList.listIterator();
    }

    /**
     * Must be manually synchronized by user
     */
    @Override
    public Iterator<E> iterator() {
        return arrayList.iterator();
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        synchronized (arrayList) {
            return arrayList.subList(fromIndex, toIndex);
        }
    }

    /**
     * Must be manually synchronized by user
     */
    @Override
    public void forEach(Consumer<? super E> action) {
        arrayList.forEach(action);
    }

    /**
     * Must be manually synchronized by user
     */
    @Override
    public Spliterator<E> spliterator() {
        return arrayList.spliterator();
    }

    @Override
    public boolean removeIf(Predicate<? super E> filter) {
        synchronized (arrayList) {
            return arrayList.removeIf(filter);
        }
    }

    @Override
    public void replaceAll(UnaryOperator<E> operator) {
        synchronized (arrayList) {
            arrayList.replaceAll(operator);
        }
    }

    @Override
    public void sort(Comparator<? super E> comparator) {
        synchronized (arrayList) {
            arrayList.sort(comparator);
        }
    }

    @Override
    public int hashCode() {
        synchronized (arrayList) {
            return arrayList.hashCode();
        }
    }

    @Override
    public boolean equals(Object object) {
        synchronized (arrayList) {
            return object == this ||
                    object instanceof ThreadSafeArrayList
                            && this.arrayList.equals(((ThreadSafeArrayList) object).arrayList);
        }
    }
}
