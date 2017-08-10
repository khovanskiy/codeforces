import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.StringTokenizer;
import java.util.Vector;

public class Countpaths {
    static BufferedReader in;
    static BufferedWriter out;
    static StringTokenizer st;
    static Node[] mas;
    static int q = 1000000007;

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

    static void print(int[][] d) {
        for (int i = 0; i < d.length; i++) {
            for (int j = 0; j < d[i].length; j++) {
                System.out.print(d[i][j] + " ");
            }
            System.out.print("\n");
        }
    }

    static int rec(int v, int n) {
        if (mas[v].c > 0) {
            return mas[v].c;
        }
        for (int i = 0; i < mas[v].ch.size(); i++) {
            mas[v].c += rec(mas[v].ch.get(i), n) % q;
        }
        mas[v].c %= q;
        return mas[v].c;
    }

    public static void main(String args[]) {
        try {
            in = new BufferedReader(new FileReader("countpaths.in"));
            out = new BufferedWriter(new FileWriter("countpaths.out"));
            int n = nextInt();
            int m = nextInt();
            mas = new Node[n];
            for (int i = 0; i < n; i++) {
                mas[i] = new Node();
            }
            for (int i = 0; i < m; i++) {
                int v1 = nextInt() - 1;
                int v2 = nextInt() - 1;
                mas[v2].ch.add(v1);
            }
            mas[0].c = 1;
            rec(n - 1, n);
            out.write(mas[n - 1].c + "");
            in.close();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }

    static class Node {
        public Vector<Integer> ch;
        public int c;

        public Node() {
            ch = new Vector<Integer>();
        }
    }
}
