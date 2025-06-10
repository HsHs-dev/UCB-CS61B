import java.util.Iterator;

public class ArraySet<T> {
    
    private T[] set;
    private int size;

    public ArraySet() {
        set = (T[]) new Object[100];
        size = 0;
    }

    public void add(T value) {
        if (value == null) {
            return;
        }
        if (!contains(value)) {
            set[size] = value;
            size++;
        }
    }

    public boolean contains(T value) {
        for (int i = 0; i < size; i++) {
            if (value.equals(set[i])) {
                return true;
            }
        }
        return false;
    }

    public int size() {
        return size;
    }

    public void print() {
        for (int i = 0; i < size; i++) {
            System.out.print(set[i] + " ");
        }
        System.out.println();
    }

    public Iterator<T> iterator() {
        return new ArraySetIterator();
    }

    private class ArraySetIterator implements Iterator<T> {

        private int positon;
        public ArraySetIterator() {
            positon = 0;
        }

        @Override
        public boolean hasNext() {
            return positon < size;
        }

        @Override
        public T next() {
            positon++;
            return set[positon];
        }
    } 

}