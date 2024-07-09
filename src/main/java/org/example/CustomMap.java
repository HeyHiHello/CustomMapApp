package org.example;

import java.util.*;

/**
 * Implementation of HashMap
 * Collisions solved by chain method
 * All pairs of keys and values are stored in List of Buckets
 * Each key is mapped by hashcode to one of the bucket
 * Pair of key and value is stored in the bucket in form of Node class
 * @param <K> key Type
 * @param <V> value Type
 */
public class CustomMap<K, V> implements Map<K, V> {
    /**
     * List of Buckets
     */
    private List<Bucket<K,V>> buckets;

    /**
     * Current amount of values in the Map
     */
    private int size;
    /**
     * Length of List of Buckets
     */
    private int ARRAY_LENGTH = 100;

    public CustomMap() {
        initBuckets();
        size = 0;
    }

    /**
     * Getter for size of Map
     * @return int size
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Check if map is empty
     */
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Check if key is in the map
     * @param key key whose presence in this map is to be tested
     */
    @Override
    public boolean containsKey(Object key) {
        int bucketIndex = getBucketIndex((K) key);
        return buckets.get(bucketIndex).containsKey((K) key);
    }

    /**
     * Check if value is in the map
     * @param value value whose presence in this map is to be tested
     */
    @Override
    public boolean containsValue(Object value) {
        for (Bucket<K,V> bucket : buckets) {
            if (bucket.containsValue((V)value)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Get value by given key
     * @param key the key whose associated value is to be returned
     * @return
     */
    @Override
    public V get(Object key) {
        int bucketIndex = getBucketIndex((K) key);
        return buckets.get(bucketIndex).get((K)key);
    }

    /**
     * Add new pair of key and value
     * @param key key with which the specified value is to be associated
     * @param value value to be associated with the specified key
     * @return
     */
    @Override
    public V put(K key, V value) {
        if (loadFactor() > 0.75) {
            expandBuckets();
        }
        addNewPair(key, value);
        size += 1;
        return value;
    }

    /**
     * Remove a pair of key and value by given key
     * @param key key whose mapping is to be removed from the map
     * @return
     */
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

    /**
     * Put all keys-values from other map to this map
     * @param m mappings to be stored in this map
     */
    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        for (Map.Entry<? extends K, ? extends V> entry : m.entrySet()) {
            put(entry.getKey(), entry.getValue());
        }
    }

    /**
     * Clear the entire map
     */
    @Override
    public void clear() {
        buckets.clear();
        size = 0;
    }

    /**
     * Get Set of all keys in the map
     * @return Set of keys
     */
    @Override
    public Set<K> keySet() {
        Set<K> keys = new HashSet<>();
        for (Bucket<K,V> bucket : buckets) {
            keys.addAll(bucket.getKeySet());
        }
        return keys;
    }

    /**
     * Get Collection (List) of all values in the map
     * @return List of values
     */
    @Override
    public Collection<V> values() {
        List<V> values = new ArrayList<>();
        for (Bucket<K,V> bucket : buckets) {
            values.addAll(bucket.getValues());
        }
        return values;
    }

    /**
     * Set of all pairs key-value in the map
     * @return Set of Entries
     */
    @Override
    public Set<Entry<K, V>> entrySet() {
        Set<Entry<K, V>> entries = new HashSet<>();
        for (Bucket<K,V> bucket : buckets) {
            Set<Node<K, V>> nodes = bucket.getNodeSet();
            entries.addAll(nodes);
        }
        return entries;
    }

    /**
     * Calculate index of bucket by hashcode of a key
     * @param key of a key-value pair to calculate index of a bucket
     * @return index of a bucket
     */
    private int getBucketIndex(K key) {
        return Math.abs(key.hashCode()) % ARRAY_LENGTH;
    }

    /**
     * Load factor of a map
     * @return float value of load factor
     */
    private float loadFactor() {
//        int busyBuckets = buckets.stream()
//                .mapToInt((item-> item.isEmpty() ? 0 : 1))
//                .sum();
//        return (float) busyBuckets / ARRAY_LENGTH;
        return (float) size() / ARRAY_LENGTH;
    }

    /**
     * Initialize bucket list
     */
    private void initBuckets() {
        ArrayList<Bucket<K,V>> newBuckets = new ArrayList<>();
        for (int i = 0; i < ARRAY_LENGTH; i++) {
            newBuckets.add(new Bucket<>());
        }
        this.buckets = newBuckets;
    }

    /**
     * Expand bucket list. Replace old pairs into new buckets.
     * Used when load factor greater than 75%
     */
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
