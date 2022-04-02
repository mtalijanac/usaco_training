/*
ID: marko.t2
LANG: JAVA
TASK: transform
*/

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

/**
 * Chapter 1, Section 1.3
 * Problem: Transformations
 */
public class transform {
    String inputfile = "transform.in";
    String outputfile = "transform.out";

    void run() throws IOException {
        int[][][] beforeAndAfter = load(inputfile);

        String res = solve(beforeAndAfter[0], beforeAndAfter[1]);

        BufferedWriter bw = new BufferedWriter(new FileWriter(outputfile));
        bw.write(res);
        bw.close();
    }

    int[][][] load(String path) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(path));
        int n = Integer.parseInt(reader.readLine());
        int[][] before = loadMatrix(n, reader);
        int[][] after  = loadMatrix(n, reader);
        reader.close();
        return new int[][][] {before, after};
    }

    int[][] loadMatrix(int n, BufferedReader reader) throws IOException {
        int[][] matrix = new int[n][n];
        for (int i = 0; i < n; i++) {
            String line = reader.readLine();
            for (int j = 0; j < n; j++) {
                matrix[i][j] = '@' == line.charAt(j) ? 1 : 0;
            }
        }
        return matrix;
    }

    String solve(int[][] before, int[][] after) {
        int[][] b90 = rotateCW(before);
        if (Arrays.deepEquals(after, b90)) return "1\n";

        int[][] b180 = rotateCW(b90);
        if (Arrays.deepEquals(after, b180)) return "2\n";

        int[][] b270 = rotateCW(b180);
        if (Arrays.deepEquals(after, b270)) return "3\n";

        int[][] refl = horizontalReflection(before);
        if (Arrays.deepEquals(after, refl)) return "4\n";

        int[][] refl90 = rotateCW(refl);
        if (Arrays.deepEquals(after, refl90)) return "5\n";

        int[][] refl180 = rotateCW(refl90);
        if (Arrays.deepEquals(after, refl180)) return "5\n";

        int[][] refl270 = rotateCW(refl180);
        if (Arrays.deepEquals(after, refl270)) return "5\n";

        if (Arrays.deepEquals(before, after)) return "6\n";

        return "7\n";
    }

    int[][] rotateCW(int[][] in) {
        int n = in.length;
        int[][] out = new int[n][n];

        for (int ridx = 0; ridx < n; ridx++)
            for (int cidx = 0; cidx < n; cidx++) {
                int val = in[ridx][cidx];
                out[cidx][n - ridx - 1] = val;
            }
        return out;
    }

    int[][] horizontalReflection(int[][] in) {
        int n = in.length;
        int[][] out = new int[n][n];

        for (int ridx = 0; ridx < n; ridx++)
            for (int cidx = 0; cidx < n / 2; cidx++) {
                int a = in[ridx][cidx];
                int c = in[ridx][n - cidx - 1];

                out[ridx][cidx] = c;
                out[ridx][n - cidx - 1] = a;
            }

        if (n % 2 == 1) {
            int cidx = n / 2;
            for (int r = 0; r < n; r++) {
                out[r][cidx] = in[r][cidx];
            }
        }

        return out;
    }

    void test() throws IOException {
        example: {
            int[][] before = {{1,0,1},
                              {0,0,0},
                              {1,1,0}};

            int[][] after =  {{1,0,1},
                              {1,0,0},
                              {0,0,1}};

            String out = solve(before, after);
            assert "1\n".equals(out) : out;
        }


        test_1: {
            int[][] before = {{0,0,0},
                              {0,0,0},
                              {0,0,0}};

            int[][] after =  {{0,0,0},
                              {0,1,0},
                              {0,0,0}};

            String out = solve(before, after);
            assert "7\n".equals(out) : out;
        }

        test_2: {
            int[][] before = {{0,1,1,1,0},
                              {0,1,1,0,0},
                              {0,1,0,0,0},
                              {0,0,0,0,0},
                              {0,0,0,0,0}};

            int[][] after = {{0,0,0,0,0},
                             {0,0,0,0,1},
                             {0,0,0,1,1},
                             {0,0,1,1,1},
                             {0,0,0,0,0}};

            String out = solve(before, after);
            assert "5\n".equals(out) : out;
        }


        System.out.println("All tests passed");
    }


    public static void main(String[] args) throws IOException {
        boolean assertionsEnabled = false;

        assert assertionsEnabled = true;
        if (assertionsEnabled) {
            new transform().test();
            return;
        }

        new transform().run();
    }
}
