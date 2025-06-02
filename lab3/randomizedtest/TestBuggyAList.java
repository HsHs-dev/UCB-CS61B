package randomizedtest;

import edu.princeton.cs.algs4.StdRandom;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by hug.
 */
public class TestBuggyAList {

    @Test
    public void testThreeAddThreeRemove() {
        AListNoResizing<Integer> aList = new AListNoResizing<>();
        BuggyAList<Integer> bAList = new BuggyAList<>();

        aList.addLast(5);
        aList.addLast(10);
        aList.addLast(15);

        bAList.addLast(5);
        bAList.addLast(10);
        bAList.addLast(15);

        assertEquals(bAList.size(), aList.size());

        assertEquals(aList.removeLast(), bAList.removeLast());
        assertEquals(aList.removeLast(), bAList.removeLast());
        assertEquals(aList.removeLast(), bAList.removeLast());
    }

    @Test
    public void randomizedTest() {

        AListNoResizing<Integer> L = new AListNoResizing<>();
        BuggyAList<Integer> B = new BuggyAList<>();

        int N = 5000;
        for (int i = 0; i < N; i += 1) {
            int operationNumber = StdRandom.uniform(0, 4);
            if (operationNumber == 0) {
                // addLast
                int randVal = StdRandom.uniform(0, 100);
                L.addLast(randVal);
                B.addLast(randVal);
            } else if (operationNumber == 1) {
                // size
                int lSize = L.size();
                int bSize = B.size();
            } else if (operationNumber == 2) {
                if (L.size() == 0 || B.size() == 0) continue;
                int lLast = L.getLast();
                int bLast = B.getLast();
                assertEquals(lLast, bLast);
            } else if (operationNumber == 3) {
                if (L.size() == 0 || B.size() == 0) continue;
                int lRemove = L.removeLast();
                int bRemove = B.removeLast();
                assertEquals(lRemove, bRemove);
            }
        }

    }


}
