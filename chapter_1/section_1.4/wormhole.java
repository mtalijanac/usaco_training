/*
ID: marko.t2
LANG: JAVA
TASK: wormhole
*/

import java.io.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Chapter 1, Section 1.4
 * Problem: Wormholes
 */
public class wormhole {
    String inputfile = "wormhole.in";
    String outputfile = "wormhole.out";

    int N = 0;
    int[][] holes = null;
    AtomicInteger permutationCounter = new AtomicInteger(0);
    AtomicInteger cycleCounter = new AtomicInteger(0);
    private int[][] wormholePermutation;

    void run() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(inputfile));
        int N = Integer.parseInt(reader.readLine());

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

        int[][] wormholes = inputList.toArray(new int[inputList.size()][2]);

        String result = solve(N, wormholes);

        BufferedWriter bw = new BufferedWriter(new FileWriter(outputfile));
        bw.write(result);
        bw.close();
    }


    String solve(int N, int[][] wormholes) {
        this.N = N;
        this.holes = wormholes;
        permutationCounter.set(0);
        cycleCounter.set(0);

        Integer[] indexes = new Integer[N];
        for (int idx = 0; idx < N; idx++) indexes[idx] = idx;
        LinkedHashSet<Object> pairedholes = new LinkedHashSet<>();
        permutate_wormholes(indexes, pairedholes);

        int permutations = permutationCounter.get();
        int cycleCount = cycleCounter.get();
        return cycleCount + "\n";
    }


    void permutate_wormholes(Object[] input, LinkedHashSet<Object> output) {
        if (input.length == output.size()) {
            permutationCounter.incrementAndGet();
            wormholePermutation = wormholePermutation(output);
            boolean contains_cycle = contains_cycle(wormholePermutation);
            if (contains_cycle) cycleCounter.incrementAndGet();
            return;
        }

        int idx = 0;
        for (idx = 0; idx < input.length; idx++) {
            if (!output.contains(input[idx])) {
                break;
            }
        }

        output.add(input[idx]);

        for (int jdx = idx + 1; jdx < input.length; jdx++) {
            if (output.contains(input[jdx]))
                continue;

            output.add(input[jdx]);
            permutate_wormholes(input, output);
            output.remove(input[jdx]);
        }
        output.remove(input[idx]);
    }

    /** rearange indexes to actual pairs of wormholes */
    int[][] wormholePermutation(LinkedHashSet<Object> output) {
        int[][] wormhole_pairs = new int[holes.length][];
        Object[] array = output.toArray();
        for (int idx = 0; idx < output.size(); idx++) {
            Integer hindex = (Integer) array[idx];
            wormhole_pairs[idx] = holes[hindex];
        }
        return wormhole_pairs;
    }

    /** return true if current permutation of holes contains a cylce */
    boolean contains_cycle(int[][] wormhole_pairs) {
        boolean[] was_here = new boolean[wormhole_pairs.length];
        for (int idx = 0; idx < wormhole_pairs.length; idx++) {
            Arrays.fill(was_here, false);
            if (walk_holes(wormhole_pairs, idx, was_here)) {
                return true;
            }
        }
        return false;
    }


    boolean walk_holes(int[][] wormhole_pairs, int entrance_idx, boolean[] was_here) {
        // if we already entered this hole it is a cylce and return true
        int[] entrance = wormhole_pairs[entrance_idx];
        if (was_here[entrance_idx])
            return true;

        was_here[entrance_idx] = true;

        // find paired whole and thus exiting placement
        int exit_idx = entrance_idx + 1 - 2 * (entrance_idx % 2);
        int[] exit = wormhole_pairs[exit_idx];
        
        // find next hole
        int next_hole_idx = -1;
        for (int idx = 0; idx < wormhole_pairs.length; idx++) {
            // skip exit hole
            if (idx == exit_idx) continue;      
            
            int[] hole = wormhole_pairs[idx];   

            // entrance hole should be at the same level (y koordinati)
            if (hole[1] != exit[1]) continue;   

            // next entrance hole should be right to the previous best
            if (hole[0] <= exit[0]) continue;
            
            // next entrance hole should be left to the previous best
            if (next_hole_idx != -1 && wormhole_pairs[next_hole_idx][0] < hole[0]) continue;
            
            next_hole_idx = idx;
        }
        
        // if there is a whole to the +x from here enter in that and cyle continues
        // if there is not a whole to the +x from exiting one

        if (next_hole_idx == -1) 
            return false;

        return walk_holes(wormhole_pairs, next_hole_idx, was_here);
    }


    void test() throws IOException {
        sample: {
            int[][] input = {
                    {0, 0},
                    {1, 0},
                    {1, 1},
                    {0, 1}};

            String out = solve(4, input);
            System.out.println(out);
            assert "2\n".equals(out) : out;
        }

        test_7: {
            int[][] input = {
                    {636437309, 704270751},
                    {695056713, 700147825},
                    {636437309, 356396548},
                    {921201220, 589666013},
                    {430411607, 671693685},
                    {324259336, 671693685},
                    {723442153, 589666013},
                    {528904109, 419799944},
                    {921201220, 356396548},
                    {723442153, 856537355},
                    {691516566, 726853849},
                    {941903572, 634527403},
            };

            String out = solve(12, input);
            System.out.println(out);
            assert "2835\n".equals(out) : out;
        }

        test_8: {
            int[][] input = {
                    { 0, 0},
                    { 1, 0},
                    { 2, 0},
                    { 3, 0},
                    { 4, 0},
                    { 5, 0},
                    { 6, 0},
                    { 7, 0},
                    { 8, 0},
                    { 9, 0},
                    {10, 1},
                    {10, 0},
            };

            String out = solve(12, input);
            System.out.println(out);
            assert "8910\n".equals(out) : out;
        }

        System.out.println("All tests passed");
    }


    public static void main(String[] args) throws IOException {
        boolean assertionsEnabled = false;

        assert assertionsEnabled = true;
        if (assertionsEnabled) {
            new wormhole().test();
            return;
        }

        new wormhole().run();
    }
}
