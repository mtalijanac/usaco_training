/*
ID: marko.t2
LANG: JAVA
TASK: combo
*/

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;

/**
 * Chapter 1, Section 1.4
 * Problem: Combination Lock
 */
public class combo {
    String inputfile = "combo.in";
    String outputfile = "combo.out";

    void run() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(inputfile));
        int N = Integer.parseInt(reader.readLine());
        int[] farmerJohnCombination = extractDigits(reader.readLine());
        int[] masterCombination = extractDigits(reader.readLine());
        reader.close();

        String result = solve(N, farmerJohnCombination, masterCombination);

        BufferedWriter bw = new BufferedWriter(new FileWriter(outputfile));
        bw.write(result);
        bw.close();
    }

    int[] extractDigits(String line) {
        String[] vals = line.split(" ");
        int[] digits = new int[3];
        digits[0] = Integer.parseInt(vals[0]);
        digits[1] = Integer.parseInt(vals[1]);
        digits[2] = Integer.parseInt(vals[2]);
        return digits;
    }


    String solve(int N, int[] combo1, int[] combo2) {
        HashSet<String> results = new HashSet<String>();
        rotateLock(combo1, results, N);
        rotateLock(combo2, results, N);
        return results.size() + "\n";
    }

    void rotateLock(int[] combo, HashSet<String> results, int N) {
        int[] startPosition = {
                val(combo[0], -2, N),
                val(combo[1], -2, N),
                val(combo[2], -2, N)
        };

        for (int a = 0; a < 5; a++) for (int b = 0; b < 5; b++) for (int c = 0; c < 5; c++) {
            int d1 = val(startPosition[0], a, N);
            int d2 = val(startPosition[1], b, N);
            int d3 = val(startPosition[2], c, N);
            String num = d1 + "," + d2 + "," + d3;
            results.add(num);
        }
    }

    int val(int v, int off, int n) {
        int add = off > 0 ? 1 : -1;
        for (int i = 0; i < off; i++) {
            v += add;
            if (v < 1) v = n;
            if (v > n) v = 1;
        }
        return v;
    }


    void test() throws IOException {
        example: {
            String out = solve(50, new int[] {1, 2, 3}, new int[] {5, 6, 7});
            assert "249\n".equals(out) : out;
        }

        run_2: {
            String out = solve(1, new int[] {1, 1, 1}, new int[] {1, 1, 1});
            assert "1\n".equals(out) : out;
        }

        System.out.println("All tests passed");
    }


    public static void main(String[] args) throws IOException {
        boolean assertionsEnabled = false;

        assert assertionsEnabled = true;
        if (assertionsEnabled) {
            new combo().test();
            return;
        }

        new combo().run();
    }
}
