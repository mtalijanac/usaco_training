/*
ID: marko.t2
LANG: JAVA
TASK: ride
*/

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Chapter 1
 * Problem: Your Ride Is Here
 */
public class ride {
	String inputfile = "ride.in";
	String outputfile = "ride.out";
	
	void run() throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(inputfile));
		String comet = reader.readLine();
		String group = reader.readLine();
		reader.close();
		
		String res = ride(comet, group);
		
		BufferedWriter bw = new BufferedWriter(new FileWriter(outputfile));
		bw.write(res);
		bw.close();
	}
	
	String ride(String cometName, String groupName) {
		long c = mod47(cometName);
		long g = mod47(groupName);
		String res = c == g ? "GO\n" : "STAY\n";
		return res;
	}
	
	long mod47(String val) {
		long prod = 1;
		for (int i = 0; i < val.length(); i++) {
			char ch = val.charAt(i);
			prod *= (ch - 'A' + 1l);
		}
		long res = prod % 47;
		return res;
	}
	
	void test() {
		String res1 = ride("COMETQ", "HVNGAT");
		assert "GO\n".equals(res1) : res1;

		String res2 = ride("ABSTAR", "USACO");
		assert "STAY\n".equals(res2) : res2;
		
		System.out.println("All tests passed");
	}

	
	public static void main(String[] args) throws IOException {
		boolean assertionsEnabled = false;
		
		assert assertionsEnabled = true;
		if (assertionsEnabled) {
			new ride().test();
			return;
		}
		
		new ride().run();
	}
}
