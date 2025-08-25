package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

public class AvlTreeTest {

    private AvlTree avlTree;

    @BeforeEach
    public void setup() {
        avlTree = new AvlTree();
    }

    @Test
    public void testInsertAndSearch() {
        avlTree.insert(10);
        avlTree.insert(20);
        avlTree.insert(30);

        int result = avlTree.search(20);
        assertEquals(20, result);
    }

    @Test
    public void testInsertAndSearchForNonExistingElement() {
        avlTree.insert(10);
        avlTree.insert(20);
        avlTree.insert(30);

        int result = avlTree.search(40);
        assertEquals(-1, result);
    }

    @ParameterizedTest
    @MethodSource("provideInsertTestCases")
    public void testInsertMultiple(int[] elements, int[] expectedOrder) {
        for (int el : elements) {
            avlTree.insert(el);
        }

        String result = avlTree.toString();
        String expected = arrayToString(expectedOrder);
        assertEquals(expected, result);
    }

    @Test
    public void testDeleteLeafNode() {
        avlTree.insert(10);
        avlTree.insert(20);
        avlTree.insert(30);

        avlTree.delete(10);

        int result = avlTree.search(10);
        assertEquals(-1, result);
    }

    @Test
    public void testDeleteNodeWithOneChild() {
        avlTree.insert(10);
        avlTree.insert(5);
        avlTree.insert(20);
        avlTree.insert(15);

        avlTree.delete(20);

        int result = avlTree.search(20);
        assertEquals(-1, result);
    }

    @Test
    public void testDeleteNodeWithTwoChildren() {
        avlTree.insert(10);
        avlTree.insert(5);
        avlTree.insert(20);
        avlTree.insert(15);

        avlTree.delete(10);

        int result = avlTree.search(10);
        assertEquals(-1, result);
    }

    private static Stream<Arguments> provideInsertTestCases() {
        return Stream.of(
                Arguments.of(new int[]{10, 20, 30}, new int[]{10, 20, 30}),
                Arguments.of(new int[]{30, 20, 10}, new int[]{10, 20, 30}),
                Arguments.of(new int[]{5, 10, 15, 20}, new int[]{5, 10, 15, 20})
        );
    }

    private String arrayToString(int[] array) {
        StringBuilder sb = new StringBuilder();
        for (int el : array) {
            sb.append(el).append(" ");
        }
        return sb.toString().trim();
    }



    @Test
    public void testRootInsertSingleElement() {
        AvlTree tree = new AvlTree();
        tree.insert(10);
        assertEquals(10, tree.getRoot().getKey());
    }

    @Test
    public void testRootInsertMultipleElements() {
        AvlTree tree = new AvlTree();
        tree.insert(10);
        assertEquals(10, tree.getRoot().getKey());
        tree.insert(20);
        assertEquals(10, tree.getRoot().getKey());
        tree.insert(5);
        assertEquals(10, tree.getRoot().getKey());
        tree.insert(15);
        assertEquals(10, tree.getRoot().getKey());
        tree.insert(30);
        assertEquals(10, tree.getRoot().getKey());
    }

    @Test
    public void testRootDeleteRootElement() {
        AvlTree tree = new AvlTree();
        tree.insert(10);
        assertEquals(10, tree.getRoot().getKey());
        tree.insert(20);
        assertEquals(10, tree.getRoot().getKey());
        tree.insert(5);
        assertEquals(10, tree.getRoot().getKey());

        tree.delete(10);
        assertEquals(20, tree.getRoot().getKey());

        tree.delete(20);
        assertEquals(5, tree.getRoot().getKey());

        tree.delete(5);
        assertNull(tree.getRoot());
    }

    @Test
    public void testHeightInsertSingleElement() {
        AvlTree tree = new AvlTree();
        tree.insert(10);
        assertEquals(1, tree.getRoot().getHeight());
    }

    @Test
    public void testHeightInsertMultipleElements() {
        AvlTree tree = new AvlTree();
        tree.insert(10);
        assertEquals(1, tree.getRoot().getHeight());
        tree.insert(20);
        assertEquals(2, tree.getRoot().getHeight());
        tree.insert(5);
        assertEquals(2, tree.getRoot().getHeight());
        tree.insert(15);
        assertEquals(3, tree.getRoot().getHeight());
        tree.insert(30);
        assertEquals(3, tree.getRoot().getHeight());
    }

    @Test
    public void testDeleteRootElement() {
        AvlTree tree = new AvlTree();
        tree.insert(10);
        assertEquals(10, tree.getRoot().getKey());
        tree.insert(20);
        assertEquals(10, tree.getRoot().getKey());
        tree.insert(5);
        assertEquals(10, tree.getRoot().getKey());

        tree.delete(10);
        assertEquals(20, tree.getRoot().getKey());

        tree.delete(5);
        assertEquals(20, tree.getRoot().getKey());

        tree.delete(20);
        assertNull(tree.getRoot());
    }
}

