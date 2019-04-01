import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;

public class Program
{
	static BufferedReader in;
	static BufferedWriter out;
	
	public static void main(String[] args)
	{
		try
		{
			in = new BufferedReader(new FileReader("a.in"));
			out = new BufferedWriter(new FileWriter("a.out"));
			
			Expression p = ExpressionParser.parse("(A->B)->C");
			Console.print(p.toString()+" "+(p instanceof Variable));
			
			while (in.ready())
			{
				String token = in.readLine();
				//Console.print(token + " " + ExpressionParser.parse(token));
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
