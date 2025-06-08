package deque;

import org.junit.Test;
import static org.junit.Assert.*;

public class ArrayDequeTest {

    /*
     * Testing strategy for each operation of ArrayDeque:
     *
     * addFirst, addLast:
     *   partition on deque size: 0, > 0
     *
     * removeFirst, removeLast:
     *   partition on deque size: 0, 1, > 1
     *
     * size, isEmpty:
     *   partition on deque size: 0, > 0
     *
     * get:
     *   partition on deque size: 0, 1, > 1
     */


    // addFirst, isEmpty, size, get: 0, 1
    @Test
    public void addFirstNoElements() {
        ArrayDeque<Integer> list = new ArrayDeque<>();
        assertTrue("check deque is empty", list.isEmpty());
        assertEquals(0, list.size());
        assertNull(list.get(0));
        assertNull(list.get(-1));
        Integer ele = 5;
        list.addFirst(ele);
        assertFalse("check deque is empty", list.isEmpty());
        assertEquals(1, list.size());
        assertEquals(ele, list.get(0));
    }

    // addFirst, size, get: > 1
    @Test
    public void addFirstMultipleElements() {
        ArrayDeque<String> list = new ArrayDeque<>();
        list.addFirst("Hassan");
        list.addFirst("Siddig");
        assertEquals(2, list.size());
        assertEquals("Siddig", list.get(0));
        assertEquals("Hassan", list.get(1));
    }

    // addLast: 0
    @Test
    public void addLastNoElements() {
        ArrayDeque<Double> list = new ArrayDeque<>();
        Double ele = 5.5;
        list.addLast(ele);
        assertEquals(1, list.size());
        assertEquals(ele, list.get(0));
    }

    // addLast: > 0
    @Test
    public void addLastMultipleElements() {
        ArrayDeque<Character> list = new ArrayDeque<>();
        list.addLast('A');
        list.addLast('B');
        assertEquals(2, list.size());
        assertEquals((Character)'A', list.get(0));
        assertEquals((Character)'B', list.get(1));
    }

    // removeFirst, removeLast: 0
    @Test
    public void removeEmpty() {
        ArrayDeque<Integer> list = new ArrayDeque<>();
        assertNull(list.removeFirst());
        assertNull(list.removeLast());
    }

    // removeFirst: 1
    @Test
    public void removeFirstOneElement() {
        ArrayDeque<Integer> list = new ArrayDeque<>();
        list.addLast(44);
        assertEquals((Integer)44, list.removeFirst());
        assertEquals(0, list.size());
    }

    // removeFirst: > 1
    @Test
    public void removeFirstMultipleElements() {
        ArrayDeque<Integer> list = new ArrayDeque<>();
        list.addFirst(44);
        list.addFirst(4);
        list.addFirst(45);
        assertEquals((Integer)45, list.removeFirst());
        assertEquals(2, list.size());
        assertEquals((Integer)4, list.removeFirst());
        assertEquals(1, list.size());
        assertEquals((Integer)44, list.removeFirst());
        assertEquals(0, list.size());
    }

    // removeLast: 1
    @Test
    public void removeLastOneElement() {
        ArrayDeque<Integer> list = new ArrayDeque<>();
        list.addFirst(44);
        assertEquals((Integer)44, list.removeLast());
        assertEquals(0, list.size());
    }

    // removeLast: > 1
    @Test
    public void removeLastMultipleElements() {
        ArrayDeque<Integer> list = new ArrayDeque<>();
        list.addLast(10);
        list.addLast(20);
        list.addLast(30);
        assertEquals((Integer)30, list.removeLast());
        assertEquals(2, list.size());
        assertEquals((Integer)20, list.removeLast());
        assertEquals(1, list.size());
        assertEquals((Integer)10, list.removeLast());
        assertEquals(0, list.size());
    }

    // @source chatGPT
    @Test
    public void randomizedTest() {
        ArrayDeque<Integer> studentDeque = new ArrayDeque<>();
        java.util.ArrayDeque<Integer> javaDeque = new java.util.ArrayDeque<>();

        java.util.Random rand = new java.util.Random(2025);
        int N = 10000;

        for (int i = 0; i < N; i++) {
            int operation = rand.nextInt(6);

            switch (operation) {
                case 0 -> {
                    // addFirst
                    int val = rand.nextInt(100);
                    studentDeque.addFirst(val);
                    javaDeque.addFirst(val);
                }
                case 1 -> {
                    // addLast
                    int val = rand.nextInt(100);
                    studentDeque.addLast(val);
                    javaDeque.addLast(val);
                }
                case 2 -> {
                    // removeFirst
                    if (studentDeque.isEmpty() || javaDeque.isEmpty()) continue;
                    Integer expected = javaDeque.pollFirst();
                    Integer actual = studentDeque.removeFirst();
                    assertEquals("removeFirst failed at iteration " + i, expected, actual);
                }
                case 3 -> {
                    // removeLast
                    if (studentDeque.isEmpty() || javaDeque.isEmpty()) continue;
                    Integer expected = javaDeque.pollLast();
                    Integer actual = studentDeque.removeLast();
                    assertEquals("removeLast failed at iteration " + i, expected, actual);
                }
                case 4 -> {
                    // get
                    if (studentDeque.isEmpty() || javaDeque.isEmpty()) continue;
                    int index = rand.nextInt(studentDeque.size());
                    Integer expected = (Integer) javaDeque.toArray()[index];
                    Integer actual = studentDeque.get(index);
                    assertEquals("get failed at iteration " + i + " index " + index, expected, actual);
                }
                case 5 -> {
                    // size
                    assertEquals("size mismatch at iteration " + i, javaDeque.size(), studentDeque.size());
                }
            }
        }
    }

}
