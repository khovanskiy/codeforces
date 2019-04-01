package ru.ifmo.ctddev.khovanskiy.task2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.NavigableSet;
import java.util.Random;
import java.util.TreeSet;

public class Main {
	
	public static String testEqual2(Object a, Object b)
	{
		if (a == null)
		{
			if (b == null)
			{
				return "true";
			}
			else
			{
				return "false "+a+" "+b;
			}
		}
		if (b == null)
		{
			return "false "+a+" "+b;
		}
		if (a.equals(b))
		{
			return "true";
		}
		else
		{
			return "false "+a+" "+b;
		}
	}
	
	public static boolean testEqual(Object a, Object b)
	{
		if (a == null)
		{
			return b == null;
		}
		if (b == null)
		{
			return false;
		}
		return a.equals(b);
	}
	
	public static void test(int count)
	{
		Random random = new Random();
		//random.
		int MAX_VALUE = 100;
		int MAX_LENGHT = 100;
		for (int i = 0; i < count; ++i)
		{
			int n = random.nextInt(Integer.MAX_VALUE) % (MAX_LENGHT - 1) + 1;
			//Console.print(n);
			
			List<Integer> list = new ArrayList<Integer>(n);
			for (int j = 0; j < n; ++j)
			{
				list.add(random.nextInt(Integer.MAX_VALUE) % MAX_VALUE);
			}
			NavigableSet<Integer> b = new TreeSet<Integer>(list);
			NavigableSet<Integer> a = new ArraySet<Integer>(list);
			
			
			for (int j = 0; j < 100; ++j)
			{
				int p = random.nextInt(Integer.MAX_VALUE) % n;
				if (!testEqual(a.lower(p), b.lower(p)))
				{
					Console.print("TEST FAILED lower!");
					Console.print(list);
					Console.print(a);
					Console.print(b);
				}
				if (!testEqual(a.floor(p), b.floor(p)))
				{
					Console.print("TEST FAILED floor!");
				}
				if (!testEqual(a.ceiling(p), b.ceiling(p)))
				{
					Console.print("TEST FAILED ceiling!");
				}
				if (!testEqual(a.higher(p), b.higher(p)))
				{
					Console.print("TEST FAILED higher!");
				}
				if (a.headSet(p).toArray().equals(b.headSet(p).toArray()))
				{
					Console.print("TEST FAILED headSet!");
				}
				if (a.tailSet(p).toArray().equals(b.tailSet(p).toArray()))
				{
					Console.print("TEST FAILED tailSet!");	
				}
				int p2 = random.nextInt(Integer.MAX_VALUE) % n + p;
				if (a.subSet(p, p2).toArray().equals(b.subSet(p, p2).toArray()))
				{
					Console.print("TEST FAILED subSet!");	
				}
			}
		}
	}
	public static void main(String[] args)
	{
		try
		{
			test(10);
		}
		catch (Exception e)
		{
			Console.print(e);
			e.printStackTrace();
		}
		List<Integer> list = Arrays.asList(10, 20, -10, 40, 90);
		NavigableSet<Integer> a = new ArraySet<Integer>(list);
		NavigableSet<Integer> b = new ArraySet<Integer>(list);
		NavigableSet<Integer> c = new ArraySet<Integer>(Arrays.asList(3, 5, 10, 7, 21, 8, 1));
		
		/*Console.print("Elements");
		Console.print(a);
		Console.print("Iterator");
		Iterator it = a.iterator(); 
		while (it.hasNext())
		{
			Console.print(it.next());
		}
		Console.print("Tests");
		Console.print(a.first() == -10);
		Console.print(a.last() == 90);
		Console.print(!list.isEmpty());
		Console.print(a.size() == list.size());
		Console.print(a.equals(b));
		Console.print(!a.equals(c));
		Console.print(a.hashCode() == b.hashCode());
		Console.print(a.hashCode() != c.hashCode());
		Console.print("Nagiable");
		Console.print("Ceiling");
		Console.print(testEqual(a.ceiling(-1000), new Integer(-10)));
		Console.print(testEqual(a.ceiling(10), new Integer(10)));
		Console.print(testEqual(a.ceiling(25), new Integer(40)));
		Console.print(testEqual(a.ceiling(90), new Integer(90)));
		Console.print(testEqual(a.ceiling(1000), null));
		Console.print("Higher");
		Console.print(testEqual(a.higher(-1000), new Integer(-10)));
		Console.print(testEqual(a.higher(10), new Integer(20)));
		Console.print(testEqual(a.higher(20), new Integer(40)));
		Console.print(testEqual(a.higher(46), new Integer(90)));
		Console.print(testEqual(a.higher(90), null));
		Console.print(testEqual(a.higher(1000), null));
		Console.print("Floor");
		Console.print(testEqual(a.floor(-1000), null));
		Console.print(testEqual(a.floor(9), new Integer(-10)));
		Console.print(testEqual(a.floor(10), new Integer(10)));
		Console.print(testEqual(a.floor(20), new Integer(20)));
		Console.print(testEqual(a.floor(19), new Integer(10)));
		Console.print(testEqual(a.floor(46), new Integer(40)));
		Console.print(testEqual(a.floor(90), new Integer(90)));
		Console.print(testEqual(a.floor(1000), new Integer(90)));
		
		Console.print("Lower");
		Console.print(testEqual(a.lower(-1000), null));
		Console.print(testEqual(a.lower(10), new Integer(-10)));
		Console.print(testEqual(a.lower(19), new Integer(10)));
		Console.print(testEqual(a.lower(20), new Integer(10)));
		Console.print(testEqual(a.lower(46), new Integer(40)));
		Console.print(testEqual(a.lower(90), new Integer(40)));
		Console.print(testEqual(a.lower(1000), new Integer(90)));
		
		Console.print("Descending");
		NavigableSet<Integer> d = a.descendingSet();
		Console.print("Iterator");
		Iterator it2 = d.iterator(); 
		while (it2.hasNext())
		{
			Console.print(it2.next());
		}
		
		Console.print(!a.equals(d));
		
		Console.print("Ceiling");
		Console.print(testEqual(d.ceiling(-1000), null));
		Console.print(testEqual(d.ceiling(10), new Integer(10)));
		Console.print(testEqual(d.ceiling(25), new Integer(20)));
		Console.print(testEqual(d.ceiling(90), new Integer(90)));
		Console.print(testEqual(d.ceiling(1000), new Integer(90)));
		Console.print("Higher");
		Console.print(testEqual(d.higher(-1000), null));
		Console.print(testEqual(d.higher(-10), null));
		Console.print(testEqual(d.higher(10), new Integer(-10)));
		Console.print(testEqual(d.higher(19), new Integer(10)));
		Console.print(testEqual(d.higher(20), new Integer(10)));
		Console.print(testEqual(d.higher(46), new Integer(40)));
		Console.print(testEqual(d.higher(90), new Integer(40)));
		Console.print(testEqual(d.higher(1000), new Integer(90)));
		Console.print(a.headSet(96, true).tailSet(10, false));
		Console.print(a.subSet(10, false, 96, true));
		//Console.print(a.floor(5));
		//Console.print(a.lower(30));
		//Console.print(a.higher(30));
		//Console.print(a.higher(90));
		//Console.print(new Integer(5).compareTo(new Integer(4)));*/
		
	}

}
