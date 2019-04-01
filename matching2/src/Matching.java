import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.StringTokenizer;

public class Matching {
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

	static void trace(int[][] arr) {
		for (int i = 0; i < arr.length; ++i) {
			for (int j = 0; j < arr[i].length; ++j) {
				System.out.print(arr[i][j] + " ");
			}
			System.out.print("\n");
		}
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
	
	static class Pair<S, F> {
		public S a;
		public F b;

		public Pair(S a, F b) {
			this.a = a;
			this.b = b;
		}
	}

	static StringTokenizer st;
	static BufferedReader in;
	static BufferedWriter out;
	static int n;
	static Pair<Integer, Integer>[] weightOfFirstSide;
	static ArrayList<Integer>[] graphForMatching;
	static boolean[] usedVertexes;
	static int[] matching;
	static int[] reverse;

	static boolean Kalgorithm(int v) {
		if (usedVertexes[v]) {
			return false;
		}

		usedVertexes[v] = true;
		for (int i = 0; i < graphForMatching[v].size(); ++i) {
			int to = graphForMatching[v].get(i);

			if (matching[to] == -1 || Kalgorithm(matching[to])) {
				matching[to] = v;
				reverse[v] = to;
				return true;
			}

		}
		return false;
	}
	
	static class WeightDown implements Comparator<Pair<Integer, Integer>> {

		@Override
		public int compare(Pair<Integer, Integer> o1, Pair<Integer, Integer> o2) {
			return -Integer.compare(o1.b, o2.b);
		}
		
	}

	public static void main(String[] args) {
		try {
			in = new BufferedReader(new FileReader("matching.in"));
			out = new BufferedWriter(new FileWriter("matching.out"));

			n = nextInt();

			weightOfFirstSide = new Pair[n];
			for (int i = 0; i < n; ++i) {
				int weight = nextInt();
				weightOfFirstSide[i] = new Pair(i, weight);
			}
			
			Arrays.sort(weightOfFirstSide, new WeightDown());

			graphForMatching = new ArrayList[n];
			for (int iterator = 0; iterator < n; ++iterator) {
				graphForMatching[iterator] = new ArrayList<>();
				int k = nextInt();
				for (int j = 0; j < k; ++j) {
					int b = nextInt() - 1;
					graphForMatching[iterator].add(b);
				}
			}

			matching = new int[n];
			reverse = new int[n];
			for (int iterator = 0; iterator < n; ++iterator) {
				matching[iterator] = -1;
				reverse[iterator] = -1;
			}

			usedVertexes = new boolean[n];
			for (int iterator = 0; iterator < n; ++iterator) {
				int v = weightOfFirstSide[iterator].a;
				for (int j = 0; j < n; ++j) {
					usedVertexes[j] = false;
				}
				Kalgorithm(v);
			}
			
			for (int i = 0; i < n; ++i) {
				if (reverse[i] != -1) {
					//System.out.print((i + 1) + " " + (reverse[i] + 1) + " " + "\n");
					out.write((reverse[i] + 1) + " ");
				} else {
					out.write("0 ");
				}
			}

			in.close();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}