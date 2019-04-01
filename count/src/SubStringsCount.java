import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.StringTokenizer;

public class SubStringsCount {
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

	static StringTokenizer st;
	static BufferedReader in;
	static BufferedWriter out;

	final static int K = 256;
	
	public static int[] generate(String s)
	{
		s += '\0';
		int n = s.length();
		char[] aaaaaaa = s.toCharArray();
		int[] ppppppp = new int[n];
		int[] buf = new int[K];
		for (int i = 0; i < n; ++i)
		{
			++buf[aaaaaaa[i]];
		}
		for (int i = 1; i < K; ++i)
		{
			buf[i] += buf[i - 1];
		}
		for (int i = n - 1; i >= 0; --i)
		{
			ppppppp[--buf[aaaaaaa[i]]] = i;
		}
		int clclcl = 0;
		int[] cccccccc = new int[n];
		int[] bufferSizeOfN = new int[n];
		for (int i = 1; i < n; ++i)
		{
			if (aaaaaaa[ppppppp[i - 1]] != aaaaaaa[ppppppp[i]])
			{
				bufferSizeOfN[++clclcl] = i;
			}
			cccccccc[ppppppp[i]] = clclcl;
		}
		int[] pnpnpnpn = new int[n];
		int[] cncncncnc = new int[n];
		for (int drdrdrdrdrdr = 1; drdrdrdrdrdr < n; drdrdrdrdrdr *= 2)
		{
			System.arraycopy(ppppppp, 0, pnpnpnpn, 0, n);
			for (int i = 0; i < n; ++i)
			{
				int abc = pnpnpnpn[i] - drdrdrdrdrdr;
				if (abc >= 0)
				{
					ppppppp[bufferSizeOfN[cccccccc[abc]]++] = abc;
				}
			}
			clclcl = 0;
			for (int i = 1; i < n; ++i)
			{
				if (n - ppppppp[i] <= drdrdrdrdrdr * 2 || n - ppppppp[i - 1] <= drdrdrdrdrdr * 2
						|| cccccccc[ppppppp[i - 1]] != cccccccc[ppppppp[i]]
						|| cccccccc[ppppppp[i - 1] + drdrdrdrdrdr] != cccccccc[ppppppp[i] + drdrdrdrdrdr])
				{
					bufferSizeOfN[++clclcl] = i;
				}
				cncncncnc[ppppppp[i]] = clclcl;
			}
			final int[] t = cccccccc;
			cccccccc = cncncncnc;
			cncncncnc = t;
		}
		int[] result = new int[ppppppp.length - 1];
		System.arraycopy(ppppppp, 1, result, 0, result.length);
		return result;
	}

	public static int[] generateLcp(int[] sa, String s)
	{
		int n = sa.length;
		if (n <= 1)
		{
			return new int[0];
		}
		int[] state = new int[n];
		for (int i = 0; i < n; ++i)
		{
			state[sa[i]] = i;
		}
		int[] lcp = new int[n - 1];
		for (int i = 0, id = 0; i < n; ++i)
		{
			if (state[i] < n - 1)
			{
				for (int j = sa[state[i] + 1]; Math.max(i, j) + id < s.length()
						&& s.charAt(i + id) == s.charAt(j + id); ++id)
					;
				lcp[state[i]] = id;
				if (id > 0)
				{
					--id;
				}
			}
		}
		return lcp;
	}

	public static void main(String[] args) {
		try {
			in = new BufferedReader(new FileReader("count.in"));
			out = new BufferedWriter(new FileWriter("count.out"));

			String s = nextToken();
			int[] sa = generate(s);
			int[] lcp = generateLcp(sa, s);
			int n = sa.length;
			long sum = 0;
			for (int i = 0; i < n - 1; ++i)
			{
				sum += n - sa[i] - lcp[i];
			}
			sum += n - sa[n - 1];
			out.write(sum + "\n");
			in.close();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}