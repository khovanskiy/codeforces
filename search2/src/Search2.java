import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.StringTokenizer;
import java.util.Vector;

public class Search2 {
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

	public static int[] buildPi(String s)
	{
		int[] prefix = new int[s.length()];
		prefix[0] = 0;
		
		int k = 0;
		for (int i = 1; i < s.length(); i++)
		{
			while (k > 0 && s.charAt(k) != s.charAt(i))
			{
				k = prefix[k - 1];
			}
			if (s.charAt(k) == s.charAt(i))
			{
				++k;
			}
			prefix[i] = k;
		}
		return prefix;
	}

	public static void main(String[] args) {
		try {
			in = new BufferedReader(new FileReader("search2.in"));
			out = new BufferedWriter(new FileWriter("search2.out"));

			String pattern = nextToken();
			String s = nextToken();

			if (pattern.length() > s.length()) {
				out.write("0\n");
			} else {
				Vector<Integer> matches = new Vector<Integer>();

				StringBuilder sb = new StringBuilder();
				sb.append(pattern);
				sb.append("#");
				sb.append(s);
				
				String r = sb.toString();
				int[] pi = buildPi(r);
				int t = s.length();
				int p = pattern.length();

				for (int i = 0; i < t; ++i)
				{
					if (pi[p + i + 1] == p)
					{
						matches.add(i + 1 - p);
					}
				}
				
				out.write(matches.size() + "\n");
				for (int i = 0; i < matches.size(); ++i) {
					out.write((matches.get(i) + 1) + " ");
				}
			}

			in.close();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}