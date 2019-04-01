import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;
import java.util.StringTokenizer;

public class Problem5 {
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

	static void mult(int n, long[][] c, long[][] a, long[][] b, long p) {
		for (int i = 0; i < n; ++i) {
			for (int j = 0; j < n; ++j) {
				c[i][j] = 0;
				for (int k = 0; k < n; ++k) {
					c[i][j] = ((c[i][j] % p) + ((a[i][k] * b[k][j]) % p)) % p;
				}
			}
		}
	}

	static long[][] pow(int n, long[][] m, int l, long p) {
		long[][] result = new long[n][n];
		for (int i = 0; i < n; ++i) {
			result[i][i] = 1;
		}
		long[][] buffer = new long[n][n];
		while (l != 0) {
			if ((l & 1) != 0) {
				mult(n, buffer, result, m, p);
				long[][] temp = result;
				result = buffer;
				buffer = temp;
			}
			mult(n, buffer, m, m, p);
			long[][] temp = m;
			m = buffer;
			buffer = temp;
			l >>= 1;
		}
		return result;
	}
	static final long PMOD = (long) Math.pow(10, 9) + 7;
	
	public static class Result {
		long[][] graph;
		int n;
		ArrayList<Integer> terminals;
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
		
		private class GhostNode {
			public int[] p = new int[ALPHABET];
			public boolean[] mask;
			public boolean[] available = new boolean[ALPHABET];
			public int id;
			public boolean isTerminal = false;
			
			public GhostNode(boolean[] mask, int id) {
				this.mask = mask;
				this.id = id;
			}
		}
		
		private class GhostEdge {
			public GhostNode start;
			public GhostNode end;
			public int symbol;
			
			public GhostEdge(GhostNode start, GhostNode end, int symbol) {
				this.start = start;
				this.end = end;
				this.symbol = symbol;
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
		
		private GhostNode findState(ArrayList<GhostNode> visited, GhostNode other) {
			for (int i = 0; i < visited.size(); ++i) {
				boolean[] a = visited.get(i).mask;
				boolean[] b = other.mask;
				boolean found = true;
				for (int j = 0; j < a.length; ++j) {
					if (a[j] != b[j]) {
						found = false;
						break;
					}
				}
				if (found) {
					return visited.get(i);
				}
			}
			return null;
		}
		
		public Result buildDFA() {
			ArrayList<GhostNode> visited = new ArrayList<GhostNode>();
			LinkedList<GhostNode> queue = new LinkedList<GhostNode>();
			ArrayList<Integer> newTerminals = new ArrayList<Integer>();
			ArrayList<GhostEdge> edges = new ArrayList<GhostEdge>();
			
			boolean[] startMask = new boolean[n];
			startMask[0] = true;
			
			GhostNode startState = new GhostNode(startMask, 0);
			queue.add(startState);
			visited.add(startState);
			if (states[0].isTerminal) {
				newTerminals.add(0);
			}
			
			while (!queue.isEmpty()) {
				GhostNode curState = queue.poll();
				//System.out.println("Poll state = #" + (curState.id + 1) + " " + echoMask(curState.mask));
				for (int c = 0; c < ALPHABET; ++c) {
					GhostNode newState = new GhostNode(new boolean[n], -1);
					boolean isEmpty = true;
					for (int u = 0; u < n; ++u) {
						if (curState.mask[u] && states[u].available[c]) {
							for (Integer v : states[u].p[c]) {
								//System.out.println("\tV = " + (v + 1) + " " + (char)(c + 'a') + " " + states[v].isTerminal);
								newState.mask[v] = true;
								isEmpty = false;
								if (states[v].isTerminal) {
									newState.isTerminal = true;
								}
							}
						}
					}
					if (!isEmpty) {
						//System.out.println("\tFind mask " + echoMask(newState.mask));
						GhostNode temp = findState(visited, newState);
						if (temp == null) {
							//System.out.println("\t\tWe didnt find it");
							newState.id = visited.size();
							//System.out.println("\t\tQ <- #" + (newState.id + 1)); 
							if (newState.isTerminal) {
								//System.out.println("\t\t Also #" + (newState.id + 1) + " is terminals");
								newTerminals.add(newState.id);
							}
							visited.add(newState);
							queue.add(newState);
							temp = newState;
						} else {
							//System.out.println("\t\tWe found it " + temp.id);
						}
						temp.isTerminal |= newState.isTerminal;
						temp.p[c] = temp.id;
						edges.add(new GhostEdge(curState, temp, c));
					}
				}
			}
			Result result = new Result();
			result.terminals = newTerminals;
			result.n = visited.size();
			System.out.println("Result: graph(" + result.n + ")");
			System.out.println(Arrays.toString(newTerminals.toArray()));
			long[][] graph = new long[result.n][result.n];
			for (int i = 0; i < edges.size(); ++i) {
				GhostEdge edge = edges.get(i);
				graph[edge.start.id][edge.end.id] += 1;
				//System.out.println(echoMask(edge.start.mask) + " " + echoMask(edge.end.mask) + " " + (char)(edge.symbol + 'a'));
				System.out.println(gbc(edge.start.id + 1) + " -> " + gbc(edge.end.id + 1) + " by " + edge.symbol);//(char)(edge.symbol + 'a'));
			}
			result.graph = graph;
			
			return result;
		}
		
		public char gbc(int i) {
			switch (i) {
			case 1: return 'A';
			case 2: return 'B';
			case 3: return 'C';
			case 4: return 'D';
			case 5: return 'E';
			case 6: return 'F';
			case 7: return 'G';
			case 8: return 'H';
			case 9: return 'I';
			}
			return 'R';
		}
		
		/*private String echoMask(boolean[] mask) {
			ArrayList<Integer> list = new ArrayList<Integer>();
			for (int i = 0; i < mask.length; ++i) {
				if (mask[i]) {
					list.add(i + 1);
				}
			}
			return Arrays.toString(list.toArray());
		}*/
	}

	static StringTokenizer st;
	static BufferedReader in;
	static BufferedWriter out;

	public static void main(String[] args) {
		try {
			in = new BufferedReader(new FileReader("problem5.in"));
			out = new BufferedWriter(new FileWriter("problem5.out"));

			int n = nextInt();
			int m = nextInt();
			int k = nextInt();
			int l = nextInt();

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
			
			Result dfa = fsm.buildDFA();

			long[][] result = pow(dfa.n, dfa.graph, l, PMOD);

			long sum = 0;
			//System.out.println(Arrays.toString(result[0]));
			for (int i = 0; i < dfa.terminals.size(); ++i) {
				sum = ((sum % PMOD) + (result[0][dfa.terminals.get(i)] % PMOD)) % PMOD;
			}

			out.write(sum + "\n");

			in.close();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}