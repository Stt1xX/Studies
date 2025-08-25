package org.example;

import lombok.Getter;

@Getter
public class AvlTree {

    private AvlTreeNode root;

    public void insert(int key){
        if (key < 0){
            throw new IllegalArgumentException("Key cannot be negative");
        }
        root = insertRec(root, key);
    }

    public void delete(int key){
        if (key < 0){
            throw new IllegalArgumentException("Key cannot be negative");
        }
        root = deleteRec(root, key);
    }

    public int search(int key){
        return searchRec(root, key) == null ? -1 : searchRec(root, key).key;
    }

    private int height(AvlTreeNode node){
        return (node == null) ? 0 : node.height;
    }

    private int getBalance(AvlTreeNode node){
        return (node == null) ? 0 : height(node.left) - height(node.right);
    }

    private AvlTreeNode searchRec(AvlTreeNode root, int key) {
        if (root == null || key < 0) return null;
        if (key < root.key) return searchRec(root.left, key);
        else if (key > root.key) return searchRec(root.right, key);
        else return root;
    }

    private AvlTreeNode deleteRec(AvlTreeNode root, int key){
        if (root == null || key < 0) return root;
        if (key < root.key)
            root.left = deleteRec(root.left, key);
        else if (key > root.key)
            root.right = deleteRec(root.right, key);
        else {

            if (root.left == null || root.right == null) {
                AvlTreeNode temp;
                if (root.left != null)
                    temp = root.left;
                else
                    temp = root.right;
                root = temp;
            } else {
                AvlTreeNode temp = minValueNode(root.right); // заменяем на правый минимальный
                root.key = temp.key;
                root.right = deleteRec(root.right, temp.key);
            }

            if (root == null) return root;

            return balanceTree(root);
        }
        return balanceTree(root);
    }

    private AvlTreeNode insertRec(AvlTreeNode root, int key){
        if (root == null) return new AvlTreeNode(key);
        if (key < 0) return root;
        if (key < root.key)
            root.left = insertRec(root.left, key);
        else
            root.right = insertRec(root.right, key);

        return balanceTree(root);
    }

    private AvlTreeNode minValueNode(AvlTreeNode root){
        AvlTreeNode current = root;
        while (current.left != null) {
            current = current.left;
        }
        return current;
    }

    private AvlTreeNode balanceTree(AvlTreeNode root){

        root.height = 1 + Math.max(height(root.left), height(root.right));

        int balance = getBalance(root);

        if (balance > 1 && getBalance(root.left) >= 0)
            return rightRotate(root);
        if (balance < -1 && getBalance(root.right) < 0)
            return leftRotate(root);
        if (balance > 1 && getBalance(root.left) < 0){
            root.left = leftRotate(root.left);
            return rightRotate(root);
        }
        if (balance < -1 && getBalance(root.right) >= 0){
            root.right = rightRotate(root.right);
            return leftRotate(root);
        }

        return root;
    }

    private AvlTreeNode rightRotate(AvlTreeNode root){
        AvlTreeNode temp = root.left;
        AvlTreeNode tempRight = temp.right;
        temp.right = root;
        root.left = tempRight;

        root.height = 1 + Math.max(height(root.left), height(root.right));
        temp.height = 1 + Math.max(height(temp.left), height(temp.right));
        return temp;
    }

    private AvlTreeNode leftRotate(AvlTreeNode root){
        AvlTreeNode temp = root.right;
        AvlTreeNode tempLeft = temp.left;
        temp.left = root;
        root.right = tempLeft;

        root.height = 1 + Math.max(height(root.left), height(root.right));
        temp.height = 1 + Math.max(height(temp.left), height(temp.right));

        return temp;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        toStringRec(root, sb);
        return sb.toString().trim();
    }

    private void toStringRec(AvlTreeNode node, StringBuilder sb) {
        if (node != null) {
            toStringRec(node.left, sb);
            sb.append(node.key).append(" ");
            toStringRec(node.right, sb);
        }
    }

    @Getter
    public static class AvlTreeNode {
        private int key;
        private int height;
        AvlTreeNode left, right;

        AvlTreeNode(int key){
            this.key = key;
            height = 1;
        }
    }
}


