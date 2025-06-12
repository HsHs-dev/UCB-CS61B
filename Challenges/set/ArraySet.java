import java.lang.reflect.Array;
import java.util.Iterator;

public class ArraySet<T> implements Iterable<T> {
    
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o instanceof ArraySet oas) {
            if (this.size != oas.size) {
                return false;
            }

            for (T x : this) {
                if (!oas.contains(x)) {
                    return false;
                }
            }
        }

        return true;
    }

    @Override
    public String toString() {
        ArraySet<String> list = new ArraySet<>();
        for (T item: this) {
            list.add(item.toString());
        }
        return "{" + String.join(", ", list) + "}";
    }
//    @Override
//    public String toString() {
//        StringBuilder sb = new StringBuilder("{");
//        for (int i = 0; i < size - 1; i++) {
//            sb.append(set[i]);
//            sb.append(", ");
//        }
//        sb.append(set[size - 1]);
//        sb.append("}");
//        return sb.toString();
//    }

    public static <T> ArraySet<T> of(T... elements) {
        ArraySet<T> list = new ArraySet<>();
        for (T element : elements) {
            list.add(element);
        }
        return list;
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
            T item = set[positon];
            positon++;
            return item;
        }
    } 

}