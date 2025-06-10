package deque;

import java.util.Iterator;

public class LinkedListDeque<T> implements Deque<T> {

    private class Node {
        Node prev;
        T item;
        Node next;

        private Node(Node prev, T item, Node next) {
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
    @Override
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
    @Override
    public void addLast(T item) {
        Node last = new Node(null, item, null);
        last.next = sentinel;
        last.prev = sentinel.prev;
        sentinel.prev.next = last;
        sentinel.prev = last;
        size++;
    }

    /**
     * @return size of the deque
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * prints the item of the deque from first to last, seperated by a space.
     * prints a newline at the end
     */
    @Override
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
    @Override
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
    @Override
    public T removeLast() {
        if (size == 0) {
            return null;
        }
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
    @Override
    public T get(int index) {
        if (isEmpty() || index < 0 || index >= size) {
            return null;
        }
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
        } else {
            counter = size - 1;
            node = sentinel.prev;
            while (counter != target) {
                node = node.prev;
                counter--;
            }
        }
        return node.item;
    }

    /**
     * @param index index of the target item
     * @return the item at the index place
     */
    public T getRecursive(int index) {
        if (index < 0 || index >= size) {
            return null;
        }
        return getRecursive(sentinel.next, index);
    }
    private T getRecursive(Node node, int index) {
        if (index == 0) {
            return node.item;
        }
        return getRecursive(node.next, index - 1);
    }

    /**
     * @return an iterator object of this deque
     */
    public Iterator<T> iterator() {
        return new LinkedListDequeIterator();
    }

    private class LinkedListDequeIterator implements Iterator<T> {

        int position;
        public LinkedListDequeIterator() {
            position = 0;
        }

        @Override
        public boolean hasNext() {
            return position < size;
        }

        @Override
        public T next() {
            T item = get(position);
            position++;
            return item;
        }
    }

}
