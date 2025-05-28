package IntList;

import static org.junit.Assert.*;
import org.junit.Test;

public class SquarePrimesTest {

    /**
     * Here is a test for isPrime method. Try running it.
     * It passes, but the starter code implementation of isPrime
     * is broken. Write your own JUnit Test to try to uncover the bug!
     */
    @Test
    public void testSquarePrimesSimple() {
        IntList lst = IntList.of(14, 15, 16, 17, 18);
        boolean changed = IntListExercises.squarePrimes(lst);
        assertEquals("14 -> 15 -> 16 -> 289 -> 18", lst.toString());
        assertTrue(changed);
    }

    @Test
    public void testSquarePrimesNoPrimes() {
        IntList lst = IntList.of(4, 6, 8, 10, 12);
        boolean changed = IntListExercises.squarePrimes(lst);
        assertEquals(lst.toString(), lst.toString());
        assertFalse(changed);
    }

    @Test
    public void testSquarePrimesTwoPrims() {
        IntList lst = IntList.of(14, 11, 16, 17, 18);
        boolean changed = IntListExercises.squarePrimes(lst);
        assertEquals("14 -> 121 -> 16 -> 289 -> 18", lst.toString());
        assertTrue(changed);
    }

}
