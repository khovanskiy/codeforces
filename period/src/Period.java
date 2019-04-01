import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.StringTokenizer;
import java.util.Vector;

public class Period {
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
	
	static <T> void trace(T s) {
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
	
	public static int getPeriod(String origin)
	{
		String s = origin + origin;
		
		int[] arr = buildPi(s);
		/*trace(origin);
		trace(s);
		for (int i = 0; i < arr.length; ++i)
		{
			System.out.print(arr[i]+" ");
		}*/
		//trace("");
		int max = -1;
		int p = 0;
		for (int i = 0; i < arr.length; ++i)
		{
			if (arr[i] > max)
			{
				max = arr[i];
				p = i;
				if (max >= origin.length())
				{
					break;
				}
			}
		}
		
		int res = p - max + 1;

		return res;
	}

	public static void main(String[] args) {
		try {
			in = new BufferedReader(new FileReader("period.in"));
			out = new BufferedWriter(new FileWriter("period.out"));

			String origin = nextToken();

			int p = getPeriod(origin);
			out.write(p+"\n");

			in.close();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}