import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.StringTokenizer;
import java.util.Vector;

public class Lcs {
    static BufferedReader in;
    static BufferedWriter out;
    static StringTokenizer st;
    static String s1;
    static String s2;
    static int[][] d;

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

    static int rec(int[] a, int[] b, int i, int j) {
        if (d[i][j] != -1) {
            return d[i][j];
        }
        if (a[i - 1] == b[j - 1]) {
            d[i][j] = rec(a, b, i - 1, j - 1) + 1;
        } else {
            d[i][j] = Math.max(rec(a, b, i - 1, j), rec(a, b, i, j - 1));
        }
        return d[i][j];
    }

    static void print(int[][] d) {
        for (int i = 0; i < d.length; i++) {
            for (int j = 0; j < d[i].length; j++) {
                System.out.print(d[i][j] + " ");
            }
            System.out.print("\n");
        }
    }

    public static void main(String args[]) {
        try {
            in = new BufferedReader(new FileReader("lcs.in"));
            out = new BufferedWriter(new FileWriter("lcs.out"));
            int n = nextInt();
            int[] a = new int[n];
            for (int i = 0; i < n; i++) {
                a[i] = nextInt();
            }
            int m = nextInt();
            int[] b = new int[m];
            for (int i = 0; i < m; i++) {
                b[i] = nextInt();
            }
            d = new int[n + 1][m + 1];
            for (int i = 0; i < n + 1; i++) {
                for (int j = 0; j < m + 1; j++) {
                    d[i][j] = -1;
                }
            }
            for (int i = 0; i < n + 1; i++) {
                d[i][0] = 0;
            }
            for (int j = 0; j < m + 1; j++) {
                d[0][j] = 0;
            }
            rec(a, b, n, m);
            int l = d[n][m];
            out.write(l + "\n");
            int i = n;
            int j = m;
            int k = 0;
            Vector<Integer> v = new Vector<Integer>();
            while (k < l) {
                while (d[i][j] == d[i][j - 1]) j--;
                while (d[i][j] == d[i - 1][j]) i--;
                v.add(a[i - 1]);
                k++;
                i--;
                j--;
            }
            for (int q = v.size() - 1; q >= 0; q--) {
                out.write(v.get(q) + " ");
            }
            //print(d);
            in.close();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }
}
