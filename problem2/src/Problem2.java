import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Problem2 {
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
	
	public static class Graph {
		
		public static final int ALPHABET = 26;
		
		private class State {
			public ArrayList<Integer>[] p = new ArrayList[ALPHABET];
			public boolean[] available = new boolean[ALPHABET];
			public boolean isTerminal = false;
			
			public State() {
				for (int i = 0; i < ALPHABET; ++i) {
					this.p[i] = new ArrayList<Integer>();
				}
			}
		}
		
		private State[] states;
		private int n;
		
		public Graph(int n) {
			this.n = n;
			this.states = new State[n];
			for (int i = 0; i < n; ++i) {
				this.states[i] = new State();
			}
		}
		
		public void setTerminal(int a, boolean isTerminal) {
			states[a].isTerminal = isTerminal;
		}
		
		public void addEdge(int a, int b, char c) {
			int j = c - (int)'a';
			states[a].p[j].add(b);
			states[a].available[j] = true;
		}
		
		public boolean match(String s) {
			boolean[][] currents = new boolean[2][n];
			int v = 0;
			currents[v][0] = true;
			
			for (int i = 0; i < s.length(); ++i) {
				for (int j = 0; j < n; ++j) {
					currents[(v + 1) % 2][j] = false;
				}
				int c = s.charAt(i) - (int)'a';
				for (int j = 0; j < n; ++j)
				{
					if (currents[v][j]) {
						State current = states[j];
						if (current.available[c]) {
							ArrayList<Integer> edges = current.p[c];
							for (int k = 0; k < edges.size(); ++k) {
								currents[(v + 1) % 2][edges.get(k)] |= true;
							}
						}
					}
				}
				v = (v + 1) % 2;
			}
			
			for (int i = 0; i < n; ++i) {
				if (currents[v][i] && states[i].isTerminal) {
					return true;
				}
			}
			return false;
		}
	}

	static StringTokenizer st;
	static BufferedReader in;
	static BufferedWriter out;

	public static void main(String[] args) {
		try {
			in = new BufferedReader(new FileReader("problem2.in"));
			out = new BufferedWriter(new FileWriter("problem2.out"));

			String s = nextToken();
			int n = nextInt();
			int m = nextInt();
			int k = nextInt();
			
			Graph fsm = new Graph(n);
			
			for (int i = 0; i < k; ++i) {
				int a = nextInt() - 1;
				fsm.setTerminal(a, true);
			}
			
			for (int i = 0; i < m; ++i) {
				int a = nextInt() - 1;
				int b = nextInt() - 1;
				char c = nextToken().charAt(0);
				fsm.addEdge(a, b, c);
			}
			
			if (fsm.match(s)) {
				out.write("Accepts\n");
			} else {
				out.write("Rejects\n");
			}

			in.close();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}