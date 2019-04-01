import java.util.Vector;


public class ExpressionParser
{
	
	private static boolean inArray(String[] arr, String s)
	{
		if (arr == null)
		{
			return false;
		}
		for (int i = 0; i < arr.length; ++i)
		{
			if (arr[i].equals(s))
			{
				return true;
			}
		}
		return false;
	}
	
	public static Expression parse(String token)
	{
		return parse(token, 0, 0);
	}
	
	/*public static void parse(String token)
	{
		
	}*/
	
	
	
	public void attach(OperationType op, int level)
	{
		
	}
	
	public static Expression parse(String token, int level, int k)
	{
		if (token.charAt(0) == '(' && token.charAt(token.length() - 1) == ')')
		{
			token = token.substring(1, token.length() - 1);
			level = 0;
		}
		level %= 4;
		Console.print(h(k)+"Level "+level+" \""+token+"\"");
		OperationType[] deliStrings = null;
		switch (level)
		{
			case 0: deliStrings = new OperationType[]{OperationType.IMPLICATION}; break;
			case 1: deliStrings = new OperationType[]{OperationType.DISJUNCTION}; break;
			case 2: deliStrings = new OperationType[]{OperationType.CONJUNCTION}; break;
			case 3: deliStrings = new OperationType[]{OperationType.NEGATION}; break;
		}
		Vector<Token> tokens = smartSplit(token, deliStrings);	
		if (tokens.size() == 1 && level == 3 && tokens.get(0).type == OperationType.EMPTY)
		{
			Console.print(h(k)+"Variable");
			return new Variable(token);
		}
		Expression current = null;
		for (int i = 0; i < tokens.size(); ++i)
		{	
			//Console.print(h(k)+tokens.get(i));
			Expression  p = parse(tokens.get(i).body, level + 1, k+1);
			//Console.print(h(k)+p);
			switch (tokens.get(i).type)
			{
				case EMPTY:
				{
					if (current == null)
					{
						current = p;
					}
				} break;
				case IMPLICATION:
				{
					if (current instanceof BinaryOperation)
					{	
						((BinaryOperation) current).right = new ImplicationOperation(((BinaryOperation) current).right, p);
					}
					else
					{
						current = new ImplicationOperation(current, p);
					}
				} break;
				case DISJUNCTION:
				{
					current = new DisjunctionOperation(current, p);
				} break;
				case CONJUNCTION:
				{
					current = new ConjunctionOperation(current, p);
				} break;
				case NEGATION:
				{
					current = new NegationOperation(p);
				}
			}
			//Console.print(h(k)+" Current = " + current);
		}
		return current;
	}
	
	public static String h(int k)
	{
		String temp = "";
		for (int i = 0; i < k; i++)
		{
			temp += "    ";
		}
		return temp;
	}
	
	public static OperationType check(OperationType[] patterns, StringBuilder buffer)
	{
		int count = 0;
		for (int i = 0; i < patterns.length; ++i)
		{
			String pattern = patterns[i].getDelimeter();
			//Console.print("Compare " + pattern + " vs " + buffer.toString());
			
			int p = buffer.length() - 1;
			if (pattern.length() != 0 && p < 0)
			{
				continue;
			}
			++count;
			for (int j = pattern.length() - 1; j >= 0; --j)
			{
				if (pattern.charAt(j) != buffer.charAt(p))
				{
					--count;
					break;
				}
				--p;
				if (p < 0 && j > 0)
				{
					--count;
					break;
				}
			}
			if (count > 0)
			{
				return patterns[i];
			}
		}
		return OperationType.EMPTY;
	}
	
	static class Token
	{
		public String body;
		public OperationType type;
		public Token(String body, OperationType type)
		{
			this.body = body;
			this.type = type;
		}
		@Override
		public String toString()
		{
			return "{"+body+", "+type+"}";
		}
	}
	
	public static Vector<Token> smartSplit(String token, OperationType[] operations)
	{
		Vector<Token> result = new Vector<Token>();
		StringBuilder buffer = new StringBuilder();
		int depth = 0;
		OperationType last = OperationType.EMPTY;
		for (int i = 0; i < token.length(); ++i)
		{
			char c = token.charAt(i);
			//Console.print("Read c = "+c);
			buffer.append(c);
			if (c == '(')
			{
				++depth;
			}
			else if (c == ')')
			{
				--depth;
			}
			else
			{
				if (depth == 0)
				{
					OperationType p = check(operations, buffer);
					//Console.print("Check "+buffer.toString()+" vs "+operations[0].getDelimeter());
					if (p != OperationType.EMPTY)
					{
						//Console.print("OK");
						buffer.setLength(buffer.length() - p.getDelimeter().length());
						if (buffer.length() > 0)
						{
							//Console.print("Put "+buffer.toString());
							result.add(new Token(buffer.toString(), last));
						}
						last = p;
						buffer.setLength(0);
					}
				}
			}
		}
		if (buffer.length() > 0)
		{
			Console.print("LAST Put "+buffer.toString()+" "+last);
			result.add(new Token(buffer.toString(), last));
		}
		return result;
	}
	
	/*public static void split(String token)
	{
		int depth = 0;
		char[] buffer = new char[token.length()];
		Console.print("Buffer size = " + buffer.length);
		int b = 0;
		int start = 0;
		int last = -1;
		Vector<String> v = new Vector<String>();
		for (int i = 0; i < token.length(); ++i)
		{
			char c = token.charAt(i);
			Console.print("Read symbol = " + c);
			if (c == '(')
			{
				++depth;	
			}
			else if (c == ')')
			{
				--depth;
				Console.trace(buffer, b);
				b = 0;
			}
			else
			{
				buffer[b] = c;
				b++;
				String[] array = new String[]{"->", "&","!"};
				for (int j = 0; j < array.length; ++j)
				{
					if (compare(array[j], buffer, b - 1))
					{
						Console.print("Split on depth = " + depth);
						b -= array[j].length();
						Console.trace(buffer, b);
						b = 0;
						break;
					}
				}
			}
		}
		for (int i = 0; i < v.size(); ++i)
		{
			Console.print(" V "+v.get(i));
		}
	}*/

	/*public Vector<String> split(String token, char[] delimetres, char[] ignoring)
	{
		Vector<String> result = new Vector<String>();
		StringBuilder buffer = new StringBuilder();
		int depth = 0;
		for (int i = 0; i < token.length(); ++i)
		{
			char c = token.charAt(i);
			if (inArray(ignoring, c))
			{
				continue;
			}
			if (depth == 0 && inArray(delimetres, c))
			{
				result.add(buffer.toString());
				buffer.setLength(0);
			}
			else
			{
				if (c == '(')
				{
					++depth;
				}
				else if (c == ')')
				{
					--depth;
				}
				else
				{
					buffer.append(c);
				}
			}
		}
		return result;
	}*/
}
