package deque;

import java.util.Iterator;

public class ArrayDeque<T> implements Deque<T>, Iterable<T> {

    private T[] list;
    private int firstIndex;
    private int lastIndex;
    private int size;


    public ArrayDeque() {
        list = (T[]) new Object[8];
        firstIndex = (list.length / 2);
        lastIndex = (list.length / 2) - 1;
        size = 0;
    }

//    public ArrayDeque(int capacity) {
//        list = (T[]) new Object[capacity];
//        firstIndex = (list.length / 2);
//        lastIndex = (list.length / 2) - 1;
//        size = 0;
//    }


    /**
     * Adds an item to the front of the deque
     * @param item to be added to the deque
     */
    @Override
    public void addFirst(T item) {
        if (firstIndex == 0) {
            resize(list.length * 2);
        }

        firstIndex--;
        list[firstIndex] = item;
        size++;

    }

    /**
     * Adds an item to the end of the deque
     * @param item to be added to the deque
     */
    @Override
    public void addLast(T item) {
        if (lastIndex == list.length - 1) {
            resize(list.length * 2);
        }

        lastIndex++;
        list[lastIndex] = item;
        size++;

    }

    /**
     * @return the number of items in the deque
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Prints the items in the deque from first to last, separated by a space.
     * Print a new line after all items are printed
     */
    @Override
    public void printDeque() {
        for (int i = 0; i < size; i++) {
            System.out.print(get(i) + " ");
        }
        System.out.println();
    }

    /**
     * Removes the first item from the deque
     * @return the first item from the deque
     */
    @Override
    public T removeFirst() {
        if (isEmpty()) {
            return null;
        }
        if (checkUsage()) {
            shrink();
        }
        T first = list[firstIndex];
        list[firstIndex] = null;
        firstIndex++;
        size--;
        return first;
    }


    /**
     * Removes the last item from the deque
     * @return the last item from the deque
     */
    @Override
    public T removeLast() {
        if (isEmpty()) {
            return null;
        }
        if (checkUsage()) {
            shrink();
        }
        T last = list[lastIndex];
        list[lastIndex] = null;
        lastIndex--;
        size--;
        return last;
    }

    /**
     * @param index of the item to be returned
     * @return item at the index index
     */
    @Override
    public T get(int index) {
        if (index < 0 || index >= size) {
            return null;
        }
        return list[index + firstIndex];
    }

    /**
     * @return an iterator object of this deque
     */
    public Iterator<T> iterator() {
        return new ArrayDequeIterator();
    }

    private class ArrayDequeIterator implements Iterator<T> {

        int position;
        ArrayDequeIterator() {
            position = 0;
        }

        @Override
        public boolean hasNext() {
            return position < size;
        }

        @Override
        public T next() {
            T item = list[position + firstIndex];
            position++;
            return item;
        }
    }

    /**
     * resize the deque by a given size
     * @param factor new deque length
     */
    private void resize(int factor) {
        T[] a = (T[]) new Object[factor];
        int newStart = factor / 4;
        System.arraycopy(list, firstIndex, a, newStart, size);
        list = a;
        firstIndex = newStart;
        lastIndex = firstIndex + size - 1;
    }

    /**
     * if the deque size is less than 25% of the array length, shrink the array
     * @return true if the deque size is less than 25% of the array size
     */
    private boolean checkUsage() {
        return list.length > 8 && size < (list.length / 4);
    }

    /**
     * Shrink the array to 25% of its original length
     */
    private void shrink() {
        T[] a = (T[]) new Object[list.length / 4];
        System.arraycopy(list, firstIndex, a, 0, size);
        list = a;
        firstIndex = 0;
        lastIndex = size - 1;
    }


    /**
     * @param o object to be compared to
     * @return true if parameter o is equal to the Deque
     */
    public boolean equals(Object o) {

        if (!(o instanceof Deque)) {
            return false;
        }

        if (this == o) {
            return true;
        }



        if (this.size() != ((Deque<?>) o).size()) {
            return false;
        }

        for (int i = 0; i < size; i++) {
            if (!java.util.Objects.equals(get(i), ((Deque<?>) o).get(i))) {
                return false;
            }
        }

        return true;
    }

}
