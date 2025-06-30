package bstmap;

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

        if (key.equals(root.key)) {
            return true;
        } else if (key.compareTo(root.key) > 0) {
            return containsKey(root.right, key);
        } else {
            return containsKey(root.left, key);
        }

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
        throw new UnsupportedOperationException();
    }

    @Override
    public V remove(K key) {
        throw new UnsupportedOperationException();
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
