/*
ID: marko.t2
LANG: JAVA
TASK: crypt1
*/

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Chapter 1, Section 1.4
 * Problem: Prime Cryptarithm
 */
public class crypt1 {
    String inputfile = "crypt1.in";
    String outputfile = "crypt1.out";

    void run() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(inputfile));
        int digitCount = Integer.parseInt(reader.readLine());
        String[] line = reader.readLine().split(" ");
        int[] digits = new int[digitCount];
        for (int idx = 0; idx < digits.length; idx++)
            digits[idx] = Integer.parseInt(line[idx]);
        reader.close();

        String result = solve(digits);

        BufferedWriter bw = new BufferedWriter(new FileWriter(outputfile));
        bw.write(result);
        bw.close();
    }


    String solve(int[] digits) {
        int foundSolutions = 0;

        for (int a: digits) for (int b: digits) for (int c: digits) for (int d: digits) for (int e: digits) {
            int abc = 100 * a + 10 * b + c;
            int  de = 10 * d + e;

            int result = abc * de;
            if (result > 9999) continue;
            if (!digitTest(result, digits)) continue;

            int p1 = e * abc;
            if (p1 > 999) continue;
            if (!digitTest(p1, digits)) continue;

            int p2 = d * abc;
            if (p2 > 999) continue;
            if (!digitTest(p2, digits)) continue;

            foundSolutions++;
        }

        return foundSolutions + "\n";
    }


    boolean digitTest(int number, int[] allowedDigits) {
        while (number > 0) {
            int remainder = number % 10;
            number = number / 10;

            boolean isAllowed = false;
            for (int d: allowedDigits)
                if (d == remainder) isAllowed = true;

            if (!isAllowed) return false;
        }
        return true;
    }


    void test() throws IOException {
        example: {
            String out = solve(new int[] {2, 3, 4, 6, 8});
            assert "1\n".equals(out) : out;
        }

        System.out.println("All tests passed");
    }


    public static void main(String[] args) throws IOException {
        boolean assertionsEnabled = false;

        assert assertionsEnabled = true;
        if (assertionsEnabled) {
            new crypt1().test();
            return;
        }

        new crypt1().run();
    }
}
