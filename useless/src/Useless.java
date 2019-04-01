import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Useless {
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
	
	public static <T> void trace(T s) {
		System.out.println(s);
	}
	
	public static class Rule {
		
		private int u;
		public String r;
		
		public Rule(int u, String r) {
			this.u = u;
			this.r = r;
		}
		
		public boolean isGenerating(boolean[] generating) {
			boolean terminals = true;
			boolean noterminals = true;
			for (int i = 0; i < r.length(); ++i) {
				char c = r.charAt(i);
				if (c >= 'a' && c <= 'z') {
					continue;
				} else {
					terminals = false;
				}
				int v = c - (int)'A';
				noterminals &= generating[v];
			}
			return terminals || noterminals;
		}
		
		@Override
		public String toString() {
			return (char)(u + 'A') + " -> " + r;
		}
	}

	public static void main(String[] args) {
		try {
			in = new BufferedReader(new FileReader("useless.in"));
			out = new BufferedWriter(new FileWriter("useless.out"));

			String[] buffer;
			buffer = in.readLine().split(" ");
			
			int n = Integer.parseInt(buffer[0]);
			int s = buffer[1].charAt(0) - (int)'A';
			
			ArrayList<Rule>[] rules = new ArrayList[ALPHABET];
			for (int i = 0; i < ALPHABET; ++i) {
				rules[i] = new ArrayList<Rule>();
			}
			
			boolean[] exists = new boolean[ALPHABET];
			
			for (int i = 0; i < n; ++i) {
				String[] lexems = in.readLine().split(" ");
				int a = lexems[0].charAt(0) - (int)'A';
				exists[a] = true;
				if (lexems.length > 2) {
					rules[a].add(new Rule(a, lexems[2]));
					for (int j = 0; j < lexems[2].length(); ++j) {
						char c = lexems[2].charAt(j);
						if (c >= 'a' && c <= 'z') {
							continue;
						}
						int v = c - (int)'A';
						exists[v] = true;
					}
				} else {
					rules[a].add(new Rule(a, ""));
				}
			}
			
			boolean[] generating = new boolean[ALPHABET];
			
			boolean updated = true;
			while (updated) {
				updated = false;
				for (int i = 0; i < ALPHABET; ++i) {
					if (!generating[i]) {
						for (int j = 0; j < rules[i].size(); ++j) {
							if (rules[i].get(j).isGenerating(generating)) {
								generating[i] = true;
								updated = true;
								break;
							}
						}
					}
				}
			}
			
			for (int i = 0; i < ALPHABET; ++i) {
				if (!generating[i]) {
					rules[i].clear();
				} else {
					ArrayList<Rule> temp = new ArrayList<Rule>(rules[i].size());
					for (int j = 0; j < rules[i].size(); ++j) {
						if (rules[i].get(j).isGenerating(generating)) {
							temp.add(rules[i].get(j));
						}
					}
					rules[i] = temp;
				}
			}
			
			boolean[] reachable = new boolean[ALPHABET];
			LinkedList<Integer> queue = new LinkedList<Integer>();
			reachable[s] = true;
			queue.add(s);
			
			while (!queue.isEmpty()) {
				int u = queue.poll();
				for (int i = 0; i < rules[u].size(); ++i) {
					Rule current = rules[u].get(i);
					for (int j = 0; j < current.r.length(); ++j) {
						char c = current.r.charAt(j);
						if (c >= 'a' && c <= 'z') {
							continue;
						}
						int v = c - (int)'A';
						if (!reachable[v]) {
							reachable[v] = true;
							queue.add(v);
						}
					}
				}
			}
			
			for (int i = 0; i < ALPHABET; ++i) {
				//trace((char)(i + 'A') + " " + exists[i] + " " + generating[i] + " " + reachable[i]);
				if (exists[i] && (!generating[i] || !reachable[i])) {
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