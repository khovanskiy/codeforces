import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.StringTokenizer;

public class Destroy {
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

	static long nextLong() {
		return Long.parseLong(nextToken());
	}

	static void trace(int s) {
		System.out.println(s);
	}

	static <T> void trace(T s) {
		System.out.println(s);
	}

	static void trace(String s) {
		System.out.println(s);
	}

	static StringTokenizer st;
	static BufferedReader in;
	static BufferedWriter out;

	static class Task {
		public int d;
		public int w;

		public Task(int d, int w) {
			this.d = d;
			this.w = w;
		}

		@Override
		public String toString() {
			return "[" + d + ", " + w + "]";
		}
	}

	static class Pair<S, F> {
		public S a;
		public F b;

		public Pair(S a, F b) {
			this.a = a;
			this.b = b;
		}
	}

	static class Edge {
		public int id;
		public long weight;
		public int a;
		public int b;

		public Edge(int id, int a, int b, long weight) {
			this.id = id;
			this.a = a;
			this.b = b;
			this.weight = weight;
		}
	}

	static class WeightDown implements Comparator<Edge> {
		@Override
		public int compare(Edge o1, Edge o2) {
			return -Long.compare(o1.weight, o2.weight);
		}
	}

	static class WeightUp implements Comparator<Edge> {
		@Override
		public int compare(Edge o1, Edge o2) {
			return Long.compare(o1.weight, o2.weight);
		}
	}

	static class DSU
	{
		int[] parent;
		int[] rank;
		public DSU(int size)
		{
			parent = new int[size];
			rank = new int[size];
		}
		
		void make(int v)
		{
			parent[v] = v;
			rank[v] = 0;
		}
		 
		int get(int v)
		{
			if (v == parent[v])
			{
				return v;
			}
			return parent[v] = get (parent[v]);
		}
		 
		void union(int a, int b)
		{
			a = get(a);
			b = get(b);
			if (a != b)
			{
				if (rank[a] < rank[b])
				{
					int temp = a;
					a = b;
					b = temp;
				}
				parent[b] = a;
				if (rank[a] == rank[b])
				{
					++rank[a];
				}
			}
		}
	}

	public static void main(String[] args) {
		try {
			in = new BufferedReader(new FileReader("destroy.in"));
			out = new BufferedWriter(new FileWriter("destroy.out"));
			int n = nextInt();
			int m = nextInt();
			long s = nextLong();
			Edge[] g = new Edge[m];
			for (int i = 0; i < m; ++i) {
				int a = nextInt() - 1;
				int b = nextInt() - 1;
				long w = nextLong();
				g[i] = new Edge(i, a, b, w);
			}
			
			ArrayList<Edge> result = new ArrayList<>();

			DSU dsu = new DSU(n);
			Arrays.sort(g, new WeightDown());

			for (int iterator = 0; iterator < n; ++iterator) {
				dsu.make(iterator);
			}

			for (int iterator = 0; iterator < m; ++iterator) {
				int a = g[iterator].a;
				int b = g[iterator].b;
				if (dsu.get(a) != dsu.get(b)) {
					dsu.union(a, b);
				} else {
					result.add(g[iterator]);
				}
			}

			ArrayList<Integer> answer = new ArrayList<>(m);

			for (int iterator = result.size() - 1; iterator >= 0; --iterator) {
				Edge e = result.get(iterator);
				//trace((e.a + 1) + " " + (e.b + 1) + " " + e.weight);
				if (s >= e.weight) {
					s = s - e.weight;
					answer.add(e.id);
				} else {
					break;
				}
			}

			Collections.sort(answer);

			out.write(answer.size() + "\n");
			for (int iterator = 0; iterator < answer.size(); ++iterator) {
				out.write((answer.get(iterator) + 1) + " ");
			}

			in.close();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}