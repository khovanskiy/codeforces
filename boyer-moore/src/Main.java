public class Main {
	static <T> void trace(T suff) {
		System.out.println(suff);
	}

	static void trace(int[] suff) {
		if (suff.length == 0) {
			System.out.println("[]");
			return;
		}
		System.out.print("[");
		for (int i = 0; i < suff.length - 1; ++i) {
			System.out.print(suff[i] + ", ");
		}
		System.out.print(suff[suff.length - 1] + "]\n");
	}

	static void trace(char[] suff) {
		if (suff.length == 0) {
			System.out.println("[]");
			return;
		}
		System.out.print("[");
		for (int i = 0; i < suff.length - 1; ++i) {
			System.out.print(suff[i] + ", ");
		}
		System.out.print(suff[suff.length - 1] + "]\n");
	}

	/*
	 * static int ASIZE = 256;
	 * 
	 * static void preBmBc(String x, int m, int bmBc[]) { int i; for (i = 0; i <
	 * ASIZE; ++i) bmBc[i] = m; for (i = 0; i < m - 1; ++i) bmBc[x.charAt(i)] =
	 * m - i - 1; }
	 * 
	 * static void suffixes(String x, int m, int[] suff) { int f = 0, g, i;
	 * 
	 * suff[m - 1] = m; g = m - 1; for (i = m - 2; i >= 0; --i) { //trace("i");
	 * if (i > g && suff[i + m - 1 - f] < i - g) suff[i] = suff[i + m - 1 - f];
	 * else { if (i < g) g = i; f = i; while (g >= 0 && x.charAt(g) ==
	 * x.charAt(g + m - 1 - f)) { --g; //trace("g"); } suff[i] = f - g; } } }
	 * 
	 * 
	 * static void preBmGs(String x, int m, int bmGs[]) { int[] suff = new
	 * int[x.length()];
	 * 
	 * suffixes(x, m, suff); int[] pi = buildPi(x);
	 * 
	 * trace(x.toCharArray()); trace("Suffix"); trace(suff); trace("Prefix");
	 * trace(pi);
	 * 
	 * for (int i = 0; i < m; ++i) bmGs[i] = m; int j = 0; trace("M " + m); int
	 * kk = 0; for (int i = m - 1; i >= 0; --i) { trace("i = " + i + " " +
	 * suff[i]); ++kk; if (suff[i] == i + 1) { trace("	w"); for (; j < m - 1 -
	 * i; ++j) { trace("	j = " + j); ++kk; if (bmGs[j] == m) { bmGs[j] = m - 1 -
	 * i; } } } } trace("KK = " + kk); trace("bmGs v1"); trace(bmGs); for (int i
	 * = 0; i <= m - 2; ++i) { bmGs[m - 1 - suff[i]] = m - 1 - i; }
	 * trace("bmGs v2"); trace(bmGs); }
	 * 
	 * public static int[] buildPi(String string) { int[] prefixFunction = new
	 * int[string.length()]; prefixFunction[0] = 0;
	 * 
	 * int k = 0; for (int i = 1; i < string.length(); i++) { while (k > 0 &&
	 * string.charAt(k) != string.charAt(i)) { k = prefixFunction[k - 1]; } if
	 * (string.charAt(k) == string.charAt(i)) { ++k; } prefixFunction[i] = k; }
	 * return prefixFunction; }
	 * 
	 * 
	 * static void BM(String x, int m, String y, int n) { int i; int j; int[]
	 * bmGs = new int[x.length()]; int[] bmBc = new int[ASIZE];
	 * 
	 * // Preprocessing preBmGs(x, m, bmGs); preBmBc(x, m, bmBc);
	 * 
	 * // Searching j = 0; while (j <= n - m) { for (i = m - 1; i >= 0 &&
	 * x.charAt(i) == y.charAt(i + j); --i) ; if (i < 0) {
	 * System.out.println(j); j += bmGs[0]; } else j += Math.max(bmGs[i],
	 * bmBc[y.charAt(i + j)] - m + 1 + i); } }
	 */

	/**
	 * Returns the index within this string of the first occurrence of the
	 * specified substring. If it is not a substring, return -1.
	 * 
	 * @param y
	 *            The string to be scanned
	 * @param x
	 *            The target string to search
	 * @return The start index of the substring
	 */
	public static int BM(char[] y, char[] x) {
		if (x.length == 0) {
			return 0;
		}
		
		int charTable[] = preBmBc(x, x.length);
		int offsetTable[] = preBmGs(x, x.length);
		
		for (int i = x.length - 1; i < y.length;) {
			int j = x.length - 1;
			while (x[j] == y[i]) {
				if (j == 0) {
					return i;
				}
				--i;
				--j;
			}
			i += Math.max(offsetTable[x.length - 1 - j], charTable[y[i]]);
		}
		return -1;
	}

	static final int ASIZE = 256;

	/**
	 * Makes the jump table based on the mismatched character information.
	 */
	private static int[] preBmBc(char[] x, int m) {
		int[] table = new int[ASIZE];
		for (int i = 0; i < ASIZE; ++i) {
			table[i] = m;
		}
		for (int i = 0; i < m - 1; ++i) {
			table[x[i]] = m - 1 - i;
		}
		return table;
	}

	/**
	 * Makes the jump table based on the scan offset which mismatch occurs.
	 */
	private static int[] preBmGs(char[] x, int m) {
		int[] table = new int[m];
		int lastPrefixPosition = m;
		for (int i = m - 1; i >= 0; --i) {
			trace("i");
			if (isPrefix(x, m, i + 1)) {
				lastPrefixPosition = i + 1;
			}
			table[m - 1 - i] = lastPrefixPosition - i + m - 1;
		}
		for (int i = 0; i < m - 1; ++i) {
			int slen = suffixLength(x, m, i);
			table[slen] = m - 1 - i + slen;
		}
		return table;
	}

	static int total = 0;
	/**
	 * Is needle[p:end] a prefix of needle?
	 */
	private static boolean isPrefix(char[] x, int m, int p) {
		int j = 0;
		for (int i = p; i < m; ++i) {
			total++;
			if (x[i] != x[j]) {
				return false;
			}
			++j;
		}
		return true;
	}

	/**
	 * Returns the maximum length of the substring ends at p and is a suffix.
	 */
	private static int suffixLength(char[] x, int m, int p) {
		int len = 0;
		int i = p;
		int j = m - 1;
		while (i >= 0 && x[i] == x[j]) {
			len += 1;
			--i;
			--j;
		}
		return len;
	}

	public static void main(String[] args) {
		String y = "abcdeffabcabcg";
		String x = "aaaaaaaaaaa";
		trace(BM(y.toCharArray(), x.toCharArray()));
		trace("Total " + total);
	}
}
