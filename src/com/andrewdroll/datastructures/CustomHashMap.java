/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.andrewdroll.datastructures;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.ListIterator;
import java.util.Random;

/**
 *
 * @author Andrew
 */
public class CustomHashMap<T, S> {

    private static int DEFAULT_SIZE = 1000;

    private CustomArrayList<Entry<T, S>>[] map;

    private int numBuckets;

    public class Entry<T, S> {

        private T key;
        private S value;

        private Entry(T key, S value) {
            this.key = key;
            this.value = value;
        }
    }

    public CustomHashMap(int n) {
        map = new CustomArrayList[n];
        numBuckets = n;
    }

    public CustomHashMap() {
        this(DEFAULT_SIZE);
    }

    public void put(T key, S value) {
        int bucket = (key.hashCode() % numBuckets + numBuckets) % numBuckets;
        if (map[bucket] == null) {
            map[bucket] = new CustomArrayList<Entry<T, S>>(20);
        }
        Entry<T, S> ent = new Entry<T, S>(key, value);
        map[bucket].add(ent);
    }

    public S get(T key) {
        int bucket = (key.hashCode() % numBuckets + numBuckets) % numBuckets;
        CustomArrayList<Entry<T, S>> bucketList = map[bucket];
        if (bucketList != null) {
            ListIterator<Entry<T, S>> it = bucketList.listIterator();
            while (it.hasNext()) {
                Entry<T, S> ent = it.next();
                if (ent.key.equals(key)) {
                    return ent.value;
                }
            }
        }
        return null;
    }

    public static void main(String[] args) {
        HashMap<Integer, Double> testMap = new HashMap<>(100000);
        CustomHashMap<Integer, Double> testCMap = new CustomHashMap<>(100000);
        Random oneR = new Random(20);
        Random twoR = new Random(20);
        int NUM_TO_PUT = 10000000;
        int NUM_TO_GET = 1000000;

        ArrayList<Double> defGot = new ArrayList<>();
        ArrayList<Double> meGot = new ArrayList<>();

        //test Java implementation
        long startDef = System.currentTimeMillis();
        for (int i = 0; i < NUM_TO_PUT; i++) {
            testMap.put(i, Math.random());
        }
        long eDefPut = System.currentTimeMillis() - startDef;
        long sDefGet = System.currentTimeMillis();
        for (int i = 0; i < NUM_TO_GET; i++) {
            defGot.add(testMap.get(oneR.nextInt() % NUM_TO_PUT));
        }
        long eDefGet = System.currentTimeMillis() - sDefGet;
        long elapsedDef = System.currentTimeMillis() - startDef;
        System.out.println("Default elapsed: " + elapsedDef + " and got " + defGot.size() + " values. "
                + "Put time: " + eDefPut + " get time: " + eDefGet);

        //test my implementation
        long startMe = System.currentTimeMillis();
        for (int i = 0; i < NUM_TO_PUT; i++) {
            testCMap.put(i, Math.random());
        }
        long eMePut = System.currentTimeMillis() - startMe;
        long sMeGet = System.currentTimeMillis();
        for (int i = 0; i < NUM_TO_GET; i++) {
            meGot.add(testCMap.get(twoR.nextInt() % NUM_TO_PUT));
        }
        long eMeGet = System.currentTimeMillis() - sMeGet;
        long elapsedMe = System.currentTimeMillis() - startMe;

        System.out.println("Me elapsed: " + elapsedMe + " and got " + meGot.size() + " values. "
                + "Put time: " + eMePut + " get time: " + eMeGet);

    }

}
