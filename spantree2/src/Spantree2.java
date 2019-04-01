import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.Vector;

public class Spantree2
{
	static class Node
	{
		public int u;
		public long cost;
		public Node(int u, long cost)
		{
			this.u = u;
			this.cost = cost;
		}
	}
	
	static class Edge implements Comparable<Edge>
	{
		public int u;
		public int v;
		public long cost;
		public Edge(int u, int v, long cost)
		{
			this.u = u;
			this.v = v;
			this.cost = cost;
		}
		
		@Override
		public int compareTo(Edge other)
		{
			if (this.cost < other.cost)
			{
				return -1;
			}
			else if (this.cost > other.cost)
			{
				return 1;
			}
			return 0;
		}
	}
	
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
	
	static class DSU
	{
		int[] parent;
		int[] rank;
		public DSU(int size)
		{
			parent = new int[size];
			rank = new int[size];
		}
		
		void make(int v)
		{
			parent[v] = v;
			rank[v] = 0;
		}
		 
		int get(int v)
		{
			if (v == parent[v])
			{
				return v;
			}
			return parent[v] = get (parent[v]);
		}
		 
		void union(int a, int b)
		{
			a = get(a);
			b = get(b);
			if (a != b)
			{
				if (rank[a] < rank[b])
				{
					int temp = a;
					a = b;
					b = temp;
				}
				parent[b] = a;
				if (rank[a] == rank[b])
				{
					++rank[a];
				}
			}
		}
	}
	
	static StringTokenizer st;
	static BufferedReader in;
	static BufferedWriter out;
	static Vector<Edge> edges;
	
	public static void main(String[] args)
	{
		try
		{
			in = new BufferedReader(new FileReader("spantree2.in"));
			out = new BufferedWriter(new FileWriter("spantree2.out"));
			int n = nextInt();
			int m = nextInt();
			
			DSU dsu = new DSU(n);
			for (int i = 0; i < n; ++i)
			{
				dsu.make(i);
			}
			
			edges = new Vector<Edge>(m);
			for (int i = 0; i < m; ++i)
			{
				int a = nextInt() - 1;
				int b = nextInt() - 1;
				int c = nextInt();
				edges.add(new Edge(a, b, c));
			}
			
			Collections.sort(edges);
			
			int sum = 0;
			for (int i = 0; i < edges.size(); ++i)
			{
				Edge current = edges.get(i);
				if (dsu.get(current.u) != dsu.get(current.v))
				{
					sum += current.cost;
					dsu.union(current.u, current.v);
				}
			}
			
			out.write(sum+"");
			
			in.close();
			out.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}