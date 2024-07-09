package org.example;

import java.util.Map.Entry;
import java.util.Objects;

/**
 * Node entry of Hashmap. Stores a pair of key and value
 * @param <K> Type of key
 * @param <V> Type of value
 */
public class Node<K, V> implements Entry<K, V> {
    private K key;
    private V value;

    /**
     *
     * @param key
     * @param value
     */
    public Node(K key, V value) {
        this.key = key;
        this.value = value;
    }

    /**
     * Getter to get key in Node pair
     * @return K key
     */
    @Override
    public K getKey() {
        return key;
    }

    /**
     * Getter to get value in Node pair
     * @return V value
     */
    @Override
    public V getValue() {
        return value;
    }

    /**
     * Setter of value
     * @param value new value to be stored in this entry
     * @return V value
     */
    @Override
    public V setValue(V value) {
        return this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node<?, ?> node = (Node<?, ?>) o;
        return Objects.equals(key, node.key) && Objects.equals(value, node.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, value);
    }
}
