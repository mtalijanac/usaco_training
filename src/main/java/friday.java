/*
ID: marko.t2
LANG: JAVA
TASK: friday
*/

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Chapter 1
 * Problem: Friday the Thirteenth
 */
public class friday {
    String inputfile = "friday.in";
    String outputfile = "friday.out";

    void run() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(inputfile));
        int N = Integer.parseInt(br.readLine());
        br.close();

        String res = friday(N);

        BufferedWriter bw = new BufferedWriter(new FileWriter(outputfile));
        bw.write(res);
        bw.close();
    }

    String friday(int N) {
        int[] daysInMonth = {
                31, 28, 31,   30, 31, 30,
                31, 31, 30,   31, 30, 31
        };

        int day = 1, month = 1, year = 1900, dayOfWeek = 1;
        int endingYear = 1900 + N;

        int endOfMonth = daysInMonth[0];

        int[] thirteens = {0, 0, 0, 0, 0, 0, 0}; // sunday - saturday

        while(true) {
            day += 1;
            dayOfWeek = (dayOfWeek + 1) % 7;

            if (day == 13) {
                thirteens[dayOfWeek]++;
            }

            if (day > endOfMonth) {
                day = 1;
                month += 1;

                if (month > 12) {
                    month = 1;
                    year += 1;

                    daysInMonth[1] = 28;
                    if (year % 4 == 0)   daysInMonth[1] = 29;
                    if (year % 100 == 0) daysInMonth[1] = 28;
                    if (year % 400 == 0) daysInMonth[1] = 29;
                }

                endOfMonth = daysInMonth[month - 1];
            }


            if (year == endingYear) {
                break;
            }
        }

        String res = thirteens[6] + " " + thirteens[0] + " " + thirteens[1] + " " + thirteens[2] + " " + thirteens[3] + " " + thirteens[4] + " " + thirteens[5] + "\n";
        return res;
    }

    void test() throws IOException {
        String res1 = friday(20);
        String expected1 = "36 33 34 33 35 35 34\n";
        assert expected1.equals(res1) : res1;

        System.out.println("Tests passed");
    }


    public static void main(String[] args) throws IOException {
        boolean assertionsEnabled = false;

        assert assertionsEnabled = true;
        if (assertionsEnabled) {
            new friday().test();
            return;
        }

        new friday().run();
    }
}
