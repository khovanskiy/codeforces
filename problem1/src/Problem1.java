import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.StringTokenizer;

public class Problem1 {
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

	static void trace(int s) {
		System.out.println(s);
	}

	static void trace(String s) {
		System.out.println(s);
	}
	
	public static class Graph {
		
		public static final int ALPHABET = 26;
		
		private class State {
			public int[] p = new int[ALPHABET];
			public boolean[] available = new boolean[ALPHABET];
			public boolean isTerminal = false;
		}
		
		private State[] states;
		
		public Graph(int n) {
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
			states[a].p[j] = b;
			states[a].available[j] = true;
		}
		
		public boolean match(String s) {
			State current = states[0];
			for (int i = 0; i < s.length(); ++i) {
				int c = s.charAt(i) - (int)'a';
				if (current.available[c]) {
					current = states[current.p[c]];
				} else {
					return false;
				}
			}
			return current.isTerminal;
		}
	}

	static StringTokenizer st;
	static BufferedReader in;
	static BufferedWriter out;

	public static void main(String[] args) {
		try {
			in = new BufferedReader(new FileReader("problem1.in"));
			out = new BufferedWriter(new FileWriter("problem1.out"));

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