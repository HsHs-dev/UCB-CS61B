public class SLList <T> {

    private class Node {

        public T item;
        public Node next;

        public Node(T i, Node n) {
            item = i;
            next = n;
        }

    }

//    private Node first;
    /** The first item if it exists, it will be in first.next */
    private Node first;
    private int size;

    public SLList(T x) {
        first = new Node(x, null);
        first.next = new Node(x, null);
        size = 1;
    }

    public void addFirst(T x) {
        first.next = new Node(x, first.next);
        size++;
    }

    public T getFirst() {
        return first.next.item;
    }

    public void addLast(T x) {
        size++;
        // ugly
//        if (first == null) {
//            first = new Node(x, null);
//            return;
//        }
        Node node = first;
        while (node.next != null) {
            node = node.next;
        }
        node.next = new Node(x, null);
    }
    /*
    @source JHug
    public void addLast(int x) {
      Node node = first;
      while (node.next != null) {
        node = node.next;
      }
      node.next = new Node(x, null);
    }
    */

    // O(1)
    public int size() {
        return this.size;
    }

    // O(n)
    // public int size() {
    //     int size = 1;
    //     Node node = first.next;
    //     while (node != null) {
    //         size++;
    //         node = node.next;
    //     }
    //     return size;
    // }

    /*
    @source JHug
    // Returns the size of list that starts at Node node.
    private static int size(Node node) {
      if (node.next == null) return 1;
      return 1 + size(node.next);
    }

    public int size() {
      return size(first);
    }
    */

    public String print() {
        String list = first.next.item + " -> ";
        Node node = first.next.next;
        while (node.next != null) {
            list += node.item + " -> ";
            node = node.next;
        }
        list += node.item;
        return list;
    }

}
