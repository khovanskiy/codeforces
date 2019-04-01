import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Automaton {
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
		
		public boolean match(int s, String w) {
			boolean[][] currents = new boolean[2][n];
			int v = 0;
			currents[v][s] = true;
			
			for (int i = 0; i < w.length(); ++i) {
				for (int j = 0; j < n; ++j) {
					currents[(v + 1) % 2][j] = false;
				}
				int c = w.charAt(i) - (int)'a';
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
			in = new BufferedReader(new FileReader("automaton.in"));
			out = new BufferedWriter(new FileWriter("automaton.out"));

			int n = nextInt();
			int s = nextToken().charAt(0) - (int)'A' + 1;
			
			Graph fsm = new Graph(Graph.ALPHABET + 1);
			
			fsm.setTerminal(0, true);
			
			for (int i = 0; i < n; ++i) {
				String left = nextToken();
				nextToken();
				String right = nextToken();
				
				int a = left.charAt(0) - (int)'A' + 1;
				int b = 0;
				if (right.length() > 1) {
					b = right.charAt(1) - (int)'A' + 1;
				}
				char c = right.charAt(0);
				fsm.addEdge(a, b, c);
			}
			
			int m = nextInt();
			for (int i = 0; i < m; ++i) {
				String w = nextToken();
				if (fsm.match(s, w)) {
					out.write("yes\n");
				} else {
					out.write("no\n");
				}
			}
			in.close();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}