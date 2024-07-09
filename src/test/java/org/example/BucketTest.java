package org.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.management.openmbean.KeyAlreadyExistsException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BucketTest {

    private Bucket<String, String> bucket;

    @BeforeEach
    void setUp() {
        bucket = new Bucket<>();
        bucket.add("Hello", "world");
    }

    @Test
    public void addTest() {
        bucket.containsKey("Hello");
        bucket.containsValue("world");
    }

    @Test
    public void addExistingKeyTest() {
        Assertions.assertThrows(KeyAlreadyExistsException.class, () -> bucket.add("Hello", "WORLD"));
    }

    @Test
    public void getExistingKeyTest() {
        Assertions.assertEquals("world", bucket.get("Hello"));
    }

    @Test
    public void getNotExistingKeyTest() {
        Assertions.assertNull(bucket.get("HELLO"));
    }

    @Test
    public void removeTest() {
        bucket.remove("Hello");
        Assertions.assertNull(bucket.get("Hello"));
    }

    @Test
    public void removeNotExistingKeyTest() {
        Bucket<String, String> bucket = new Bucket<>();
        Assertions.assertDoesNotThrow(() -> bucket.remove("HELLO"));
    }

    @Test
    public void containsKeyTest() {
        Assertions.assertTrue(bucket.containsKey("Hello"));
    }

    @Test
    public void containsNotExistingKeyTest() {
        Assertions.assertFalse(bucket.containsKey("HELLO"));
    }

    @Test
    public void containsValueTest() {
        Assertions.assertTrue(bucket.containsValue("world"));
    }

    @Test
    public void containsNotExistingValueTest() {
        Assertions.assertFalse(bucket.containsValue("WORLD"));
    }

    @Test
    public void getKeySetTest() {
        Set<String> keySet = new HashSet<>();
        keySet.add("Hello");
        keySet.add("HELLO");

        bucket.add("HELLO", "WORLD");

        Assertions.assertEquals(keySet, bucket.getKeySet());
    }

    @Test
    public void getValuesTest() {
        List<String> values = new ArrayList<>();
        values.add("world");
        values.add("WORLD");

        bucket.add("HELLO", "WORLD");

        Assertions.assertEquals(values, bucket.getValues());
    }

    @Test
    public void getNodesTest() {
        Set<Node<String, String>> nodes = new HashSet<>();
        nodes.add(new Node<>("Hello", "world"));
        nodes.add(new Node<>("HELLO", "WORLD"));

        bucket.add("HELLO", "WORLD");

        Assertions.assertEquals(nodes, bucket.getNodeSet());
    }

    @Test
    public void bigTest() {
        Bucket<String, String> bucket = new Bucket<>();
        int TEST_SIZE = 10000;
        for (int i = 0; i < TEST_SIZE; i++) {
            bucket.add("Hello" + i, "world" + i);
        }
        Assertions.assertEquals(bucket.size(), TEST_SIZE);
        Assertions.assertEquals(bucket.get("Hello1"), "world1");
        Assertions.assertEquals(bucket.get("Hello123"), "world123");
        Assertions.assertEquals(bucket.get("Hello321"), "world321");
    }
}
