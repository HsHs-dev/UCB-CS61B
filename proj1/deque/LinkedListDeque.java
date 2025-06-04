package deque;

import com.sun.nio.sctp.SendFailedNotification;

public class LinkedListDeque<T> {

    private class Node {
        Node prev;
        T item;
        Node next;

        public Node(Node prev, T item, Node next) {
            this.prev = prev;
            this.item = item;
            this.next = next;
        }

    }

    private Node sentinel;
    private int size;

    public LinkedListDeque() {
        sentinel = new Node(null, null, null);
        sentinel.prev = sentinel;
        sentinel.next = sentinel;
        size = 0;
    }

    /**
     * Adds an item to the front of the deque
     * @param item item to be added
     */
    public void addFirst(T item) {
        Node first = new Node(null, item, null);
        if (size == 0) {
            first.next = sentinel;
            first.prev = sentinel;
            sentinel.prev = first;
            sentinel.next = first;
            size++;
            return;
        }

        first.next = sentinel.next;
        first.prev = sentinel;
        first.next.prev = first;
        sentinel.next = first;
        size++;

    }

    /**
     * Adds item to the back of the deque
     * @param item item to be added
     */
    public void addLast(T item) {
        Node last = new Node(null, item, null);
        last.next = sentinel;
        last.prev = sentinel.prev;
        sentinel.prev.next = last;
        sentinel.prev = last;
        size++;
    }

    /**
     * @return true if deque is empty
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * @return size of the deque
     */
    public int size() {
        return size;
    }

    /**
     * prints the item of the deque from first to last, seperated by a space.
     * prints a newline at the end
     */
    public void printDeque() {
        Node node = sentinel.next;
        while (node != sentinel) {
            System.out.print(node.item + " ");
            node = node.next;
        }
        System.out.println();
    }

    /**
     * removes the first item from the deque
     * @return first item from the deque
     */
    public T removeFirst() {
        if (size == 0) return null;
        T item = sentinel.next.item;
        Node first = sentinel.next;
        sentinel.next.next.prev = sentinel;
        sentinel.next = sentinel.next.next;
        first = null;
        size--;
        return item;
    }

    /**
     * removes the last item from the deque
     * @return last item from the deque
     */
    public T removeLast() {
        if (size == 0) return null;
        T item = sentinel.prev.item;
        Node last = sentinel.prev;
        sentinel.prev.prev.next = sentinel;
        sentinel.prev = sentinel.prev.prev;
        last = null;
        size--;
        return item;
    }

    /**
     * @param index index of the target item
     * @return the item at the index place
     */
    public T get(int index) {
        if (size == 0) return null;
        int target = index;
        int counter;
        Node node;
        // start from the closer end to the target
        if (index <= (size / 2)) {
            counter = 0;
            node = sentinel.next;
            while (counter != target) {
                node = node.next;
                counter++;
            }
            return node.item;
        } else {
            counter = size - 1;
            node = sentinel.prev;
            while (counter != target) {
                node = node.prev;
                counter--;
            }
            return node.item;
        }
    }

}
