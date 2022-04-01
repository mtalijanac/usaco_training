/*
ID: marko.t2
LANG: JAVA
TASK: gift1
*/

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Chapter 1
 * Problem: Greedy Gift Givers
 */
public class gift1 {
    String inputfile = "gift1.in";
    String outputfile = "gift1.out";

    void run() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(inputfile));
        String result = gift(reader);
        BufferedWriter bw = new BufferedWriter(new FileWriter(outputfile));
        bw.write(result);
        bw.close();
        reader.close();
    }

    String gift(BufferedReader reader) throws IOException {
        int NP = Integer.parseInt(reader.readLine());

        LinkedHashMap<String, AtomicLong> wallets = new LinkedHashMap<String, AtomicLong>();
        for (int i = 0; i < NP; i++) {
            String name = reader.readLine();
            wallets.put(name, new AtomicLong(0));
        }

        while (true) {
            String giver = reader.readLine();
            if (giver == null) break;

            String wallet_and_friends = reader.readLine();
            String[] wf = wallet_and_friends.split(" ");

            long initialAmount = Long.parseLong(wf[0]);
            long friendCount = Long.parseLong(wf[1]);

            if (friendCount == 0) {
                wallets.get(giver).addAndGet(initialAmount);
                continue;
            }

            long giftAmount = initialAmount / friendCount;
            long remainder = initialAmount % friendCount;

            wallets.get(giver).addAndGet((-1l * initialAmount) + remainder);

            for (int i = 0; i < friendCount; i++) {
                String friend = reader.readLine();
                wallets.get(friend).addAndGet(giftAmount);
            }
        }

        StringBuffer sb = new StringBuffer();
        for (Entry<String, AtomicLong> e: wallets.entrySet()) {
            String name = e.getKey();
            AtomicLong value = e.getValue();
            sb.append(name).append(" ").append(value.get()).append("\n");
        }
        return sb.toString();
    }

    void test() throws IOException {
        String input = "5\n"
                     + "dave\n"
                     + "laura\n"
                     + "owen\n"
                     + "vick\n"
                     + "amr\n"
                     + "dave\n"
                     + "200 3\n"
                     + "laura\n"
                     + "owen\n"
                     + "vick\n"
                     + "owen\n"
                     + "500 1\n"
                     + "dave\n"
                     + "amr\n"
                     + "150 2\n"
                     + "vick\n"
                     + "owen\n"
                     + "laura\n"
                     + "0 2\n"
                     + "amr\n"
                     + "vick\n"
                     + "vick\n"
                     + "0 0\n";

        BufferedReader reader = new BufferedReader(new StringReader(input));
        String res = gift(reader);

        String expected = "dave 302\n"
                + "laura 66\n"
                + "owen -359\n"
                + "vick 141\n"
                + "amr -150\n";

        assert expected.equals(res) : res;
        System.out.println("Tests passed");
    }


    public static void main(String[] args) throws IOException {
        boolean assertionsEnabled = false;

        assert assertionsEnabled = true;
        if (assertionsEnabled) {
            new gift1().test();
            return;
        }

        new gift1().run();
    }
}
