
public class SelectionSort { 

    public static String[] selectionSort(String[] words) {

        String[] sorted = words.clone();

        for (int i = 0; i < sorted.length - 1; i++) {
            int smallestIndex = i;
            for (int j = i + 1; j < sorted.length; j++) {
                if (sorted[j].compareTo(sorted[smallestIndex]) < 0) {
                    smallestIndex = j;
                }
            }
            if (smallestIndex != i) {
                String temp = sorted[i];
                sorted[i] = sorted[smallestIndex];
                sorted[smallestIndex] = temp;
            }
        }

        return sorted;


    }

}