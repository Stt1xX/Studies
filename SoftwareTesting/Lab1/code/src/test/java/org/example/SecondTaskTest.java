package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

public class SecondTaskTest {

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
        Assertions.assertEquals(20, result);
    }

    @Test
    public void testInsertAndSearchForNonExistingElement() {
        avlTree.insert(10);
        avlTree.insert(20);
        avlTree.insert(30);

        int result = avlTree.search(40);
        Assertions.assertEquals(-1, result);
    }

    @ParameterizedTest
    @MethodSource("provideInsertTestCases")
    public void testInsertMultiple(int[] elements, int[] expectedOrder) {
        for (int el : elements) {
            avlTree.insert(el);
        }

        String result = avlTree.toString();
        String expected = arrayToString(expectedOrder);
        Assertions.assertEquals(expected, result);
    }

    @Test
    public void testDeleteLeafNode() {
        avlTree.insert(10);
        avlTree.insert(20);
        avlTree.insert(30);

        avlTree.delete(10);

        int result = avlTree.search(10);
        Assertions.assertEquals(-1, result);
    }

    @Test
    public void testDeleteNodeWithOneChild() {
        avlTree.insert(10);
        avlTree.insert(5);
        avlTree.insert(20);
        avlTree.insert(15);

        avlTree.delete(20);

        int result = avlTree.search(20);
        Assertions.assertEquals(-1, result);
    }

    @Test
    public void testDeleteNodeWithTwoChildren() {
        avlTree.insert(10);
        avlTree.insert(5);
        avlTree.insert(20);
        avlTree.insert(15);

        avlTree.delete(10);

        int result = avlTree.search(10);
        Assertions.assertEquals(-1, result);
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
}

