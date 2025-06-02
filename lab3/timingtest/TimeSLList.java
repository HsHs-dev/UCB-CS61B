package timingtest;
import edu.princeton.cs.algs4.Stopwatch;

/**
 * Created by hug.
 */
public class TimeSLList {
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
        timeGetLast();
    }

    public static void timeGetLast() {
        // list holding the tested sizes
        AList<Integer> sizes = new AList<>();
        // initial size
        int base = 1000;
        // number of sizes in the list
        int len = 8;
        initializeSizes(sizes, base, len);

        int m = 10000;
        AList<Double> times = new AList<>();
        // testing various lists sizes
        for (int i = 0; i < sizes.size(); i++) {
            SLList<Integer> list = new SLList<>();
            int counter = 0;
            while (counter < sizes.get(i)) {
                list.addLast(7);
                counter++;
            }
            Stopwatch sw = new Stopwatch();
            for (int j = 0; j < m; j++) {
                list.getLast();
            }
            times.addLast(sw.elapsedTime());
        }

        AList<Integer> ops = new AList<>();
        for (int i = 0; i < sizes.size(); i++) {
            ops.addLast(m);
        }

        printTimingTable(sizes, times, ops);
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
