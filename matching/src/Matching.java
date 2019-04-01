import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Arrays;
import java.util.StringTokenizer;
import java.util.Vector;

public class Matching {
	static class Node {
		public int u;
		public long cost;

		public Node(int u, long cost) {
			this.u = u;
			this.cost = cost;
		}
	}

	static class Edge implements Comparable<Edge> {
		public int u;
		public int v;
		public long cost;

		public Edge(int u, int v, long cost) {
			this.u = u;
			this.v = v;
			this.cost = cost;
		}

		@Override
		public int compareTo(Edge other) {
			if (this.cost < other.cost) {
				return -1;
			} else if (this.cost > other.cost) {
				return 1;
			}
			return 0;
		}
	}

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

	static boolean K(int v, int m) {
		if (used[v]) {
			return false;
		}

		used[v] = true;
		for (int i = 0; i < m; ++i) {
			if (g[v][i] == 0) {
				continue;
			}
			int to = i;
			if (matching[to] == -1 || K(matching[to], m)) {
				matching[to] = v;
				return true;
			}

		}
		return false;
	}

	static StringTokenizer st;
	static BufferedReader in;
	static BufferedWriter out;
	static int[][] g;
	static int[] matching;
	static boolean[] used;

	static final long INF = Integer.MAX_VALUE;

	public static void main(String[] args) {
		try {
			in = new BufferedReader(new FileReader("matching.in"));
			out = new BufferedWriter(new FileWriter("matching.out"));
			int n = nextInt();
			int m = nextInt();
			int k = nextInt();

			g = new int[n][m];

			for (int i = 0; i < k; ++i) {
				int a = nextInt() - 1;
				int b = nextInt() - 1;
				g[a][b] = 1;
			}

			matching = new int[n + m];
			for (int i = 0; i < m; ++i) {
				matching[i] = -1;
			}

			boolean[] temp = new boolean[n];

			for (int i = 0; i < n; ++i) {
				for (int j = 0; j < m; ++j) {
					if (g[i][j] == 0)
						continue;
					if (matching[j] == -1) {
						matching[j] = i;
						temp[i] = true;
						break;
					}
				}
			}

			used = new boolean[n];

			for (int i = 0; i < n; ++i) {
				if (temp[i]) {
					continue;
				}

				for (int j = 0; j < n; ++j) {
					used[j] = false;
				}

				K(i, m);
			}

			int count = 0;
			for (int i = 0; i < m; ++i) {
				if (matching[i] != -1) {
					// System.out.print((array[i] + 1) + " " + (i + 1)+"\n");
					++count;
				}
			}
			out.write(count + "");

			in.close();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}