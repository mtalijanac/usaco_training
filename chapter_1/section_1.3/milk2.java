/*
ID: marko.t2
LANG: JAVA
TASK: milk2
*/

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Chapter 1, Section 1.3
 * Problem: Milking Cows
 */
public class milk2 {
    String inputfile = "milk2.in";
    String outputfile = "milk2.out";

    void run() throws IOException {
        int[][] intervals = load(inputfile);

        String res = solve(intervals);

        BufferedWriter bw = new BufferedWriter(new FileWriter(outputfile));
        bw.write(res);
        bw.close();
    }

    int[][] load(String path) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(path));
        int n = Integer.parseInt(reader.readLine());
        int[][] intervals = new int[n][2];

        for (int i = 0; i < n; i++) {
            String[] line = reader.readLine().split(" ");
            int from = Integer.parseInt(line[0]);
            int to = Integer.parseInt(line[1]);
            intervals[i] = new int[] {from, to};
        }

        reader.close();
        return intervals;
    }

    String solve(int[][] inputIntervals) {
        Arrays.sort(inputIntervals, 0, inputIntervals.length, (a, b) -> a[0] - b[0]);
        int[][] cleaned = removeEmbeddedIntervals(inputIntervals);
        int[] result = durationAndIdleTime(cleaned);
        return result[0] + " " + result[1] + "\n";
    }

    int[][] removeEmbeddedIntervals(int[][] intervals) {
        for (int idx = 0; idx < intervals.length - 1; idx++) {
            int[] ival = intervals[idx];
            if (ival == null) continue;

            for (int jdx = idx + 1; jdx < intervals.length; jdx++) {
                int[] jval = intervals[jdx];
                if (jval == null) continue;

                if (jval[0] > ival[1]) continue;

                if (jval[1] > ival[1]) ival[1] = jval[1];
                intervals[jdx] = null;
            }
        }

        ArrayList<int[]> results = new ArrayList<>();
        for (int[] i: intervals) if (i != null) results.add(i);
        return results.toArray(new int[results.size()][]);
    }

    int[] durationAndIdleTime(int[][] intervals) {
        int maxDuration = 0;
        if (intervals.length > 0) maxDuration = intervals[0][1] - intervals[0][0];

        int maxIdle = 0;
        if (intervals.length > 1) maxIdle = intervals[1][0] - intervals[0][1];

        for (int idx = 1; idx < intervals.length; idx++) {
            int[] intA = intervals[idx - 1];
            int[] intB = intervals[idx];

            int duration = intB[1] - intB[0];
            if (duration > maxDuration) maxDuration = duration;

            int idle = intB[0] - intA[1];
            if (idle > maxIdle) maxIdle = idle;
        }

        return new int[] {maxDuration, maxIdle};
    }


    void test() throws IOException {
        int[][] intervals = {{300, 1000}, {700, 1200}, {1500, 2100}};
        String res1 = solve(intervals);
        assert "900 300\n".equals(res1) : res1;

        int[][] run1 = {{100, 200}};
        String sol1 = solve(run1);
        assert "100 0\n".equals(sol1) : sol1;

        int[][] run6 = {{100, 200},
                        {200, 400},
                        {400, 800},
                        {800, 1600},
                        {50, 100},
                        {1700, 3200}};

        String sol6 = solve(run6);
        assert "1550 100\n".equals(sol6) : sol6;


        inputfile = "./chapter_1/section_1.3/milk2_test7.in";
        int[][] test7 = load(inputfile);
        String sol7 = solve(test7);
        assert "912 184\n".equals(sol7) : sol7;

        System.out.println("All tests passed");
    }


    public static void main(String[] args) throws IOException {
        boolean assertionsEnabled = false;

        assert assertionsEnabled = true;
        if (assertionsEnabled) {
            new milk2().test();
            return;
        }

        new milk2().run();
    }
}
