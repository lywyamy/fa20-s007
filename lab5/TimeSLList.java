import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Class that collects timing information about SLList getLast method.
 */
public class TimeSLList {
    private static void printTimingTable(List<Integer> Ns, List<Double> times, List<Integer> opCounts) {
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
        SLList<Integer> sList = new SLList<>();
        List<Integer> Ns = Arrays.asList(1000, 2000, 4000, 8000, 16000, 32000, 64000);
        List<Double> times = new ArrayList<>();
        List<Integer> opCounts = Arrays.asList(10000, 10000, 10000, 10000, 10000, 10000, 10000);

        for (int i = 0; i < Ns.size(); i++) {
            for (int j = 0; j < Ns.get(i); j++) {
                sList.addLast(2);
            }
            Stopwatch sw = new Stopwatch();
            for (int k = 0; k < 10000; k++) {
                sList.getLast();
            }
            double timeInSeconds = sw.elapsedTime();
            times.add(timeInSeconds);
        }
        printTimingTable(Ns, times, opCounts);
    }

}
