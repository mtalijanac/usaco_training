/*
ID: marko.t2
LANG: JAVA
TASK: palsquare
*/

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Chapter 1, Section 1.3
 * Problem: Palindromic Squares
 */
public class palsquare {
    String inputfile = "palsquare.in";
    String outputfile = "palsquare.out";

    void run() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(inputfile));
        Integer base = Integer.parseInt(reader.readLine());
        reader.close();

        String result = solve(base);

        BufferedWriter bw = new BufferedWriter(new FileWriter(outputfile));
        bw.write(result);
        bw.close();
    }


    String solve(Integer base) {
        StringBuilder res = new StringBuilder();

        for (long n = 1; n <= 300; n++) {
            long sq = n * n;
            String valInB = Long.toString(sq, base);
            boolean isPalindrome = isPalindrome(valInB);
            if (isPalindrome) {
                res.append(Long.toString(n, base).toUpperCase())
                   .append(" ")
                   .append(valInB.toUpperCase())
                   .append("\n");
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
            String res = "1 1\n"
                       + "2 4\n"
                       + "3 9\n"
                       + "11 121\n"
                       + "22 484\n"
                       + "26 676\n"
                       + "101 10201\n"
                       + "111 12321\n"
                       + "121 14641\n"
                       + "202 40804\n"
                       + "212 44944\n"
                       + "264 69696\n";

            String out = solve(10);
            assert res.equals(out) : out;
        }

        System.out.println("All tests passed");
    }


    public static void main(String[] args) throws IOException {
        boolean assertionsEnabled = false;

        assert assertionsEnabled = true;
        if (assertionsEnabled) {
            new palsquare().test();
            return;
        }

        new palsquare().run();
    }
}
