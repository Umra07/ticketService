package com.jfb.tickets.structures;

import java.util.LinkedList;

public class CustomHashSet<K> {
  private static final double LOAD_FACTOR = 0.75;
  private LinkedList<K>[] store;
  private int size;
  private int capacity;

  @SuppressWarnings("unchecked")
  public CustomHashSet() {
    this.capacity = 2;
    this.store = new LinkedList[capacity];
    this.size = 0;
  }

  public int size() {
    return size;
  }

  public void put(K key) {
    if (contains(key)) {
      return;
    }

    if ((double) size / capacity >= LOAD_FACTOR) {
      resize();
    }

    int listIndex = getIndex(key);

    if (store[listIndex] == null) {
      store[listIndex] = new LinkedList<>();
    }

    store[listIndex].add(key);
    size++;
  }

  public boolean contains(K key) {
    int listIndex = getIndex(key);
    LinkedList<K> list = store[listIndex];

    if (list == null) {
      return false;
    }

    return list.contains(key);
  }

  public void remove(K key) {
    int listIndex = getIndex(key);
    LinkedList<K> list = store[listIndex];

    if (list != null && list.remove(key)) {
      size--;
    }
  }

  private int getIndex(K key) {
    return Math.abs(key.hashCode()) % capacity;
  }

  private void resize() {
    capacity *= 2;
    @SuppressWarnings("unchecked")
    LinkedList<K>[] newStore = new LinkedList[capacity];

    for (LinkedList<K> list : store) {
      if (list != null) {
        for (K key : list) {
          int listIndex = getIndex(key);
          if (newStore[listIndex] == null) {
            newStore[listIndex] = new LinkedList<>();
          }
          newStore[listIndex].add(key);
        }
      }
    }

    store = newStore;
  }

  public void iterate() {
    for (LinkedList<K> list : store) {
      if (list != null) {
        for (K key : list) {
          System.out.println(list + " " + key);
        }
      }
    }
  }
}
