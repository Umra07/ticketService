package com.jfb.tickets.structures;

import java.util.Arrays;

public class CustomArrayList<E> {
    private int size = 0;
    private E[] store;

    @SuppressWarnings("unchecked")
    public CustomArrayList(int capacity) {
        if(capacity <= 0) {
            throw new IllegalArgumentException("Capacity must be greater than zero.");
        }
        store = (E[]) new Object[capacity];
    }

    public int getSize() {
        return size;
    }

    public void add(E element) {
        resize();
        store[size++] = element;
    }

    public E getByIndex(int index) {
        checkIndex(index);
        return store[index];
    }

    public E deleteByIndex(int index) {
        checkIndex(index);

        E removedElement = store[index];

        for(int i = index; i < size - 1; i++) {
            store[i] = store[i + 1];
        }

        store[size - 1] = null;
        size--;
        return removedElement;
    }

    private void resize() {
        if(size == store.length) {
            int newCapacity = store.length * 2;
            store = Arrays.copyOf(store, newCapacity);
        }
    }

    private void checkIndex(int index) {
        if(index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of range.");
        }
    }
}
