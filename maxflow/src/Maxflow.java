import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Arrays;
import java.util.StringTokenizer;
import java.util.Vector;

public class Maxflow
{
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
	
	static void trace(int s)
	{
		System.out.println(s);
	}
	
	static void trace(String s)
	{
		System.out.println(s);
	}

	static class Edge
	{
		public int a;
		public int b;
		public int cap;
		public int flow;
		
		public Edge(int a, int b, int cap, int flow)
		{
			this.a = a;
			this.b = b;
			this.cap = cap;
			this.flow = flow;
		}
	}
	
	static int n;
	static int c[][];
	static int f[][];
	static int source;
	static int target;
	static int dist[];
	static int path[];
	static int q[];
	
	static final int INF = Integer.MAX_VALUE;
	
	static boolean bfs()
	{
		int ci = 0;
		int cm = 0;
		
		q[cm++] = source;
		
		for (int i = 0; i < n; ++i)
		{
			dist[i] = -1;
		}
		dist[source] = 0;
		
		while (ci < cm)
		{
			int v = q[ci++];
			for (int to = 0; to < n; ++to)
			{
				if (dist[to] == -1 && f[v][to] + curf < c[v][to])
				{
					q[cm++] = to;
					dist[to] = dist[v] + 1;
				}
			}
		}
		return dist[target] != -1;
	}
	 
	static int dfs (int v, int flow)
	{
		if (flow == 0)
		{
			return 0;
		}
		if (v == target)
		{
			return flow;
		}
		for (int to = path[v]; to < n; ++to) 
		{
			if (dist[to] != dist[v] + 1)
			{
				continue;
			}
			
			int pushed = dfs(to, Math.min (flow, c[v][to] - f[v][to] - curf));
			if (pushed != 0) 
			{
				f[v][to] += pushed;
				f[to][v] -= pushed;
				return pushed;
			}
		}
		return 0;
	}
	
	static int findMaxFlow()
	{
		int flow = 0;
		while (bfs())
		{	
			for (int i = 0; i < n; ++i)
			{
				path[i] = 0;
			}
			
			int pushed = dfs(source, INF);
			while (pushed != 0)
			{
				//System.out.println(pushed);
				flow += pushed;
				pushed = dfs(source, INF);
			}
		}
		return flow;
	}
	
	public static void main(String[] args)
	{
		try
		{
			in = new BufferedReader(new FileReader("maxflow.in"));
			out = new BufferedWriter(new FileWriter("maxflow.out"));
			n = nextInt();
			int m = nextInt();
			
			c = new int[n][n];
			f = new int[n][n];
			dist = new int[n];
			path = new int[n];
			q = new int[n];
			source = 0;
			target = n - 1;
			
			for (int i = 0; i < m; ++i)
			{
				int a = nextInt() - 1;
				int b = nextInt() - 1;
				int cap = nextInt();
				c[a][b] = cap;
			}
			
			
			
			
			out.write(findMaxFlow() + "");
			
			for (int i = 0; i < n; ++i)
			{
				for (int j = 0; j < n; ++j)
				{
					if (f[i][j] > 0)
					{
						System.out.print((i + 1) + " -> " + (j + 1) + " " +f[i][j]+" \n");
					}
				}
				//System.out.print("\n");
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