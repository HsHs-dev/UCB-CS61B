
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class TestSelectionSort {


    @Test
    public void testSelectionSort() {
        String[] input = {"cows", "dwell", "above", "clouds"};
        String[] expected = {"above", "clouds", "cows", "dwell"};
        String[] actual = SelectionSort.selectionSort(input);
 
        assertArrayEquals(expected, actual);

    }


    
}
