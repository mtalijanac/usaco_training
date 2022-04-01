/*
ID: marko.t2
LANG: JAVA
TASK: beads
*/

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Chapter 1
 * Problem: Broken Necklace
 *
 * From each position count to the left and right and sum the results. The longest sum wins.
 */
public class beads {
    String inputfile = "beads.in";
    String outputfile = "beads.out";
    final int R = 'r', B = 'b', W = 'w';

    void run() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(inputfile));
        int N = Integer.parseInt(br.readLine());
        String beads = br.readLine();
        br.close();

        int result = count(beads);

        BufferedWriter bw = new BufferedWriter(new FileWriter(outputfile));
        bw.write(result + "\n");
        bw.close();
    }

    int count(String beads) {
        int maxLen = -1;

        for (int idx = 0; idx < beads.length(); idx++) {
            int rightLen = countToRight(idx, beads);
            int endIdx = idx + rightLen - 1;
            if (endIdx >= beads.length()) endIdx -= beads.length();

            if (rightLen == beads.length()) {
                maxLen = rightLen;
                break;
            }

            int lefIdx = idx - 1;
            if (lefIdx < 0) lefIdx = beads.length() - 1;
            int leftLen = countToLeft(lefIdx, endIdx, beads);

            int len = rightLen + leftLen;
            if (len > maxLen) maxLen = len;
        }
        return maxLen;
    }

    int countToRight(final int startIdx, String beads) {
        int startColor = beads.charAt(startIdx);
        int sum = 1;

        for (int idx = startIdx + 1; idx != startIdx; idx += 1) {
            if (idx >= beads.length()) {
                idx = 0;
            }

            if (startIdx == idx) {
                break;
            }

            int color = beads.charAt(idx);
            if (startColor == W && color != W) {
                startColor = color;
            }

            if (color == W || color == startColor) {
                sum += 1;
                continue;
            }

            break;
        }

        return sum;
    }

    int countToLeft(final int startIdx, final int endIdx, String beads) {
        int startColor = beads.charAt(startIdx);
        int sum = 1;

        for (int idx = startIdx - 1; idx != endIdx; idx -= 1) {
            if (idx < 0) {
                idx = beads.length() - 1;
            }

            if (endIdx == idx) {
                break;
            }

            int color = beads.charAt(idx);
            if (startColor == W && color != W) {
                startColor = color;
            }

            if (color == W || color == startColor) {
                sum += 1;
                continue;
            }

            break;
        }
        return sum;
    }


    void test() {
        String testInput1 = "wwwbbrwrbrbrrbrbrwrwwrbwrwrrb";
        int res1 = count(testInput1);
        assert 11 == res1 : res1;

        int res3 = count("rwrwrwrwrwrwrwrwrwrwrwrwbwrwbwrwrwrwrwrwrwrwrwrwrwrwrwrwrwrwrwrwrwrwrwrwrwrwr");
        assert 74 == res3 : res3;

        int res2 = count("rrr");
        assert 3 == res2 : res2;

        int res4 = count("wwwwwwwwwwwwwwwww");
        assert 17 == res4 : res4;

        int res6 = count("rrwwwwbb");
        assert 8 == res6 : res6;

        System.out.println("Tests passed");
    }


    public static void main(String[] args) throws IOException {
        boolean assertionsEnabled = false;

        assert assertionsEnabled = true;
        if (assertionsEnabled) {
            new beads().test();
            return;
        }

        new beads().run();
    }
}
