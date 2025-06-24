public interface DisjointSets<T> {

    /** Connects two items P and Q */
    void connect(T p, T q);

    /** Checks to see if two items are connected */
    boolean isConnected(T p, T q);
}