import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.StringTokenizer;

public class Z {
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

	public static int[] buildZ(String p) {
		int n = p.length();
		int[] zfunctionResult = new int[n];
		zfunctionResult[0] = 0;
		int leftBound = 0;
		int rightBound = 0;
		for (int i = 1; i < n; ++i) {
			if (i > rightBound) {
				int j = 0;
				while (i + j < n && p.charAt(i + j) == p.charAt(j)) {
					j++;
				}
				zfunctionResult[i] = j;
				leftBound = i;
				rightBound = i + j - 1;
			} else if (zfunctionResult[i - leftBound] < rightBound - i + 1) {
				zfunctionResult[i] = zfunctionResult[i - leftBound];
			} else {
				int j = 1;
				while (j + rightBound < n
						&& p.charAt(j + rightBound - i) == p.charAt(rightBound
								+ j)) {
					j++;
				}
				zfunctionResult[i] = rightBound + j - i;
				leftBound = i;
				rightBound = rightBound + j - 1;
			}
		}
		return zfunctionResult;
	}

	public static void main(String[] args) {
		try {
			in = new BufferedReader(new FileReader("z.in"));
			out = new BufferedWriter(new FileWriter("z.out"));

			String stringData = nextToken();

			int[] arrayOfZFunction = buildZ(stringData);

			for (int index = 1; index < arrayOfZFunction.length; ++index) {
				out.write(arrayOfZFunction[index] + " ");
			}

			in.close();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}