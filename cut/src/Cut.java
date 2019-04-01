import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.StringTokenizer;
import java.util.Vector;

public class Cut
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
	
	static StringTokenizer st;
	static BufferedReader in;
	static BufferedWriter out;
	
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
	
	static long nextLong()
	{
		return Long.parseLong(nextToken());
	}
	
	static void trace(int s)
	{
		System.out.println(s);
	}
	
	static void trace(String s)
	{
		System.out.println(s);
	}
	
	static final long INF = Long.MAX_VALUE;
	
	static boolean bfs(long[][] rGraph, int s, int t, int[] parent, int n)
	{
	    boolean[] visited = new boolean[n];
	    LinkedList<Integer> q = new LinkedList<Integer>();
	    q.push(s);
	    visited[s] = true;
	    parent[s] = -1;
	 
	    while (!q.isEmpty())
	    {
	        int u = q.poll();
	 
	        for (int v = 0; v < n; v++)
	        {
	            if (!visited[v] && rGraph[u][v] > 0)
	            {
	                q.push(v);
	                parent[v] = u;
	                visited[v] = true;
	            }
	        }
	    }
	 
	    return visited[t];
	}
	 
	static void dfs(long[][] rGraph, int s, boolean[] visited, int n)
	{
	    visited[s] = true;
	    for (int i = 0; i < n; ++i)
	    {
	       if (rGraph[s][i] != 0 && !visited[i])
	       {
	           dfs(rGraph, i, visited, n);
	       }
	    }
	}
	
	public static void main(String[] args)
	{
		try
		{
			in = new BufferedReader(new FileReader("cut.in"));
			out = new BufferedWriter(new FileWriter("cut.out"));
			
			int n = nextInt();
			int m = nextInt();
			
			int s = 0;
			int t = n - 1;
			 
		    long[][] temp = new long[n][n];
		    long[][] graph = new long[n][n];
			for (int i = 0; i < m; ++i)
			{
				int a = nextInt() - 1;
				int b = nextInt() - 1;
				long w = nextLong();
				graph[a][b] = w;
				graph[b][a] = w;
				temp[a][b] = w;
				temp[b][a] = w;
		    }
		 
		    int[] parent = new int[n];
		 
		    int u, v;
		    while (bfs(temp, s, t, parent, n))
		    {
		        long flow = INF;
		        for (v = t; v != s; v = parent[v])
		        {
		            u = parent[v];
		            flow = Math.min(flow, temp[u][v]);
		        }
		 
		        for (v = t; v != s; v = parent[v])
		        {
		            u = parent[v];
		            temp[u][v] -= flow;
		            temp[v][u] += flow;
		        }
		    }
		 
		    boolean[] used = new boolean[n];
		    dfs(temp, s, used, n);
		 
		    int count = 0;
		    for (int i = 0; i < n; ++i)
		    {
		    	if (used[i])
		    	{
		    		++count;
		    	}
		    }
		    
		    out.write(count + "\n");
		    for (int i = 0; i < n; ++i)
		    {
		    	if (used[i])
		    	{
		    		out.write((i + 1)+" ");
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