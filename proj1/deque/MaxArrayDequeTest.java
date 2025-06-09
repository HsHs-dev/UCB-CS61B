package deque;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.Comparator;

public class MaxArrayDequeTest {

    private static class Numbers implements Comparator<Integer> {

        @Override
        public int compare(Integer o1, Integer o2) {
            return o1 - o2;
        }
    }

    private static class Words implements Comparator<String> {
        @Override
        public int compare(String a, String b) {
            return a.compareTo(b);
        }
    }

   @Test
   public void numbers() {
        Comparator<Integer> cmp = new Numbers();
        MaxArrayDeque<Integer> list = new MaxArrayDeque<>(cmp);
        list.addFirst(20);
        list.addFirst(30);
        list.addFirst(5);
        int max = list.max();
        assertEquals(30, max);
   }

   @Test
    public void names() {
        MaxArrayDeque<String> list = new MaxArrayDeque<>();
        list.addFirst("Hassan");
       list.addFirst("Siddig");
       list.addFirst("Mahmoud");

       String max = list.max(new Words());
       assertEquals("Siddig", max);

   }


}
