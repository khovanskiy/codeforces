import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Problem4 {
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

	static StringTokenizer st;
	static BufferedReader in;
	static BufferedWriter out;

	public static void main(String[] args) {
		try {
			in = new BufferedReader(new FileReader("problem4.in"));
			out = new BufferedWriter(new FileWriter("problem4.out"));

			int n = nextInt();
			int m = nextInt();
			int k = nextInt();
			int l = nextInt();

			long[][] graph = new long[n][n];
			ArrayList<Integer> terminals = new ArrayList<Integer>(n);

			for (int i = 0; i < k; ++i) {
				int a = nextInt() - 1;
				terminals.add(a);
			}

			for (int i = 0; i < m; ++i) {
				int a = nextInt() - 1;
				int b = nextInt() - 1;
				nextToken();
				graph[a][b] += 1;
			}

			long[][] result = pow(n, graph, l, PMOD);

			long sum = 0;
			for (int i = 0; i < k; ++i) {
				sum = ((sum % PMOD) + (result[0][terminals.get(i)] % PMOD)) % PMOD;
			}

			out.write(sum + "\n");

			in.close();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}