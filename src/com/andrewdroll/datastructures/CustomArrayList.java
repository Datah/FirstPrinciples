/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.andrewdroll.datastructures;

import java.util.List;
import java.util.ListIterator;

/**
 *
 * @author Andrew
 */
public class CustomArrayList<T> {

    private class Wrapper<T> {

        T val;

        private Wrapper(T val) {
            this.val = val;
        }
    }

    public class CListIterator implements ListIterator<T> {

        private int position = 0;

        private T current;
        private T next;
        private T previous;

        public CListIterator(int n) {
            if (n >= currSize || n < 0) {
                n = 0;
            }
            next = vals[n].val;
            position = n;

            if (n == 0) {
                current = null;
                previous = null;
            } else if (n == 1) {
                current = vals[n - 1].val;
                previous = null;
            } else {
                current = vals[n - 1].val;
                previous = vals[n - 2].val;
            }
        }

        @Override
        public void add(T element) {
        }

        @Override
        public void set(T newEle) {
            if (newEle != null) {
                vals[position] = new Wrapper<T>(newEle);
            }
        }

        @Override
        public boolean hasNext() {
            return position < currSize;
        }

        @Override
        public boolean hasPrevious() {
            return position > 0;
        }

        @Override
        public void remove() {
            CustomArrayList.this.remove(current);
        }

        @Override
        public int previousIndex() {
            return position - 1;
        }

        @Override
        public int nextIndex() {
            if (hasNext()) {
                return position;
            } else {
                return -1;
            }
        }

        @Override
        public T previous() {
            if (position > 0) {
                position -= 1;
                current = previous;
                next = current;
                if (position > 0) {
                    previous = vals[position - 1].val;
                } else {
                    previous = null;
                }
                return current;
            } else {
                return null;
            }
        }

        @Override
        public T next() {
            if (hasNext()) {
                position += 1;
                previous = current;
                current = next;
                if (hasNext()) {
                    next = vals[position].val;
                }
                return current;
            } else {
                return null;
            }
        }

    }

    static int DEFAULT_SIZE = 100;
    static double DEFAULT_LOAD_FACTOR = 0.75;

    private volatile int currSize = 0;
    private volatile int maxSize;
    private volatile int reindexSize;

    private volatile Wrapper<T>[] vals;

    public CustomArrayList() {
        this(DEFAULT_SIZE, DEFAULT_LOAD_FACTOR);
    }

    public CustomArrayList(int size) {
        this(size, DEFAULT_LOAD_FACTOR);
    }

    public CustomArrayList(int size, double loadFactor) {
        if (Double.compare(loadFactor, 0) <= 0) {
            loadFactor = DEFAULT_LOAD_FACTOR;
        }
        if (size > 0) {
            vals = new Wrapper[size];
            maxSize = size;
        } else {
            vals = new Wrapper[DEFAULT_SIZE];
            maxSize = DEFAULT_SIZE;
        }
        reindexSize = (int) (maxSize * loadFactor);
    }

    public boolean add(T val) {
        if (val != null) {
            Wrapper<T> wrap = new Wrapper<T>(val);
            vals[currSize] = wrap;
            currSize++;
            if (currSize >= reindexSize) {
                reindex(null);
            }
            return true;
        } else {
            return false;
        }
    }

    public void add(int pos, T val) {
    }

    public T get(int index) {
        if (index < currSize) {
            return vals[index].val;
        } else {
            return null;
        }
    }

    public boolean remove(Object item) {
        if (item != null) {
            return reindex(item);
        } else {
            return false;
        }
    }

    public T remove(int index) {
        if (index >= 0 && index < currSize) {
            return reindex(index);
        } else {
            return null;
        }
    }

    public ListIterator<T> listIterator() {
        return new CListIterator(0);
    }

    public ListIterator<T> listIterator(int n) {
        return new CListIterator(n);
    }

    public int lastIndexOf(Object o) {
        return -1;
    }

    public int indexOf(Object o) {
        return -1;
    }

    public CustomArrayList<T> subList(int n, int m) {
        if (m > n) {
            if (m > currSize) {
                m = currSize;
            }
            if (n < 0) {
                n = 0;
            }
            CustomArrayList<T> subList = new CustomArrayList<T>(m - n);
            for (int i = n; i < m; i++) {
                subList.add(this.get(i));
            }
            return subList;
        } else {
            return null;
        }
    }

    private boolean reindex(Object toRemove) {
        int newSize = currSize * 2;
        Wrapper<T>[] newArray = new Wrapper[newSize];
        int newCurrSize = 0;
        boolean found = false;
        if (toRemove == null) {
            found = true;
            for (int i = 0; i < vals.length; i++) {
                newArray[newCurrSize] = vals[i];
                vals[i] = null;
                newCurrSize++;
            }
        } else {
            for (int i = 0; i < vals.length; i++) {
                if (!vals[i].val.equals(toRemove)) {
                    newArray[newCurrSize] = vals[i];
                    vals[i] = null;
                    newCurrSize++;
                } else {
                    found = true;
                }
            }
        }
        vals = newArray;
        currSize = newCurrSize;
        maxSize = newSize;
        return found;
    }

    private T reindex(int toRemove) {
        int newSize = currSize * 2;
        Wrapper<T>[] newArray = new Wrapper[newSize];
        int newCurrSize = 0;
        T found = null;
        for (int i = 0; i < currSize; i++) {
            if (toRemove != i) {
                newArray[newCurrSize] = vals[i];
                newCurrSize++;
            } else {
                found = vals[i].val;
            }
        }
        vals = newArray;
        currSize = newCurrSize;
        maxSize = newSize;
        return found;
    }
}
