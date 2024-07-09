package org.example;

import javax.management.openmbean.KeyAlreadyExistsException;
import java.util.*;
import java.util.stream.Collectors;

public class Bucket<K, V> {
    private Queue<Node<K, V>> bucket;

    public Bucket() {
        this.bucket = new ArrayDeque<>();
    }

    public void add(K key, V value) {
        if(containsKey(key)) {
            throw new KeyAlreadyExistsException();
        }
        bucket.add(new Node<>(key, value));
    }

    public V get(K key) {
        for (Node<K, V> node : bucket) {
            if (node.getKey().equals(key)) {
                return node.getValue();
            }
        }
        return null;
    }

    public void remove(K key) {
        bucket.removeIf(node -> node.getKey().equals(key));
    }

    public boolean containsKey(K key) {
        return bucket.stream().anyMatch(node->node.getKey().equals(key));
    }

    public boolean containsValue(V value) {
        for (Node<K, V> node : bucket) {
            if (node.getValue().equals(value)) {
                return true;
            }
        }
        return false;
    }

    public Collection<K> getKeySet() {
        return bucket.stream().map(Node::getKey).collect(Collectors.toSet());
    }

    public Collection<V> getValues() {
        return bucket.stream().map(Node::getValue).collect(Collectors.toList());
    }

    public Set<Node<K, V>> getNodeSet() {
        return new HashSet<>(bucket);
    }

    public int size() {
        return bucket.size();
    }

    public boolean isEmpty() {
        return bucket.isEmpty();
    }
}
