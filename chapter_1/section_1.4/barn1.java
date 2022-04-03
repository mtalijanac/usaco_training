/*
ID: marko.t2
LANG: JAVA
TASK: barn1
*/

import java.io.*;

/**
 * Chapter 1, Section 1.4
 * Problem: Barn Repair
 */
public class barn1 {
    String inputfile = "barn1.in";
    String outputfile = "barn1.out";

    final int cow = 1, empty = 0, ignored = -1;

    void run() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(inputfile));
        String[] msc = reader.readLine().split(" ");
        int M = Integer.parseInt(msc[0]);
        int S = Integer.parseInt(msc[1]);
        int C = Integer.parseInt(msc[2]);

        int[] stalls = new int[S];
        for (int i = 0; i < C; i++) {
            int idx = Integer.parseInt(reader.readLine());
            stalls[idx - 1] = cow;
        }

        reader.close();

        String result = solve(M, S, C, stalls);

        BufferedWriter bw = new BufferedWriter(new FileWriter(outputfile));
        bw.write(result);
        bw.close();
    }


    String solve(int M, int S, int C, int[] stalls) {

        // first board
        for (int idx = 0; idx < stalls.length; idx++) {
            if (stalls[idx] != empty) break;
            stalls[idx] = ignored;
        }

        for (int idx = stalls.length - 1; idx > -1; idx--) {
            if (stalls[idx] != empty) break;
            stalls[idx] = ignored;
        }

        int m = 1;
        while (m != M) {
            m += 1;
            longestEmptyStreak(stalls);
        }

        int count = 0;
        for (int stall: stalls)
            if (stall != ignored) count++;

        return count + "\n";
    }

    int longestEmptyStreak(int[] stalls) {
        // 1. find longest empty streak

        int idx = indexOfValue(empty, stalls, 0);
        int len = lenOfValue(empty, stalls, idx);
        if (len == 0) return 0;

        int maxIdx = idx, maxLen = len;

        while (true) {
            idx = indexOfValue(empty, stalls, idx + len);
            if (idx == -1) break;

            len = lenOfValue(empty, stalls, idx);
            if (len > maxLen) {
                maxLen = len;
                maxIdx = idx;
            }
        }

        // 2. mark longest empty streak occupied
        for (idx = maxIdx; idx < stalls.length; idx++) {
            if (stalls[idx] != empty) break;
            stalls[idx] = ignored;
        }

        return len;
    }

    int indexOfValue(int value, int[] array, int startIndex) {
        if (startIndex >= array.length || startIndex < 0)
            return -1;

        for(int idx = startIndex; idx < array.length; idx++)
            if (array[idx] == value) return idx;

        return -1;
    }

    int lenOfValue(int value, int[] array, int startIdx) {
        int len = 0;
        for (int idx = startIdx; idx >= 0 && idx < array.length; idx++)
            if (array[idx] == value) len++;
            else break;

        return len;
    }


    void test() throws IOException {
        sample: {
            int[] stalls = new int[50];
            for (int i: new int[]{3, 4, 6, 8, 14, 15, 16, 17, 21, 25, 26, 27, 30, 31, 40, 41, 42, 43})
                stalls[i - 1] = cow;

            String out = solve(4, 50, 18, stalls);
            assert "25\n".equals(out) : out;
        }

        run_3: {
            int[] stalls = new int[27];
            for (int i: new int[]{2, 3, 5, 6, 8, 9, 10, 13, 14, 15, 16, 19, 20, 21, 22, 27})
                stalls[i - 1] = cow;

            String out = solve(3, 27, 16, stalls);
            assert "20\n".equals(out) : out;
        }

        run_4: {
            int[] stalls = new int[200];
            for (int i: new int[]{101, 105, 102, 106, 103, 107, 104, 99})
                stalls[i - 1] = cow;

            String out = solve(1, 200, 8, stalls);
            assert "9\n".equals(out) : out;
        }

        run_5: {
            int[] stalls = new int[200];
            for (int i: new int[]{18, 69, 195, 38, 73, 28, 6, 172, 53, 99})
                stalls[i - 1] = cow;

            String out = solve(50, 200, 10, stalls);
            assert "10\n".equals(out) : out;
        }


        System.out.println("All tests passed");
    }


    public static void main(String[] args) throws IOException {
        boolean assertionsEnabled = false;

        assert assertionsEnabled = true;
        if (assertionsEnabled) {
            new barn1().test();
            return;
        }

        new barn1().run();
    }
}
