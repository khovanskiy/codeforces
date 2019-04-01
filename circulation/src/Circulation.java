import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.StringTokenizer;
import java.util.Vector;

public class Circulation
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
	
	static void trace(int[] e)
	{
		for (int i = 0; i < e.length; ++i)
		{
			System.out.print(e[i]+ " ");
		}
		System.out.println();
	}
	
	static void trace(String s)
	{
		System.out.println(s);
	}
	
	static StringTokenizer st;
	static BufferedReader in;
	static BufferedWriter out;

	static final int INF = Integer.MAX_VALUE;
	
	static class Edge
	{
		public int a;
		public int b;
		public int cap;
		public int flow;
		public int l;
		
		public Edge(int u, int v, int capacity, int flow, int l)
		{
			this.a = u;
			this.b = v;
			this.cap = capacity;
			this.flow = flow;
			this.l = l;
		}
	}
	static class Graph
	{
		private boolean[][] connectivity;
		private Vector<Integer>[] graph;
		private Vector<Edge> edges;
		private int n;
		
		private int source;
		private int sink;
		
		static int dist[];
		static int path[];
		static int q[];
		
		public Graph(int n)
		{
			this.n = n;
			
			graph = new Vector[n];
			for (int i = 0; i < n; ++i)
			{
				graph[i] = new Vector<Integer>();
			}
			
			edges = new Vector<Edge>();
			
			connectivity = new boolean[n][n];
			dist = new int[n];
			path = new int[n];
			q = new int[n];
		}
		
		/*public void addEdge(int u, int v, int capacity, int flow)
		{
			connectivity[u][v] = true;
			c[u][v] = capacity;
			if (flow > 0)
			{
				connectivity[v][u] = true;
				c[v][u] = flow;
			}
		}*/
		
		public void deleteEdge(int u, int v)
		{
			connectivity[u][v] = false;
		}
		
		public boolean hasEdge(int u, int v)
		{
			return connectivity[u][v];
		}
		
		public void addEdge(int a, int b, int capacity, int flow, int l) 
		{
			connectivity[a][b] = true;
			Edge e1 = new Edge(a, b, capacity, flow, l);
			Edge e2 = new Edge(b, a, 0, flow, l);
			graph[a].add(edges.size());
			edges.add(e1);
			graph[b].add(edges.size());
			edges.add(e2);
		}
		 
		private boolean bfs() 
		{
			int ci = 0;
			int cm = 0;
			q[cm++] = source;
			
			for (int i = 0; i < dist.length; ++i)
			{
				dist[i] = -1;
			}
			dist[source] = 0;
			
			while (ci < cm && dist[sink] == -1)
			{
				int v = q[ci++];
				for (int i=0; i<graph[v].size(); ++i) 
				{
					int id = graph[v].get(i);
					int to = edges.get(id).b;
					
					if (dist[to] == -1 && edges.get(id).flow < edges.get(id).cap)
					{
						q[cm++] = to;
						dist[to] = dist[v] + 1;
					}
				}
			}
			return dist[sink] != -1;
		}
		 
		private int dfs (int v, int flow)
		{
			if (flow == 0)
			{
				return 0;
			}
			if (v == sink)
			{
				return flow;
			}
			for (; path[v] < graph[v].size(); ++path[v]) 
			{
				int id = graph[v].get(path[v]);
				int to = edges.get(id).b;
				
				if (dist[to] != dist[v] + 1)
				{
					continue;
				}
				int pushed = dfs (to, Math.min(flow, edges.get(id).cap - edges.get(id).flow));
				
				if (pushed != 0) 
				{
					edges.get(id).flow += pushed;
					edges.get(id ^ 1).flow -= pushed;
					return pushed;
				}
			}
			return 0;
		}
		
		public int findMaxFlow(int source, int sink)
		{
			this.source = source;
			this.sink = sink;
			
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
					flow += pushed;
					pushed = dfs(source, INF);
				}
			}
			return flow;
		}
		
		public void print()
		{
			for (int i = 0; i < n; ++i)
			{
				for (int j = 0; j < graph[i].size(); ++j)
				{
					Edge e = edges.get(graph[i].get(j));
					if (e.flow > 0)
					{
						System.out.print((e.a + 1) + " -> " + (e.b + 1) + " " +e.flow+" \n");
					}
				}
			}
		}
		
		public void circ(BufferedWriter out) throws IOException
		{
			int s = 0;
			int t = n - 1;
			int flow = 0;
			flow = findMaxFlow(s, t);
			//trace(flow+"");
			boolean ok = true;
			for (int i = 0; i < graph[s].size(); ++i)
			{
				Edge e = edges.get(graph[s].get(i));
				if (e.flow < e.cap)
				{
					ok = false;
					break;
				}
			}
			if (ok)
			{
				out.write("YES\n");
				for (int i = 0; i < edges.size(); i += 2)
				{
					if (edges.get(i).a != s && edges.get(i).b != t)
					{
						out.write((edges.get(i).flow + edges.get(i).l) + "\n");
					}
				}
			}
			else
			{
				out.write("NO\n");
			}
		}
	}
	
	public static void main(String[] args)
	{
		try
		{
			in = new BufferedReader(new FileReader("circulation.in"));
			out = new BufferedWriter(new FileWriter("circulation.out"));
			
			int n = nextInt() + 2;
			int m = nextInt();
			
			Graph graph = new Graph(n);
			
			int s = 0;
			int t = n - 1;
			
			for (int i = 0; i < m; ++i)
			{
				int a = nextInt();
				int b = nextInt();
				int l = nextInt();
				int c = nextInt();
				
				graph.addEdge(s, b, l, 0, l);
				graph.addEdge(a, b, c - l, 0, l);
				graph.addEdge(a, t, l, 0, l);
			}
			
			graph.circ(out);
			
			//out.write(flow+"\n");
			
			//graph.print();
			
			//graph.decomposition(out);
			
			//trace("--------");
			
			//graph.print();
			
			in.close();
			out.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}