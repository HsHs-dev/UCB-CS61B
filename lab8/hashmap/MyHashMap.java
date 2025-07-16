package hashmap;

import java.util.*;

/**
 *  A hash table-backed Map implementation. Provides amortized constant time
 *  access to elements via get(), remove(), and put() in the best case.
 *
 *  Assumes null keys will never be inserted, and does not resize down upon remove().
 *  @author YOUR NAME HERE
 */
public class MyHashMap<K, V> implements Map61B<K, V> {

    /**
     * Protected helper class to store key/value pairs
     * The protected qualifier allows subclass access
     */
    protected class Node {
        K key;
        V value;

        Node(K k, V v) {
            key = k;
            value = v;
        }
    }

    private final int DEFAULT_INITIAL_SIZE = 16;
    private final double DEFAULT_LOAD_FACTOR = 0.75;

    /** The List of buckets of type Node */
    private Collection<Node>[] buckets;

    /** The number of elements in the current map */
    private int size;

    /** The load factor of the map */
    private final double loadFactor;

    /** Constructors */
    public MyHashMap() {
        loadFactor = DEFAULT_LOAD_FACTOR;
        buckets = createTable(DEFAULT_INITIAL_SIZE);
        size = 0;
    }

    public MyHashMap(int initialSize) {
        loadFactor = DEFAULT_LOAD_FACTOR;
        buckets = createTable(initialSize);
        size = 0;
    }

    /**
     * MyHashMap constructor that creates a backing array of initialSize.
     * The load factor (# items / # buckets) should always be <= loadFactor
     *
     * @param initialSize initial size of backing array
     * @param maxLoad maximum load factor
     */
    public MyHashMap(int initialSize, double maxLoad) {
        this.loadFactor = maxLoad;
        buckets = createTable(initialSize);
        size = 0;
    }

    /**
     * Returns a new node to be placed in a hash table bucket
     */
    private Node createNode(K key, V value) {
        return new Node(key, value);
    }

    /**
     * Returns a data structure to be a hash table bucket
     *
     * The only requirements of a hash table bucket are that we can:
     *  1. Insert items (`add` method)
     *  2. Remove items (`remove` method)
     *  3. Iterate through items (`iterator` method)
     *
     * Each of these methods is supported by java.util.Collection,
     * Most data structures in Java inherit from Collection, so we
     * can use almost any data structure as our buckets.
     *
     * Override this method to use different data structures as
     * the underlying bucket type
     *
     * BE SURE TO CALL THIS FACTORY METHOD INSTEAD OF CREATING YOUR
     * OWN BUCKET DATA STRUCTURES WITH THE NEW OPERATOR!
     */
    protected Collection<Node> createBucket() {
        return new LinkedList<>();
    }

    /**
     * Returns a table to back our hash table. As per the comment
     * above, this table can be an array of Collection objects
     *
     * BE SURE TO CALL THIS FACTORY METHOD WHEN CREATING A TABLE SO
     * THAT ALL BUCKET TYPES ARE OF JAVA.UTIL.COLLECTION
     *
     * @param tableSize the size of the table to create
     */
    private Collection<Node>[] createTable(int tableSize) {
        Collection<Node>[] table = new Collection[tableSize];
        for (int i = 0; i < tableSize; i++) {
            table[i] = createBucket();
        }
        return table;
    }

    @Override
    public void clear() {
        buckets = null;
        size = 0;
    }

    @Override
    public boolean containsKey(K key) {
        return false;
    }

    @Override
    public V get(K key) {

        int bucket = key.hashCode() % buckets.length;

        for (Node node: buckets[bucket]) {
            if (node.key.equals(key)) {
                return node.value;
            }
        }

        return null;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public void put(K key, V value) {

        // check if the load factor is exceeded
        double load = (double)size / buckets.length;
        if (load >= loadFactor) {
            resize();
        }

        // compute the suitable bucket
        int bucket = key.hashCode() % buckets.length;

        // check if the key already exists
        for (Node node: buckets[bucket]) {
            if (node.key.equals(key)) {
                node.value = value;
                return;
            }
        }

        // if the key doesn't exist, add it to the table
        Node node = createNode(key, value);
        buckets[bucket].add(node);
        size++;
    }
    private void resize() {

    }

    @Override
    public Set<K> keySet() {
        Set<K> keys = new HashSet<>();

        for (Collection<Node> bucket: buckets) {
            for (Node node: bucket) {
                keys.add(node.key);
            }
        }

        return keys;
    }

    @Override
    public V remove(K key) {
        throw new UnsupportedOperationException();
    }

    public V remove(K key, V value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator<K> iterator() {
        return null;
    }

}
