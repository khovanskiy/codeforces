import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Arrays;
import java.util.StringTokenizer;
import java.util.Vector;

public class Search1
{
	static String nextToken()
	{
		while (st == null || !st.hasMoreTokens())
		{
			try
			{
				st = new StringTokenizer(in.readLine());
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		return st.nextToken();
	}
	
	static int nextInt()
	{
		return Integer.parseInt(nextToken());
	}
	
	static void trace(int s)
	{
		System.out.println(s);
	}
	
	static void trace(String s)
	{
		System.out.println(s);
	}

	static StringTokenizer st;
	static BufferedReader in;
	static BufferedWriter out;
	
	public static void main(String[] args)
	{
		try
		{
			in = new BufferedReader(new FileReader("search1.in"));
			out = new BufferedWriter(new FileWriter("search1.out"));
			
			String p = nextToken();
			String t = nextToken();
			
			if (p.length() > t.length())
			{
				out.write("0\n");
			}
			else
			{
				Vector<Integer> matches = new Vector<Integer>();
				for (int i = 0; i < t.length() - p.length() + 1; ++i)
				{
					boolean found = true;
					for (int j = 0; j < p.length(); ++j)
					{
						if (t.charAt(i + j) != p.charAt(j))
						{
							found = false;
							break;
						}
					}
					if (found)
					{
						matches.add(i);
					}
				}
				out.write(matches.size()+"\n");
				for (int i = 0; i < matches.size(); ++i)
				{
					out.write((matches.get(i) + 1) + " ");
				}
			}
			in.close();
			out.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}