package org.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class NodeTest {

    @Test
    public void createTest() {
        Node<String, String> node = new Node<>("hello", "world");
        Assertions.assertEquals("hello", node.getKey());
        Assertions.assertEquals("world", node.getValue());
    }

    @Test
    public void setValueTest() {
        Node<String, String> node = new Node<>("hello", "world");
        node.setValue("WORLD");
        Assertions.assertEquals("WORLD", node.getValue());
    }

    @Test
    public void equalsTest() {
        Node<String, String> node1 = new Node<>("hello", "world");
        Node<String, String> node2 = new Node<>("hello", "world");

        Assertions.assertEquals(node1, node2);
    }

    @Test
    public void sameEqualsTest() {
        Node<String, String> node1 = new Node<>("hello", "world");
        Node<String, String> node2 = node1;

        Assertions.assertEquals(node1, node2);
    }

    @Test
    public void nullEqualsTest() {
        Node<String, String> node1 = new Node<>("hello", "world");

        Assertions.assertNotEquals(node1, null);
    }

    @Test
    public void differentClassEqualsTest() {
        Node<String, String> node1 = new Node<>("hello", "world");
        String temp = "Hello world";
        Assertions.assertNotEquals(node1, temp);
    }

    @Test
    public void hashCodeTest() {
        Node<String, String> node = new Node<>("hello", "world");
        Assertions.assertEquals(-1107615551, node.hashCode());
    }
}
