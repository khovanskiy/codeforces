import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.StringTokenizer;

public class Salesman {

    static BufferedReader in;
    static BufferedWriter out;
    static StringTokenizer st;

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

    /*static int rec(int l,int r,String s)
    {
        if (d[l][r] == -1)
        {
            if (s.charAt(l) == s.charAt(r))
            {
                d[l][r] = rec(l + 1, r - 1, s) + 2;
            }
            else
            {
                d[l][r] = Math.max(rec(l, r - 1, s), rec(l + 1, r, s));
            }
        }
        return d[l][r];
    }*/
    public static void main(String args[]) {
        try {
            in = new BufferedReader(new FileReader("salesman.in"));
            out = new BufferedWriter(new FileWriter("salesman.out"));
            int n = nextInt();
            int m = nextInt();
            int[][] mas = new int[n][n];
            for (int i = 0; i < m; i++) {
                int v1 = nextInt() - 1;
                int v2 = nextInt() - 1;
                int w = nextInt();
                mas[v1][v2] = mas[v2][v1] = w;
            }
            int x = 1 << n;
            //long inf = Long.MAX_VALUE;
            //long inf = Long.MAX_VALUE-10000;
            long inf = (long) (2 * Math.pow(10, 10));

            long[][] d = new long[n][x + 1];
            for (int i = 0; i < n; i++) {
                for (int j = 1; j < x; j++) {
                    d[i][j] = inf;
                }
            }
            d[0][0] = 0;

            for (int i = 0; i < n; i++) {
                d[i][1 << i] = 0;
            }

            for (int mask = 0; mask < x; mask++) {
                for (int i = 0; i < n; i++) {
                    for (int j = 0; j < n; j++) {
                        if (((mask >> i) % 2 != 0) && mas[i][j] != 0) {
                            d[j][mask] = Math.min(d[j][mask], d[i][mask - (1 << i)] + mas[i][j]);
                        }
                    }
                }
            }
            long ans = inf;
            boolean ok = false;
            for (int i = 0; i < n; i++) {
                if (d[i][x - (1 << i) - 1] < ans) {
                    ans = d[i][x - 1 - (1 << i)];
                    ok = true;
                }
            }
            if (ok) {
                out.write(ans + "");
            } else {
                out.write("-1");
            }
            in.close();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }

    static int nextInt() {
        return Integer.parseInt(nextToken());
    }
}
