import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.StringTokenizer;
import java.util.Vector;

public class Prefix {
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

	public static int[] buildPi(String string)
	{
		int[] prefixFunction = new int[string.length()];
		prefixFunction[0] = 0;
		
		int k = 0;
		for (int i = 1; i < string.length(); i++)
		{
			while (k > 0 && string.charAt(k) != string.charAt(i))
			{
				k = prefixFunction[k - 1];
			}
			if (string.charAt(k) == string.charAt(i))
			{
				++k;
			}
			prefixFunction[i] = k;
		}
		return prefixFunction;
	}

	public static void main(String[] args) {
		try {
			in = new BufferedReader(new FileReader("prefix.in"));
			out = new BufferedWriter(new FileWriter("prefix.out"));

			String stringData = nextToken();

			int[] arrayOfPrefixFunction = buildPi(stringData);
			
			for (int index = 0; index < arrayOfPrefixFunction.length; ++index)
			{
				out.write(arrayOfPrefixFunction[index]+" ");
			}

			in.close();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}