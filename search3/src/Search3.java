import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.StringTokenizer;
import java.util.Vector;

public class Search3 {
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

	static <T> void trace(T s) {
		System.out.println(s);
	}
	
	static void trace(int[] s) {
		if (s.length == 0)
		{
			trace("[]");
			return;
		}
		System.out.print("[");
		for (int i = 0; i < s.length - 1; ++i)
		{
			System.out.print(s[i]+", ");
		}
		System.out.print(s[s.length - 1] +  "]\n");
	}

	static void trace(String s) {
		System.out.println(s);
	}

	static StringTokenizer st;
	static BufferedReader in;
	static BufferedWriter out;
	
	public static int[] buildZ(char[] p) {
		int n = p.length;
		int[] zfunctionResult = new int[n];
		zfunctionResult[0] = 0;
		int leftBound = 0;
		int rightBound = 0;
		for (int i = 1; i < n; ++i) {
			if (i > rightBound) {
				int j = 0;
				while (i + j < n && p[i + j] == p[j]) {
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
						&& p[j + rightBound - i] == p[rightBound
								+ j]) {
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
			in = new BufferedReader(new FileReader("search3.in"));
			out = new BufferedWriter(new FileWriter("search3.out"));

			String pattern = nextToken();
			String source = nextToken();

			int p = pattern.length();
			int s = source.length();
			char[] t1 = new char[p + s + 1];
			char[] t2 = new char[p + s + 1];
			
			for (int i = 0; i < t1.length;)
			{
				for (int j = 0; j < p; ++j)
				{
					t1[i] = pattern.charAt(j);
					++i;
				}
				t1[i++] = '#';
				for (int j = 0; j < s; ++j)
				{
					t1[i] = source.charAt(j);
					++i;
				}
			}
			
			for (int i = 0; i < t2.length;)
			{
				for (int j = p - 1; j >= 0; --j)
				{
					t2[i] = pattern.charAt(j);
					++i;
				}
				t2[i++] = '#';
				for (int j = s - 1; j >= 0; --j)
				{
					t2[i] = source.charAt(j);
					++i;
				}
			}
			
			int[] z1 = buildZ(t1);
			int[] z2 = buildZ(t2);
			//trace(z1);
			//trace(z2);
			
			/*int[] r = new int[source.length()];
			for (int i = 0; i < r.length; ++i)
			{
				r[i] = z2[z2.length - i - 1];
			}
			
			trace(r);*/
			
			Vector<Integer> matches = new Vector<Integer>();
			
			for (int i = 0; i + p - 1 < s; ++i)
			{
				int a = z1[p + i + 1];
				int b = z2[s - i + 1];
				//trace(a + " " + b);
				if (a + b >= pattern.length() - 1)
				{
					//trace("Found = " + (i));
					matches.add(i);
				}
			}
			
			out.write(matches.size()+"\n");
			for (int i = 0; i < matches.size(); ++i)
			{
				out.write((matches.get(i) + 1)+" ");
			}
			
			in.close();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}