import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Epsilon {
	static String nextToken() {
		while (st == null || !st.hasMoreTokens()) {
			try {
				st = new StringTokenizer(in.readLine());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return st.nextToken();
	}

	static int nextInt() {
		return Integer.parseInt(nextToken());
	}

	public static final int ALPHABET = 26;
	
	static StringTokenizer st;
	static BufferedReader in;
	static BufferedWriter out;
	
	public static class Rule {
		
		private String r;
		public Rule(String r) {
			this.r = r;
		}
		
		public boolean isEpsilon(boolean[] epsilon) {
			for (int i = 0; i < r.length(); ++i) {
				char c = r.charAt(i);
				if (c >= 'a' && c <= 'z') {
					return false;
				}
				int v = c - (int)'A';
				if (!epsilon[v]) {
					return false;
				}
			}
			return true;
		}
	}

	public static void main(String[] args) {
		try {
			in = new BufferedReader(new FileReader("epsilon.in"));
			out = new BufferedWriter(new FileWriter("epsilon.out"));

			String[] buffer;
			buffer = in.readLine().split(" ");
			
			int n = Integer.parseInt(buffer[0]);
			int s = buffer[1].charAt(0) - (int)'A';
			
			ArrayList<Rule>[] rules = new ArrayList[ALPHABET];
			for (int i = 0; i < ALPHABET; ++i) {
				rules[i] = new ArrayList<Rule>();
			}
			boolean[] epsilon = new boolean[ALPHABET];
			
			for (int i = 0; i < n; ++i) {
				String[] lexems = in.readLine().split(" ");
				int a = lexems[0].charAt(0) - (int)'A';
				if (lexems.length > 2) {
					rules[a].add(new Rule(lexems[2]));
				} else {
					epsilon[a] = true;
				}
			}
			
			boolean updated = true;
			while (updated) {
				updated = false;
				for (int i = 0; i < ALPHABET; ++i) {
					if (!epsilon[i]) {
						for (int j = 0; j < rules[i].size(); ++j) {
							if (rules[i].get(j).isEpsilon(epsilon)) {
								epsilon[i] = true;
								updated = true;
								break;
							}
						}
					}
				}
			}
			
			for (int i = 0; i < ALPHABET; ++i) {
				if (epsilon[i]) {
					char c = (char)(i + (int)'A');
					out.write(c + " ");
				}
			}
			
			in.close();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}