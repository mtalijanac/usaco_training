/*
ID: marko.t2
LANG: JAVA
TASK: namenum
*/

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

/**
 * Chapter 1, Section 1.3
 * Problem: Name That Number
 */
public class namenum {
    String dictfile = "dict.txt";
    String inputfile = "namenum.in";
    String outputfile = "namenum.out";

    StringBuilder sb = new StringBuilder(30);
    HashSet<String> acceptableNames;
    HashSet<String> foundNames = new HashSet<>();

    final char[][] keypad = {
            {}, {},
            {'A', 'B', 'C'},
            {'D', 'E', 'F'},
            {'G', 'H', 'I'},
            {'J', 'K', 'L'},
            {'M', 'N', 'O'},
            {'P', 'R', 'S'},
            {'T', 'U', 'V'},
            {'W', 'X', 'Y'}};


    void run() throws IOException {
        acceptableNames = loadDict(dictfile);

        BufferedReader reader = new BufferedReader(new FileReader(inputfile));
        String number = reader.readLine();
        reader.close();

        String result = solve(number);

        BufferedWriter bw = new BufferedWriter(new FileWriter(outputfile));
        bw.write(result);
        bw.close();
    }

    HashSet<String> loadDict(String path) throws IOException {
        HashSet<String> dict = new HashSet<String>();
        BufferedReader reader = new BufferedReader(new FileReader(path));
        while (true) {
            String line = reader.readLine();
            if (line == null) break;
            if (line.trim().isEmpty()) {
                continue;
            }
            dict.add(line);
        }
        reader.close();
        return dict;
    }


    String solve(String input) {
        foundNames.clear();

        int[] serialnumber = new int[input.length()];
        for (int idx = 0; idx < input.length(); idx++) {
            serialnumber[idx] =  input.charAt(idx) - '0';
        }

        recursiveSolver(0, serialnumber, new ArrayList<>());

        if (foundNames.size() == 0) {
            return "NONE\n";
        }

        String[] array = foundNames.toArray(new String[foundNames.size()]);
        Arrays.sort(array, 0, array.length, String::compareTo);

        StringBuilder res = new StringBuilder();
        for (String str: array) {
            res.append(str).append("\n");
        }
        return res.toString();
    }

    void recursiveSolver(int idx, int[] serialNumber, ArrayList<Character> nameChars) {
        if (idx == serialNumber.length) {
            sb.setLength(0);
            for (Character ch: nameChars) {
                sb.append(ch.charValue());
            }
            String constructedName = sb.toString();
            if (acceptableNames.contains(constructedName)) {
                foundNames.add(constructedName);
            }
            return;
        }

        int digit = serialNumber[idx];
        if (digit == 0 || digit == 1) {
            recursiveSolver(idx + 1, serialNumber, nameChars);
            return;
        }

        char[] letters = keypad[digit];
        for (char ch: letters) {
            nameChars.add(ch);
            recursiveSolver(idx + 1, serialNumber, nameChars);
            nameChars.remove(nameChars.size() - 1);
        }
    }


    void test() throws IOException {
        acceptableNames = loadDict("./chapter_1/section_1.3/namenumdict.txt");

        example: {
            String out = solve("4734");
            assert "GREG\n".equals(out) : out;
        }

        run_12: {
            String out = solve("463373633623"); // slowest run
            assert "INDEPENDENCE\n".equals(out) : out;
        }

        System.out.println("All tests passed");
    }


    public static void main(String[] args) throws IOException {
        boolean assertionsEnabled = false;

        assert assertionsEnabled = true;
        if (assertionsEnabled) {
            new namenum().test();
            return;
        }

        new namenum().run();
    }
}
