package timingtest;
import edu.princeton.cs.algs4.Stopwatch;

/**
 * Created by hug.
 */
public class TimeAList {
    private static void printTimingTable(AList<Integer> Ns, AList<Double> times, AList<Integer> opCounts) {
        System.out.printf("%12s %12s %12s %12s\n", "N", "time (s)", "# ops", "microsec/op");
        System.out.printf("------------------------------------------------------------\n");
        for (int i = 0; i < Ns.size(); i += 1) {
            int N = Ns.get(i);
            double time = times.get(i);
            int opCount = opCounts.get(i);
            double timePerOp = time / opCount * 1e6;
            System.out.printf("%12d %12.2f %12d %12.2f\n", N, time, opCount, timePerOp);
        }
    }

    public static void main(String[] args) {
        timeAListConstruction();
    }

    public static void timeAListConstruction() {
        // list holding the tested sizes
        AList<Integer> sizes = new AList<>();
        // initial size
        int base = 1000;
        // number of sizes in the list
        int len = 15;
        initializeSizes(sizes, base, len);

        AList<Double> times = new AList<>();
        // testing various lists sizes
        for (int i = 0; i < sizes.size(); i++) {
            Stopwatch sw = new Stopwatch();
            AList<Integer> list = new AList<>();
            int counter = 0;
            while (counter < sizes.get(i)) {
                list.addLast(7);
                counter++;
            }
            times.addLast(sw.elapsedTime());
        }

        printTimingTable(sizes, times, sizes);

    }

    /**
     *
     * @param sizes list of tested sizes
     * @param base initial value of the tested sizes
     * @param len the length of the array
     */
    private static void initializeSizes(AList<Integer> sizes, int base, int len) {
        int initVal = base;
        for (int i = 0; i < len; i++) {
            sizes.addLast(initVal);
            initVal += initVal;
        }
    }
}
