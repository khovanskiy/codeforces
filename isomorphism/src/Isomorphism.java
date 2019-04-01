import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.StringTokenizer;

public class Isomorphism {
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

	/*static void trace(int s) {
		System.out.println(s);
	}

	static void trace(String s) {
		System.out.println(s);
	}*/
	
	public static class Graph {
		
		public static final int ALPHABET = 26;
		
		private class State {
			public int[] p = new int[ALPHABET];
			public boolean[] available = new boolean[ALPHABET];
			public boolean isTerminal = false;
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
			states[a].p[j] = b;
			states[a].available[j] = true;
		}
		
		public static boolean isIsomorphic(Graph a, Graph b) {
			if (a.n != b.n) {
				return false;
			}
			int[] colorA = new int[a.n];
			int[] colorB = new int[b.n];
			int[] isoA = new int[a.n];
			int[] isoB = new int[b.n];
			return dfs(a, b, 0, 0, colorA, colorB, isoA, isoB);
		}
		
		private static boolean dfs(Graph a, Graph b, int u1, int u2, int[] colorA, int[] colorB, int[] isoA, int[] isoB) {
			//trace("Pair: " + (u1 + 1) + " " + (u2 + 1));
			if (a.states[u1].isTerminal != b.states[u2].isTerminal) {
				//trace("Diff teminals: U1=" + a.states[u1].isTerminal +" U2="+b.states[u2].isTerminal);
				return false;
			}
			colorA[u1] = 1;
			colorB[u2] = 1;
			isoA[u1] = u2;
			isoB[u2] = u1;
			for (int c = 0; c < ALPHABET; ++c) {
				if (a.states[u1].available[c] != b.states[u2].available[c]) {
					//trace("Diff available (" + (char)(c+(int)'a') + "): U1=" + a.states[u1].available[c] +" U2=" + b.states[u2].available[c]);
					return false;
				}
				if (a.states[u1].available[c]) {
					int v1 = a.states[u1].p[c];
					int v2 = b.states[u2].p[c];
					//trace("\tChild: " + (v1 + 1) + " " + (v2 + 1));
					if (colorA[v1] != colorB[v2]) {
						//trace("\tDiff states: colorA[V1]=" + colorA[v1] +" colorB[V2]="+colorB[v2]);
						return false;
					}
					if (colorA[v1] != 0) {
						if (isoA[v1] != v2 || isoB[v2] != v1) {
							//trace("\tDiff states: ISO(V1)=" + (isoA[v1]+1) +" V2="+(v2+1));
							//trace("\tDiff states: V1=" + (v1+1) +" ISO(V2)="+(isoB[v2]+1));
							return false;
						}
					}
					if (colorA[v1] == 0) {
						if (!dfs(a, b, v1, v2, colorA, colorB, isoA, isoB)) {
							return false;
						}
					}
				}
			}
			colorA[u1] = 2;
			colorB[u2] = 2;
			return true;
		}
	}

	static StringTokenizer st;
	static BufferedReader in;
	static BufferedWriter out;

	public static void main(String[] args) {
		try {
			in = new BufferedReader(new FileReader("isomorphism.in"));
			out = new BufferedWriter(new FileWriter("isomorphism.out"));

			int n = nextInt();
			int m = nextInt();
			int k = nextInt();
			
			Graph graph1 = new Graph(n);
			
			for (int i = 0; i < k; ++i) {
				int a = nextInt() - 1;
				graph1.setTerminal(a, true);
			}
			
			for (int i = 0; i < m; ++i) {
				int a = nextInt() - 1;
				int b = nextInt() - 1;
				char c = nextToken().charAt(0);
				graph1.addEdge(a, b, c);
			}
			
			n = nextInt();
			m = nextInt();
			k = nextInt();
			
			Graph graph2 = new Graph(n);
			
			for (int i = 0; i < k; ++i) {
				int a = nextInt() - 1;
				graph2.setTerminal(a, true);
			}
			
			for (int i = 0; i < m; ++i) {
				int a = nextInt() - 1;
				int b = nextInt() - 1;
				char c = nextToken().charAt(0);
				graph2.addEdge(a, b, c);
			}
			
			if (Graph.isIsomorphic(graph1, graph2)) {
				out.write("YES\n");
			} else {
				out.write("NO\n");
			}

			in.close();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}