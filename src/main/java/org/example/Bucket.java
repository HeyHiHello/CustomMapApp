package org.example;

import javax.management.openmbean.KeyAlreadyExistsException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Collection of Nodes used as a chain for solving collisions
 * All values that has the same key hashcode wil be stored in same Bucket
 * @param <K> key Type
 * @param <V> value Type
 */
public class Bucket<K, V> {
    /**
     * Collection of key value pairs
     */
    private final Queue<Node<K, V>> bucket;

    public Bucket() {
        this.bucket = new ArrayDeque<>();
    }

    /**
     * Add a single pair of key and value in bucket
     * @param key
     * @param value
     */
    public void add(K key, V value) {
        if(containsKey(key)) {
            throw new KeyAlreadyExistsException();
        }
        bucket.add(new Node<>(key, value));
    }

    /**
     * Finds and returns value with provided key
     * @param key
     * @return V value
     */
    public V get(K key) {
        for (Node<K, V> node : bucket) {
            if (node.getKey().equals(key)) {
                return node.getValue();
            }
        }
        return null;
    }

    /**
     * Remove pair with provided key
     * @param key
     */
    public void remove(K key) {
        bucket.removeIf(node -> node.getKey().equals(key));
    }

    /**
     * Check if provided key exist in the bucket
     * @param key
     */
    public boolean containsKey(K key) {
        return bucket.stream().anyMatch(node->node.getKey().equals(key));
    }

    /**
     * Check if provided value exists in the bucket
     * @param value
     */
    public boolean containsValue(V value) {
        for (Node<K, V> node : bucket) {
            if (node.getValue().equals(value)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Get set of all keys in Bucket
     * @return Set of keys
     */
    public Collection<K> getKeySet() {
        return bucket.stream().map(Node::getKey).collect(Collectors.toSet());
    }

    /**
     * Get List if all values in Bucket
     * @return List of values
     */
    public Collection<V> getValues() {
        return bucket.stream().map(Node::getValue).collect(Collectors.toList());
    }

    /**
     * Get Set of pairs of keys and values represented by Node class
     * @return Set of Nodes of keys values
     */
    public Set<Node<K, V>> getNodeSet() {
        return new HashSet<>(bucket);
    }

    /**
     * Size of the bucket
     * @return size
     */
    public int size() {
        return bucket.size();
    }

    /**
     * Check if bucket is empty
     */
    public boolean isEmpty() {
        return bucket.isEmpty();
    }
}
