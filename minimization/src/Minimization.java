import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.StringTokenizer;

public class Minimization {
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
		
		private boolean[] terminals;
		private int n;
		private int[][] edges_to;
		private ArrayList<Integer>[][] edges_from;
		
		public Graph(int count) {
			this.n = count + 1;
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
			this.terminals[a + 1] = true;
		}
		
		public void addEdge(int a, int b, char c) {
			int j = c - (int)'a';
			this.edges_to[a + 1][j] = b + 1;
			//this.edges_from[b + 1][j].add(a + 1);
		}
		private void dumpArray(boolean[] used) {
			System.out.print("{| border=\"1\" class=\"wikitable\"\n");
			System.out.print("! \n");
			for (int i = 0; i < used.length; ++i) {
				System.out.print("! " + (char)(i + 'A' - 1) + "\n");
			}
			System.out.print("|-\n");
			for (int i = 0; i < used.length; ++i) {
				System.out.print("|" + (used[i]?"true":"false")+"\n");
			}
			System.out.print("|}\n");
		}
		private void dumpTable(boolean[][] table) {
			System.out.print("{| border=\"1\" class=\"wikitable\"\n");
			System.out.print("! \n");
			for (int i = 0; i < table.length; ++i) {
				System.out.print("! " + (char)(i + 'A' - 1) + "\n");
			}
			for (int i = 0; i < table.length; ++i) {
				System.out.print("|-\n! "+ (char)(i + 'A' - 1) + "\n");
				for (int j = 0; j < table[i].length; ++j) {
					System.out.print("|" + (table[i][j]?"marked":"")+"\n");
				}
			}
			System.out.print("|}\n");
		}
		
		private void dfs(int u, boolean[] used) {
			used[u] = true;
			for (int c = 0; c < ALPHABET; ++c) {
				if (!used[edges_to[u][c]]) {
					dfs(edges_to[u][c], used);
				}
			}
		}
		
		public void minimization(BufferedWriter out) throws IOException {
			// Строим список обратных ребер
			for (int i = 0; i < n; ++i) {
				for (int j = 0; j < ALPHABET; ++j) {
					edges_from[edges_to[i][j]][j].add(i);
				}
			}
			
			boolean[][] table = new boolean[n][n];
			
			boolean[] used = new boolean[n];
			dfs(1, used);
			//dumpArray(used);
			System.out.println(Arrays.toString(used));
			
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
				
				for (int c = 0; c < ALPHABET; ++c) {
					ArrayList<Integer> rr = this.edges_from[u][c];
					ArrayList<Integer> ss = this.edges_from[v][c];
					
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
					System.out.println(i);
					continue;
				}
				if (component[i] == -1) {
					nC++;
					component[i] = nC;
					classes.add(i);
					for (int j = i + 1; j < n; ++j) {
						if (!table[i][j] && !used[i]) {
							component[j] = nC;
						}
					}
				}
			}
			
			System.out.println(Arrays.toString(component));
			System.out.println(Arrays.toString(classes.toArray()));
			
			ArrayList<Edge> edges = new ArrayList<Edge>();
			for (int i = 0; i < classes.size(); ++i) {
				int u = component[classes.get(i)];
				for (int c = 0; c < ALPHABET; ++c) {
					if (used[edges_to[classes.get(i)][c]] && component[edges_to[classes.get(i)][c]] != 0) {
						int v = component[edges_to[classes.get(i)][c]];
						System.out.println(u + " " + v + " " + (char)(c + (int)'a'));
						Edge edge = new Edge();
						edge.from = u;
						edge.to = v;
						edge.c = (char)(c + (int)'a');
						edges.add(edge);
					}
				}
			}
			
			int k = 0;
			ArrayList<Integer> terms = new ArrayList<Integer>();
			for (int i = 0; i < classes.size(); ++i) {
				if (terminals[classes.get(i)]) {
					++k;
					terms.add(component[classes.get(i)]);
				}
			}
			
			out.write(classes.size() + " " + edges.size() + " " + k + "\n");
			
			for (int i = 0; i < terms.size(); ++i) {
				out.write(terms.get(i) + " ");
			}
			
			out.write("\n");
			
			for (int i = 0; i < edges.size(); ++i) {
				Edge edge = edges.get(i);
				out.write(edge.from + " " + edge.to + " " + (edge.c) + "\n");
			}
		}
		
		private class Edge {
			int from;
			int to;
			char c;
		}
	}

	static StringTokenizer st;
	static BufferedReader in;
	static BufferedWriter out;

	public static void main(String[] args) {
		try {
			in = new BufferedReader(new FileReader("minimization.in"));
			out = new BufferedWriter(new FileWriter("minimization.out"));

			int n = nextInt();
			int m = nextInt();
			int k = nextInt();
			
			Graph graph = new Graph(n);
			
			for (int i = 0; i < k; ++i) {
				int a = nextToken().charAt(0) - 'A';
				graph.setTerminal(a);
			}
			
			for (int i = 0; i < m; ++i) {
				int a = nextToken().charAt(0) - 'A';
				int b = nextToken().charAt(0) - 'A';
				char c = nextToken().charAt(0);
				graph.addEdge(a, b, c);
			}
			
			graph.minimization(out);

			in.close();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}