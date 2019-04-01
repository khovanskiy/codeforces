import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Problem3 {
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
	public static class State {
		public ArrayList<Integer> to = new ArrayList<Integer>();
		public ArrayList<Integer> from = new ArrayList<Integer>();
		public boolean isTerminal = false;
	}
	
	public static void findUsefulEdges(State[] states, int u, boolean[] used) {
		used[u] = true;
		State current = states[u];
		for (int i = 0; i < current.from.size(); ++i) {
			if (!used[current.from.get(i)]) {
				findUsefulEdges(states, current.from.get(i), used);
			}
		}
	}
	
	public static boolean checkCycles(State[] states, int u, boolean[] isUsefullEdge, int[] used) {
		//System.out.println("Now in U = " + (u+1));
		used[u] = 1;
		State current = states[u];
		for (int i = 0; i < current.to.size(); ++i) {
			int v = current.to.get(i);
			//System.out.println("\tcheck V = " + (v+1));
			if (isUsefullEdge[v]) {
				//System.out.println("\tIt is usefull V = " + (v+1));
				if (used[v] == 0) {
					checkCycles(states, v, isUsefullEdge, used);
				}
				if (used[v] == 1) {
					return true;
				}
			}
		}
		used[u] = 2;
		return false;
	}
	
	public static ArrayList<Integer> topsort(State[] states, boolean[] isUsefullEdge) {
		boolean[] visited = new boolean[states.length];
		ArrayList<Integer> result = new ArrayList<Integer>(isUsefullEdge.length);
		for (int i = 0; i < states.length; ++i) {
			if (isUsefullEdge[i] && !visited[i]) {
				dfs(states, i, isUsefullEdge, visited, result);
			}
		}
		return result;
	}
	
	public static void dfs(State[] states, int u, boolean[] isUsefullEdge, boolean[] visited, ArrayList<Integer> result) {
		visited[u] = true;
		State current = states[u];
		for (int i = 0; i < current.to.size(); ++i) {
			int v = current.to.get(i);
			if (isUsefullEdge[v] && !visited[v]) {
				dfs(states, v, isUsefullEdge, visited, result);
			}
		}
		result.add(u);
	}
	
	static final int P = (int) Math.pow(10, 9) + 7;

	static StringTokenizer st;
	static BufferedReader in;
	static BufferedWriter out;

	public static void main(String[] args) {
		try {
			in = new BufferedReader(new FileReader("problem3.in"));
			out = new BufferedWriter(new FileWriter("problem3.out"));

			int n = nextInt();
			int m = nextInt();
			int k = nextInt();
			
			State[] states = new State[n];
			for (int i = 0; i < n; ++i) {
				states[i] = new State();
			}
			
			ArrayList<Integer> terminals = new ArrayList<Integer>(n);
			for (int i = 0; i < k; ++i) {
				int a = nextInt() - 1;
				states[a].isTerminal = true;
				terminals.add(a);
			}
			
			for (int i = 0; i < m; ++i) {
				int a = nextInt() - 1;
				int b = nextInt() - 1;
				nextToken();
				states[a].to.add(b);
				states[b].from.add(a);
			}
			
			boolean[] isUsefullEdge = new boolean[n];
			for (int i = 0; i < terminals.size(); ++i) {
				findUsefulEdges(states, terminals.get(i), isUsefullEdge);
			}
			//System.out.println(Arrays.toString(isUsefullEdge));
			
			int[] visited = new int[n];
			boolean hasCycle = checkCycles(states, 0, isUsefullEdge, visited);
			//System.out.println(hasCycle);
			
			if (hasCycle) {
				out.write("-1\n");
			} else {
				ArrayList<Integer> result = topsort(states, isUsefullEdge);
				//System.out.println(Arrays.toString(result.toArray()));
				int[] paths = new int[n];
				paths[0] = 1;
				for (int i = result.size() - 1; i >= 0; --i) {
					int u = result.get(i);
					State current = states[u];
					for (int j = 0; j < current.from.size(); ++j) {
						int v = current.from.get(j);
						paths[u] = ((paths[u] % P) +(paths[v] % P)) % P;
					}
				}
				//System.out.println(Arrays.toString(paths));
				int sum = 0;
				for (int i = 0; i < terminals.size(); ++i) {
					sum = ((sum % P) + (paths[terminals.get(i)] % P)) % P;
				}
				out.write(sum + "\n");
			}
			in.close();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}