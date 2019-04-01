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

	static int[] buildShift(int[] kmin, int[] rmin, int[] h, int nd, int m) {
		int[] shift = new int[m + 1];
		for (int i = 0; i <= nd; ++i)
			shift[i] = kmin[h[i]];
		for (int i = nd + 1; i < m; ++i)
			shift[i] = rmin[h[i]];
		shift[m] = rmin[0];
		return shift;
	}

	static int[] buildNext(int[] kmin, int[] rmin, int[] h, int nd, int m) {
		int[] nhd0 = new int[m];
		int s = 0;
		for (int i = 0; i < m; ++i) {
			nhd0[i] = s;
			if (kmin[i] > 0)
				++s;
		}

		/* Computation of next */
		int[] next = new int[m + 1];
		for (int i = 0; i <= nd; ++i)
			next[i] = nhd0[h[i] - kmin[h[i]]];
		for (int i = nd + 1; i < m; ++i)
			next[i] = nhd0[m - rmin[h[i]]];
		next[m] = nhd0[m - rmin[h[m - 1]]];
		return next;
	}

	static void COLUSSI(char[] x, int m, char[] y, int n) {

		// Processing
		int[] hmax = buildHmax(x, m);
		int[] kmin = buildKmin(hmax, m);
		int[] rmin = buildRmin(hmax, kmin, m);

		// Построение массива h
		int[] h = new int[m];
		int s = -1;
		int r = m;
		for (int i = 0; i < m; ++i)
			if (kmin[i] == 0)
				h[--r] = i;
			else
				h[++s] = i;
		int nd = s;

		int[] shift = buildShift(kmin, rmin, h, nd, m);
		int[] next = buildNext(kmin, rmin, h, nd, m);

		// Searching
		int i = 0;
		int j = 0;
		int last = -1;
		while (j <= n - m) {
			while (i < m && last < j + h[i] && x[h[i]] == y[j + h[i]])
				i++;
			if (i >= m || last >= j + h[i]) {
				System.out.println(j);
				i = m;
			}
			if (i > nd)
				last = j + m - 1;
			j += shift[i];
			i = next[i];
		}
	}

	static int[] buildRmin(int[] hmax, int[] kmin, int m) {
		int[] rmin = new int[m];
		int r = 0;
		for (int i = m - 1; i >= 0; --i) {
			if (hmax[i + 1] == m)
				r = i + 1;
			if (kmin[i] == 0)
				rmin[i] = r;
			else
				rmin[i] = 0;
		}
		return rmin;
	}

	static int[] buildKmin(int[] hmax, int m) {
		int[] kmin = new int[m];
		for (int i = m; i >= 1; --i) {
			if (hmax[i] < m)
				kmin[hmax[i]] = i;
		}
		return kmin;
	}

	static int[] buildHmax(char[] x, int m) {
		int hmax[] = new int[m + 1];
		/*
		 * for (int k = 1; k <= m; ++k) { int i = k; while (x[i] == x[i - k]) {
		 * i++; } hmax[k] = i; }
		 */
		int i = 1;
		int k = 1;
		while (k <= m) {
			while (x[i] == x[i - k])
				i++;
			hmax[k] = i;
			int q = k + 1;
			while (hmax[q - k] + k < i) {
				hmax[q] = hmax[q - k] + k;
				q++;
			}
			k = q;
			if (k == i + 1)
				i = k;
		}
		trace(hmax);
		return hmax;
	}

	public static void main(String[] args) {
		String y = "abcdffabc";
		String x = "fa#";
		COLUSSI(x.toCharArray(), x.length() - 1, y.toCharArray(), y.length());
	}
}
