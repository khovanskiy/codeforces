import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Arrays;
import java.util.StringTokenizer;
import java.util.Vector;
 
public class Paths {
    static class Node {
        public int u;
        public long cost;
 
        public Node(int u, long cost) {
            this.u = u;
            this.cost = cost;
        }
    }
 
    static class Edge implements Comparable<Edge> {
        public int u;
        public int v;
        public long cost;
 
        public Edge(int u, int v, long cost) {
            this.u = u;
            this.v = v;
            this.cost = cost;
        }
 
        @Override
        public int compareTo(Edge other) {
            if (this.cost < other.cost) {
                return -1;
            } else if (this.cost > other.cost) {
                return 1;
            }
            return 0;
        }
    }
 
    static String nextToken() {
        while (st == null || !st.hasMoreTokens()) {
            try {
                st = new StringTokenizer(in.readLine());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return st.nextToken();
    }
 
    static int nextInt() {
        return Integer.parseInt(nextToken());
    }
 
    static void trace(int s) {
        System.out.println(s);
    }
 
    static void trace(String s) {
        System.out.println(s);
    }
 
    static StringTokenizer st;
    static BufferedReader in;
    static BufferedWriter out;
    static boolean[][] graph;
 
    static final long INF = Integer.MAX_VALUE;
 
    public static boolean dfs(boolean[][] graph, boolean[] used, int[] array, int s, int t) 
    {
        if (s == -1)
        {
            return true;
        }
 
        for (int i = 1; i < t + 1; ++i)
        {
            if (graph[s][i] && !used[i])
            {
                used[i] = true;
                if (dfs(graph, used, array, array[i], t))
                {
                    array[i] = s;
                    return true;
                }
            }
        }
 
        return false;
    }
 
    public static void main(String[] args)
    {
        try
        {
            in = new BufferedReader(new FileReader("paths.in"));
            out = new BufferedWriter(new FileWriter("paths.out"));
            int n = nextInt();
            int k = nextInt();
 
            graph = new boolean[n + 1][n + 1];
 
            for (int i = 0; i < k; ++i)
            {
                int a = nextInt();
                int b = nextInt();
                graph[a][b] = true;
            }
 
            int sink = n;
 
            int f = 0;
            int[] array = new int[sink + 1];
            Arrays.fill(array, -1);
             
            for (int u = 1; u < n + 1; ++u)
            {
                boolean[] used = new boolean[n + 1];
                if (dfs(graph, used, array, u, sink))
                {
                    ++f;
                }
            }
             
            out.write((n - f)+"");
 
            in.close();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}