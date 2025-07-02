package bstmap;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {

    private class Node {
        K key;
        V value;
        Node left, right;
        Node(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    private Node root;
    private int size;

    public BSTMap() {
        root = null;
        size = 0;
    }

    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    @Override
    public boolean containsKey(K key) {

        if (root == null) {
            return false;
        }
        return containsKey(root, key);
    }
    private boolean containsKey(Node root, K key) {

        if (root == null) {
            return false;
        }

        if (key.equals(root.key)) {
            return true;
        } else if (key.compareTo(root.key) > 0) {
            return containsKey(root.right, key);
        } else {
            return containsKey(root.left, key);
        }
    }

    @Override
    public V get(K key) {
        Node currNode = root;
        while (currNode != null) {
            int comp = key.compareTo(currNode.key);
            if (key.equals(currNode.key)) {
                return currNode.value;
            } else if (comp > 0) {
                currNode = currNode.right;
            } else {
                currNode = currNode.left;
            }
        }
        return null;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void put(K key, V value) {
        root = put(root, key, value);
    }
    private Node put(Node root, K key, V value) {
        if (root == null) {
            size++;
            return new Node(key, value);
        }

        if (key.compareTo(root.key) > 0) {
            root.right = put(root.right, key, value);
        } else if (key.compareTo(root.key) < 0) {
            root.left = put(root.left, key, value);
        } else {
            root.value = value;
        }

        return root;
    }

    @Override
    public Set<K> keySet() {
        Set<K> keys = new HashSet<>();
        addKey(root, keys);
        return keys;
    }
    private void addKey(Node root, Set<K> set) {
        if (root == null) return;
        set.add(root.key);
        addKey(root.left, set);
        addKey(root.right, set);
    }

    @Override
    public V remove(K key) {
        V val = get(key);
        if (val == null) return null;
        root = deleteKey(root, key);
        size--;
        return val;
    }
    private Node deleteKey(Node root, K key) {
        if (root == null) return null;
        int comp = key.compareTo(root.key);

        if (comp < 0) {
            root.left = deleteKey(root.left, key);
        } else if (comp > 0) {
            root.right = deleteKey(root.right, key);
        } else {
            // covers if node has 0 or 1 child
            if (root.right == null) return root.left;
            if (root.left == null) return root.right;

            // if node has 2 children
            Node temp = root;  // preserve the current node so we can get it's left hand
            root = min(temp.right); // make the node equals its successor
            root.right = deleteMin(temp.right);  // delete the successor (while preserving its right hand)
            root.left = temp.left; // wire the left hand
        }

        return root;

    }
    private Node min(Node root) {
        if (root == null) return null;
        while (root.left != null) {
            root = root.left;
        }
        return root;
    }
    private Node deleteMin(Node root) {
        if (root.left == null) return root.right;
        root.left = deleteMin(root.left);
        return root;
    }

    @Override
    public V remove(K key, V value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator<K> iterator() {
        throw new UnsupportedOperationException();
    }
}
