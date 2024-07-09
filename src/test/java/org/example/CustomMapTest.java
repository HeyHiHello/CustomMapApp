package org.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.management.openmbean.KeyAlreadyExistsException;
import java.util.*;

public class CustomMapTest {
    CustomMap<String, String> map;

    @BeforeEach
    void setUp() {
        map = new CustomMap<>();
        map.put("Hello", "world");
    }

    @Test
    public void sizeTest() {
        Assertions.assertEquals(1, map.size());
    }

    @Test
    public void emptyTest() {
        Map<String, String> map = new CustomMap<>();
        Assertions.assertTrue(map.isEmpty());
    }

    @Test
    public void notEmptyTest() {
        Assertions.assertFalse(map.isEmpty());
    }

    @Test
    public void containsKey() {
        Assertions.assertTrue(map.containsKey("Hello"));
    }

    @Test
    public void containsNotExistingKey() {
        Assertions.assertFalse(map.containsKey("HELLO"));
    }

    @Test
    public void containsValue() {
        Assertions.assertTrue(map.containsValue("world"));
    }

    @Test
    public void containsNotExistingValue() {
        Assertions.assertFalse(map.containsValue("HELLO"));
    }

    @Test
    public void getTest() {
        Assertions.assertEquals("world", map.get("Hello"));
    }

    @Test
    public void getNotExistingKeyTest() {
        Assertions.assertNull(map.get("HELLO"));
    }

    @Test
    public void putTest() {
        map.put("HELLO", "WORLD");
        Assertions.assertEquals("WORLD", map.get("HELLO"));
    }

    @Test
    public void putExistingKeyTest() {
        Assertions.assertThrows(KeyAlreadyExistsException.class, () -> map.put("Hello", "WORLD"));
    }

    @Test
    public void removeEmptyTest() {
        Map<String, String> map = new CustomMap<>();
        Assertions.assertNull(map.remove("Hello"));
    }

    @Test
    public void removeTest() {
        Assertions.assertEquals("world", map.remove("Hello"));
        Assertions.assertTrue(map.isEmpty());
    }

    @Test
    public void removeNotExistingKeyTest() {
        Assertions.assertNull(map.remove("HELLO"));
    }

    @Test
    public void putAllTest() {
        Map<String, String> newMap = new HashMap<>();
        newMap.put("HELLO", "WORLD");
        newMap.put("H", "W");

        map.putAll(newMap);
        Assertions.assertEquals("WORLD", map.get("HELLO"));
        Assertions.assertEquals("W", map.get("H"));
        Assertions.assertEquals(3, map.size());
    }

    @Test
    public void clearTest() {
        map.clear();
        Assertions.assertEquals(0, map.size());
    }

    @Test
    public void keySetTest() {
        Set<String> keySet = new HashSet<>();
        keySet.add("Hello");
        keySet.add("HELLO");

        map.put("HELLO", "WORLD");

        Assertions.assertEquals(keySet, map.keySet());
    }

    @Test
    public void valuesTest() {
        List<String> values = new ArrayList<>();
        values.add("world");
        values.add("WORLD");

        map.put("HELLO", "WORLD");

        Assertions.assertEquals(values, map.values());
    }

    @Test
    public void entrySetTest() {
        Set<Map.Entry<String, String>> entrySet = new HashSet<>();
        entrySet.add(new Node<>("Hello", "world"));

        Assertions.assertEquals(entrySet, map.entrySet());
    }

    @Test
    public void bigTest(){
        int TEST_SIZE = 1000000;
        Map<String, String> map = new CustomMap<>();
        String keyBase = "Hello";
        String valueBase = "world";
        for (int i = 0; i < TEST_SIZE; i++) {
            map.put(keyBase + i, valueBase + i);
        }
        Assertions.assertEquals(TEST_SIZE, map.size());
        Assertions.assertEquals(map.get("Hello1"), "world1");
        Assertions.assertEquals(map.get("Hello123"), "world123");
        Assertions.assertEquals(map.get("Hello321"), "world321");
    }
}
