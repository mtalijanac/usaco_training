/*
ID: marko.t2
LANG: JAVA
TASK: dualpal
*/

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Chapter 1, Section 1.3
 * Problem: Dual Palindromes
 */
public class dualpal {
    String inputfile = "dualpal.in";
    String outputfile = "dualpal.out";

    void run() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(inputfile));
        String[] line = reader.readLine().split(" ");
        Integer N = Integer.parseInt(line[0]);
        Integer S = Integer.parseInt(line[1]);
        reader.close();

        String result = solve(N, S);

        BufferedWriter bw = new BufferedWriter(new FileWriter(outputfile));
        bw.write(result);
        bw.close();
    }


    String solve(Integer N, Integer S) {
        StringBuilder res = new StringBuilder();

        int foundCount = 0;
        for (long num = S + 1; foundCount < N; num++) {
            int palCount = 0;
            for (int base = 2; base <= 10; base++) {
                String numInB = Long.toString(num, base);
                if (isPalindrome(numInB)) palCount++;
                if (palCount == 2) break;
            }

            if (palCount >= 2) {
                res.append(num).append("\n");
                foundCount += 1;
            }
        }

        return res.toString();
    }

    boolean isPalindrome(String input) {
        for (int idx = 0; idx < input.length() / 2; idx++) {
            char h = input.charAt(idx);
            char t = input.charAt(input.length() - 1 - idx);
            if (h != t) return false;
        }
        return true;
    }


    void test() throws IOException {
        example: {
            String out = solve(3, 25);
            assert "26\n27\n28\n".equals(out) : out;
        }

        System.out.println("All tests passed");
    }


    public static void main(String[] args) throws IOException {
        boolean assertionsEnabled = false;

        assert assertionsEnabled = true;
        if (assertionsEnabled) {
            new dualpal().test();
            return;
        }

        new dualpal().run();
    }
}
