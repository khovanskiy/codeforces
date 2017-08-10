import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.StringTokenizer;
import java.util.Vector;

public class Matching {
    static BufferedReader in;
    static BufferedWriter out;
    static StringTokenizer st;
    static Vector<Vector<Egle>> graph;

    static int nextInt() {
        return Integer.parseInt(nextToken());
    }

    static long nextLong() {
        return Long.parseLong(nextToken());
    }

    static String nextToken() {
        while (st == null || !st.hasMoreTokens()) {
            try {
                st = new StringTokenizer(in.readLine());
            } catch (Exception e) {
                return "0";
            }
        }
        return st.nextToken();
    }

    static void print(int[] d) {
        for (int i = 0; i < d.length; i++) {
            System.out.print(d[i] + " ");
        }
        System.out.print("\n");
    }

    static void print(int[][] d) {
        for (int i = 0; i < d.length; i++) {
            for (int j = 0; j < d[i].length; j++) {
                System.out.print(d[i][j] + " ");
            }
            System.out.print("\n");
        }
    }

    static void print(String[][] d) {
        for (int i = 0; i < d.length; i++) {
            for (int j = 0; j < d[i].length; j++) {
                System.out.print(d[i][j] + " ");
            }
            System.out.print("\n");
        }
    }

    public static void dfs(int v, boolean[] used, long[] a, long[] b, long[] c) {
        used[v] = true;
        for (int i = 0; i < graph.get(v).size(); i++) {
            if (!used[graph.get(v).get(i).to]) {
                dfs(graph.get(v).get(i).to, used, a, b, c);
                a[v] = Math.max(a[v], b[graph.get(v).get(i).to] + graph.get(v).get(i).w - c[graph.get(v).get(i).to]);
                b[v] += c[graph.get(v).get(i).to];
            }
        }
        a[v] += b[v];
        c[v] = Math.max(a[v], b[v]);
    }

    public static void main(String args[]) {
        try {
            in = new BufferedReader(new FileReader("matching.in"));
            out = new BufferedWriter(new FileWriter("matching.out"));
            int n = nextInt();
            long[] a = new long[n];
            long[] b = new long[n];
            long[] c = new long[n];
            boolean[] used = new boolean[n];
            graph = new Vector<Vector<Egle>>();
            for (int i = 0; i < n; i++) {
                graph.add(new Vector<Egle>());
            }
            for (int i = 0; i < n - 1; i++) {
                int v1 = nextInt() - 1;
                int v2 = nextInt() - 1;
                int w = nextInt();
                graph.get(v1).add(new Egle(v2, w));
                graph.get(v2).add(new Egle(v1, w));
            }
            dfs(0, used, a, b, c);
            //print(c);
            //print(a);
            //print(b);
            out.write(c[0] + "");
            in.close();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }

    static class Egle {
        public int to;
        public int w;

        public Egle(int to, int w) {
            this.to = to;
            this.w = w;
        }
    }
}
