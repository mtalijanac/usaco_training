/*
ID: marko.t2
LANG: JAVA
TASK: milk
*/

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.concurrent.atomic.AtomicInteger;

import javax.swing.tree.TreeSelectionModel;

/**
 * Chapter 1, Section 1.4
 * Problem: Combination Lock
 */
public class milk {
    String inputfile = "milk.in";
    String outputfile = "milk.out";

    void run() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(inputfile));

        ArrayList<int[]> inputList = new ArrayList<>();
        while (true) {
            String line = reader.readLine();
            if (line == null)
                break;

            String[] vals = line.split(" ");
            int a = Integer.parseInt(vals[0]);
            int b = Integer.parseInt(vals[1]);
            inputList.add(new int[] {a, b});
        }
        reader.close();

        int[] val = inputList.remove(0);
        int N = val[0];
        int M = val[1];
        int[][] farmersRations = inputList.toArray(new int[inputList.size()][2]);

        String result = solve(N, M, farmersRations);

        BufferedWriter bw = new BufferedWriter(new FileWriter(outputfile));
        bw.write(result);
        bw.close();
    }


    Comparator<int[]> comp = (dis, dat) -> dis[0] - dat[0];

    String solve(int N, int M, int[][] farmersRations) {
        Arrays.sort(farmersRations, 0, farmersRations.length, comp);
        int[] prices = new int[farmersRations.length];
        int[] ammounts = new int[farmersRations.length];

        for (int idx = 0; idx < farmersRations.length; idx++) {
            prices[idx] = farmersRations[idx][0];
            ammounts[idx] = farmersRations[idx][1];
        }

        int ammount = 0, price = 0;
        for (int idx = 0; idx < prices.length;) {
            if (ammount == N) {
                break;
            }

            if (ammounts[idx] == 0) {
                idx++;
                continue;
            }


            ammount++;
            ammounts[idx]--;
            price += prices[idx];
        }

        return price + "\n";
    }



    void test() throws IOException {
        sample: {
            int[][] input = {
                    {5, 20},
                    {9, 40},
                    {3, 10},
                    {8, 80},
                    {6, 30}};

            String out = solve(100, 5, input);
            assert "630\n".equals(out) : out;
        }

        System.out.println("All tests passed");
    }


    public static void main(String[] args) throws IOException {
        boolean assertionsEnabled = false;

        assert assertionsEnabled = true;
        if (assertionsEnabled) {
            new milk().test();
            return;
        }

        new milk().run();
    }
}
