package deque;

public class ArrayDeque<T> {

    T[] list;
    int firstIndex;
    int lastIndex;
    int size;


    public ArrayDeque() {
        list = (T[]) new Object[8];
        firstIndex = (list.length / 2);
        lastIndex = (list.length / 2) - 1;
        size = 0;
    }


    /**
     * Adds an item to the front of the deque
     * @param item to be added to the deque
     */
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
    public void addLast(T item) {
        if (lastIndex == list.length - 1) {
            resize(list.length * 2);
        }

        lastIndex++;
        list[lastIndex] = item;
        size++;

    }

    /**
     * @return true if the deque is empty
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * @return the number of items in the deque
     */
    public int size() {
        return size;
    }

    /**
     * Prints the items in the deque from first to last, separated by a space.
     * Print a new line after all items are printed
     */
    public void printDeque() {
        for (int i = 0; i < size; i++) {
            System.out.print(list[i] + " ");
        }
        System.out.println();
    }

    /**
     * Removes the first item from the deque
     * @return the first item from the deque
     */
    public T removeFirst() {
        if (isEmpty()) {
            return null;
        }
        if (checkUsage()) shrink();
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
    public T removeLast() {
        if (isEmpty()) {
            return null;
        }
        if (checkUsage()) shrink();
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
    public T get(int index) {
        return list[index];
    }

    /**
     * resize the deque by a given size
     * @param factor new deque length
     */
    private void resize(int factor) {
        T[] a = (T[]) new Object[factor];
        System.arraycopy(list, firstIndex, a, a.length / 4, size);
        list = a;
        firstIndex = getFirstIndex();
        lastIndex = getLastIndex();
    }

    /**
     * @return the index of the first element
     */
    private int getFirstIndex() {
        int first = 0;
        while (list[first] == null) {
            first++;
        }
        return first;
    }

    /**
     * @return the index of the last element
     */
    private int getLastIndex() {
        int last = list.length - 1;
        while (list[last] == null) {
            last--;
        }
        return last;
    }

    /**
     * if the deque size is less than 25% of the array length, shrink the array
     * @return true if the deque size is less than 25% of the array size
     */
    private boolean checkUsage() {
        return size < (list.length / 4) && (size > 8);
    }

    /**
     * Shrink the array to 25% of its original length
     */
    private void shrink() {
        T[] a = (T[]) new Object[list.length / 4];
        System.arraycopy(list, firstIndex, a, 0, size);
        list = a;
        firstIndex = getFirstIndex();
        lastIndex = getLastIndex();
    }

}
