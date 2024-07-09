package org.example;

import java.util.*;

public class CustomMap<K, V> implements Map<K, V> {
    private List<Bucket<K,V>> buckets;
    private int size;
    private int ARRAY_LENGTH = 100;

    public CustomMap() {
        initBuckets();
        size = 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean containsKey(Object key) {
        int bucketIndex = getBucketIndex((K) key);
        return buckets.get(bucketIndex).containsKey((K) key);
    }

    @Override
    public boolean containsValue(Object value) {
        for (Bucket<K,V> bucket : buckets) {
            if (bucket.containsValue((V)value)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public V get(Object key) {
        int bucketIndex = getBucketIndex((K) key);
        return buckets.get(bucketIndex).get((K)key);
    }

    @Override
    public V put(K key, V value) {
        if (loadFactor() > 0.75) {
            expandBuckets();
        }
        addNewPair(key, value);
        size += 1;
        return value;
    }

    @Override
    public V remove(Object key) {
        if (isEmpty()) {
            return null;
        }
        int bucketIndex = getBucketIndex((K) key);
        Bucket<K,V> bucket = buckets.get(bucketIndex);
        V value = bucket.get((K)key);
        bucket.remove((K)key);
        size -= 1;
        return value;
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        for (Map.Entry<? extends K, ? extends V> entry : m.entrySet()) {
            put(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public void clear() {
        buckets.clear();
        size = 0;
    }

    @Override
    public Set<K> keySet() {
        Set<K> keys = new HashSet<>();
        for (Bucket<K,V> bucket : buckets) {
            keys.addAll(bucket.getKeySet());
        }
        return keys;
    }

    @Override
    public Collection<V> values() {
        List<V> values = new ArrayList<>();
        for (Bucket<K,V> bucket : buckets) {
            values.addAll(bucket.getValues());
        }
        return values;
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        Set<Entry<K, V>> entries = new HashSet<>();
        for (Bucket<K,V> bucket : buckets) {
            Set<Node<K, V>> nodes = bucket.getNodeSet();
            entries.addAll(nodes);
        }
        return entries;
    }

    private int getBucketIndex(K key) {
        return Math.abs(key.hashCode()) % ARRAY_LENGTH;
    }

    private float loadFactor() {
//        int busyBuckets = buckets.stream()
//                .mapToInt((item-> item.isEmpty() ? 0 : 1))
//                .sum();
//        return (float) busyBuckets / ARRAY_LENGTH;
        return (float) size() / ARRAY_LENGTH;
    }

    private void initBuckets() {
        ArrayList<Bucket<K,V>> newBuckets = new ArrayList<>();
        for (int i = 0; i < ARRAY_LENGTH; i++) {
            newBuckets.add(new Bucket<>());
        }
        this.buckets = newBuckets;
    }

    private void expandBuckets() {
        Set<Entry<K,V>> entries = entrySet();
        ARRAY_LENGTH *= 2;
        initBuckets();
        for (Entry<K,V> entry : entries) {
            addNewPair(entry.getKey(), entry.getValue());
        }
    }

    private void addNewPair(K key, V value) {
        int bucketIndex = getBucketIndex(key);
        buckets.get(bucketIndex).add(key, value);
    }
}
