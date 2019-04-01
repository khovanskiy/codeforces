
public class Console
{
	public static <T> void print(T s)
	{
		System.out.println(s);
	}
	
	public static <T> void trace(char[] buffer)
	{
		trace(buffer, buffer.length);
	}
	
	public static <T> void trace(char[] buffer, int size)
	{
		if (size == 0)
		{
			print("[]");
			return;
		}
		System.out.print("[");
		for (int i = 0; i < size - 1; ++i)
		{
			System.out.print(buffer[i] + ",");
		}
		System.out.print(buffer[size - 1] + "]\n");
	}
	public static <T> void trace(int[] buffer)
	{
		System.out.print("[");
		for (int i = 0; i < buffer.length - 1; ++i)
		{
			System.out.print(buffer[i] + ",");
		}
		System.out.print(buffer[buffer.length - 1] + "]");
	}
}
