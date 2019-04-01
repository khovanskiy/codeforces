import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.StringTokenizer;

public class Equivalence {
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
		
		private boolean[] terminals;
		private int n;
		private int edges_count;
		private int terminals_count;
		private int[][] edges_to;
		private ArrayList<Integer>[][] edges_from;
		
		public Graph(int count) {
			this.n = count + 1;
			this.edges_count = 0;
			this.terminals_count = 0;
			this.terminals = new boolean[this.n];
			edges_to = new int[this.n][ALPHABET];
			edges_from = new ArrayList[this.n][ALPHABET];
			for (int i = 0; i < this.n; ++i) {
				for (int j = 0; j < ALPHABET; ++j) {
					edges_from[i][j] = new ArrayList<Integer>();
				}
			}
		}
		
		public void setTerminal(int a) {
			if (!this.terminals[a]) {
				this.terminals_count++;
			}
			this.terminals[a] = true;
		}
		
		public void addEdge(int a, int b, char c) {
			int j = c - (int)'a';
			this.edges_to[a][j] = b;
			this.edges_count++;
		}
		
		public void addEdge(int a, int b, int c) {
			//System.out.println("edges_to[" + a + "][" + c + "]=" + b);
			this.edges_to[a][c] = b;
			this.edges_count++;
		}
		
		public int getStatesCount() {
			return this.n - 1;
		}
		
		public int getEdgesCount() {
			return this.edges_count;
		}
		
		public int getTerminalsCount() {
			return this.terminals_count;
		}
		
		private void dumpTable(boolean[][] table) {
			System.out.print("__\t");
			for (int i = 0; i < table.length; ++i) {
				System.out.print((i) + "_\t");
			}
			System.out.print("\n");
			for (int i = 0; i < table.length; ++i) {
				System.out.print((i) + "|\t");
				for (int j = 0; j < table[i].length; ++j) {
					System.out.print((table[i][j]?1:0)+"\t");
				}
				System.out.print("\n");
			}
		}
		
		private void dfs(int u, boolean[] used) {
			used[u] = true;
			for (int c = 0; c < ALPHABET; ++c) {
				if (!used[edges_to[u][c]]) {
					dfs(edges_to[u][c], used);
				}
			}
		}
		
		public Graph minimization() {
			for (int i = 0; i < n; ++i) {
				for (int j = 0; j < ALPHABET; ++j) {
					edges_from[edges_to[i][j]][j].add(i);
				}
			}
			
			boolean[][] table = new boolean[n][n];
			
			boolean[] used = new boolean[n];
			dfs(1, used);
			//System.out.println(Arrays.toString(used));
			
			LinkedList<Integer> Q_first = new LinkedList<Integer>();
			LinkedList<Integer> Q_second = new LinkedList<Integer>();
			
			for (int i = 0; i < n; ++i) {
				for (int j = 0; j < n; ++j) {
					if (!table[i][j] && terminals[i] && !terminals[j] && i != j) {
						table[i][j] = true;
						table[j][i] = true;
						Q_first.add(i);
						Q_second.add(j);
					}
				}
			}
			
			//dumpTable(table);
			
			while (!Q_first.isEmpty()) {
				
				int u = Q_first.poll();
				int v = Q_second.poll();
				
				//System.out.println("UV = " + u + " "+ v);
				for (int c = 0; c < ALPHABET; ++c) {
					ArrayList<Integer> rr = this.edges_from[u][c];
					ArrayList<Integer> ss = this.edges_from[v][c];
					//System.out.println("C  = " + c);
					//System.out.println(Arrays.toString(this.edges_from[u][c].toArray()));
					//System.out.println(Arrays.toString(this.edges_from[v][c].toArray()));
					for (int i = 0; i < rr.size(); ++i)
					{
						int r = rr.get(i);
						for (int j = 0; j < ss.size(); ++j) {
							int s = ss.get(j);
							if (!table[r][s]) {
								table[r][s] = true;
								table[s][r] = true;
								Q_first.add(r);
								Q_second.add(s);
							}
						}
					}
				}
			}
			
			//dumpTable(table);
			
			int[] component = new int[n];
			Arrays.fill(component, -1);
			ArrayList<Integer> classes = new ArrayList<Integer>();
			
			for (int i = 0; i < n; ++i) {
				if (!table[0][i]) {
					component[i] = 0;
				}
			}
				
			int nC = 0;
					
			for (int i = 1; i < n; ++i) {
				if (!used[i]) {
					continue;
				}
				if (component[i] == -1) {
					nC++;
					component[i] = nC;
					classes.add(i);
					for (int j = i + 1; j < n; ++j) {
						if (!table[i][j]) {
							component[j] = nC;
						}
					}
				}
			}
			
			//trace(Arrays.toString(component));
			//trace(Arrays.toString(classes.toArray()));
			
			Graph minGraph = new Graph(classes.size());
			
			for (int i = 0; i < classes.size(); ++i) {
				int u = component[classes.get(i)];
				for (int c = 0; c < ALPHABET; ++c) {
					if (used[edges_to[classes.get(i)][c]] && component[edges_to[classes.get(i)][c]] != 0) {
						int v = component[edges_to[classes.get(i)][c]];
						//System.out.println("Add " + u + " " + v + " " + c);
						minGraph.addEdge(u, v, c);
					}
				}
			}
			
			for (int i = 0; i < classes.size(); ++i) {
				if (terminals[classes.get(i)]) {
					minGraph.setTerminal(component[classes.get(i)]);
				}
			}
			
			return minGraph;
		}
		
		public static boolean isIsomorphic(Graph a, Graph b) {
			if (a.n != b.n) {
				return false;
			}
			int[] colorA = new int[a.n];
			int[] colorB = new int[b.n];
			int[] isoA = new int[a.n];
			int[] isoB = new int[b.n];
			return dfs(a, b, 1, 1, colorA, colorB, isoA, isoB);
		}
		
		private static boolean dfs(Graph a, Graph b, int u1, int u2, int[] colorA, int[] colorB, int[] isoA, int[] isoB) {
			if (a.terminals[u1] != b.terminals[u2]) {
				return false;
			}
			colorA[u1] = 1;
			colorB[u2] = 1;
			isoA[u1] = u2;
			isoB[u2] = u1;
			for (int c = 0; c < ALPHABET; ++c) {
				if ((a.edges_to[u1][c] != 0) != (b.edges_to[u2][c] != 0)) {
					return false;
				}
				if (a.edges_to[u1][c] != 0) {
					int v1 = a.edges_to[u1][c];
					int v2 = b.edges_to[u2][c];
					if (colorA[v1] != colorB[v2]) {
						return false;
					}
					if (colorA[v1] != 0) {
						if (isoA[v1] != v2 || isoB[v2] != v1) {
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
		
		public static boolean isEquivalent(Graph a, Graph b) {
			Graph minA = a.minimization();
			Graph minB = b.minimization();
			return Graph.isIsomorphic(minA, minB);
		}
	}

	static StringTokenizer st;
	static BufferedReader in;
	static BufferedWriter out;

	public static void main(String[] args) {
		try {
			in = new BufferedReader(new FileReader("equivalence.in"));
			out = new BufferedWriter(new FileWriter("equivalence.out"));/**/
			/*in = new BufferedReader(new FileReader("minimization.in"));
			out = new BufferedWriter(new FileWriter("minimization.out"));*/
			
			int n = nextInt();
			int m = nextInt();
			int k = nextInt();
			
			Graph graph1 = new Graph(n);
			
			for (int i = 0; i < k; ++i) {
				int a = nextInt();
				graph1.setTerminal(a);
			}
			
			for (int i = 0; i < m; ++i) {
				int a = nextInt();
				int b = nextInt();
				char c = nextToken().charAt(0);
				graph1.addEdge(a, b, c);
			}
			
			/*Graph minGraph = graph1.minimization();
			out.write(minGraph.getStatesCount() + " " + minGraph.getEdgesCount() + " " + minGraph.getTerminalsCount() + "\n");
			for (int i = 1; i < minGraph.terminals.length; ++i) {
				if (minGraph.terminals[i]) {
					out.write(i + " ");
				}
			}
			out.write("\n");
			for (int i = 1; i < minGraph.n; ++i) {
				for (int c = 0; c < Graph.ALPHABET; ++c) {
					//System.out.println(i + " " + minGraph.edges_to[i][c] + " " + c);
					if (minGraph.edges_to[i][c] != 0) {
						char sym = (char)(c + (int)'a');
						out.write(i + " " + minGraph.edges_to[i][c] + " " + sym + "\n");
					}
				}
			}*/
			
			n = nextInt();
			m = nextInt();
			k = nextInt();
			
			Graph graph2 = new Graph(n);
			
			for (int i = 0; i < k; ++i) {
				int a = nextInt();
				graph2.setTerminal(a);
			}
			
			for (int i = 0; i < m; ++i) {
				int a = nextInt();
				int b = nextInt();
				char c = nextToken().charAt(0);
				graph2.addEdge(a, b, c);
			}
			
			if (Graph.isEquivalent(graph1, graph2)) {
				out.write("YES\n");
			} else {
				out.write("NO\n");
			}
			/*if (Graph.isIsomorphic(graph1, graph2)) {
				out.write("YES\n");
			} else {
				out.write("NO\n");
			}*/

			in.close();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}