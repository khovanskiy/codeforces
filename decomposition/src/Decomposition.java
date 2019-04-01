import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.StringTokenizer;
import java.util.Vector;

public class Decomposition
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
		public int id;
		
		public Edge(int u, int v, int capacity, int flow, int id)
		{
			this.a = u;
			this.b = v;
			this.cap = capacity;
			this.flow = flow;
			this.id = id;
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
		
		public void addEdge(int a, int b, int capacity, int flow, int id) 
		{
			connectivity[a][b] = true;
			Edge e1 = new Edge(a, b, capacity, flow, id);
			Edge e2 = new Edge(b, a, 0, flow, id);
			graph[a].add(edges.size());
			edges.add(e1);
			graph[b].add(edges.size());
			edges.add(e2);
		}
		 
		private boolean bfs() 
		{
			int qh=0, qt=0;
			q[qt++] = source;
			
			for (int i = 0; i < dist.length; ++i)
			{
				dist[i] = -1;
			}
			dist[source] = 0;
			
			while (qh < qt && dist[sink] == -1)
			{
				int v = q[qh++];
				for (int i=0; i<graph[v].size(); ++i) 
				{
					int id = graph[v].get(i);
					int to = edges.get(id).b;
					
					if (dist[to] == -1 && edges.get(id).flow < edges.get(id).cap)
					{
						q[qt++] = to;
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
		
		private boolean loop(int u, boolean[] used)
		{
			used[u] = true;
			for (int i = 0; i < graph[u].size(); ++i)
			{
				Edge e = edges.get(graph[u].get(i));
				if (!used[e.b] && e.flow > 0)
				{
					if (e.b == sink || loop(e.b, used));
					{
						minf = Math.min(e.flow, minf);
						current.add(e);
						return true;
					}
				}
			}
			return false;
		}
		
		private int minf;
		private Vector<Edge> current;
		
		public void decomposition(BufferedWriter out) throws IOException
		{
			boolean[] used;
			Vector<Vector<Edge>> list = new Vector<Vector<Edge>>();
			Vector<Integer> mm = new Vector<Integer>();
			
			boolean ok = false;
			do
			{
				minf = INF;
				current = new Vector<Edge>();
				used = new boolean[n];
				
				ok = loop(source, used);
				if (!ok)
				{
					break;
				}
				
				mm.add(minf);
				list.add(current);
				
				for (int i = current.size() - 1; i >= 0; --i)
				{
					Edge e = current.get(i);
					e.flow -= minf;
				}
			} while (ok);
			
			out.write(list.size()+"\n");
			for (int i = 0; i < list.size(); ++i)
			{
				Vector<Edge> temp = list.get(i);
				out.write(mm.get(i)+" "+temp.size()+" ");
				for (int j = temp.size() - 1; j >= 0; --j)
				{
					Edge e = temp.get(j);
					out.write((e.id + 1)+" ");
				}
				out.write("\n");
			}
		}
	}
	
	public static void main(String[] args)
	{
		try
		{
			in = new BufferedReader(new FileReader("decomposition.in"));
			out = new BufferedWriter(new FileWriter("decomposition.out"));
			
			int n = nextInt();
			int m = nextInt();
			
			int s = 0;
			int t = n - 1;
			
			Graph graph = new Graph(n);
			
			int id = 0;
			for (int i = 0; i < m; ++i)
			{
				int a = nextInt() - 1;
				int b = nextInt() - 1;
				int c = nextInt();
				graph.addEdge(a, b, c, 0, id);
				++id;
			}
			
			
			int flow = 0;
			flow = graph.findMaxFlow(s, t);
			//out.write(flow+"\n");
			
			//graph.print();
			
			graph.decomposition(out);
			
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