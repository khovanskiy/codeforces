import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.StringTokenizer;

public class Optimalcode {
    static BufferedReader in;
    static BufferedWriter out;
    static StringTokenizer st;
    static long inf = (long) (2 * Math.pow(10, 11));
    static int[] f;
    static long[] w;
    static long[][] d;
    static int[][] p;
    static String ans = "";

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

    static long sum(int i, int j) {
        long res = w[j];
        if (i > 0) {
            res -= w[i - 1];
        }
        return res;
    }

    static void generate(int l, int r, String s) {
        if (l == r) {
            ans += s + "\n";
        } else {
            generate(l, p[l][r], s + "0");
            generate(p[l][r] + 1, r, s + "1");
        }
    }

    public static void count(int n) {
        for (int q = 2; q <= n; q++) {
            for (int l = 0; l <= n - q; l++) {
                int r = q + l - 1;
                if (d[l][r] == -1) {
                    long res = inf;
                    int la;
                    int ra;
                    if (p[l][r - 1] > p[l + 1][r]) {
                        la = p[l + 1][r];
                        ra = p[l][r - 1];
                    } else {
                        la = p[l][r - 1];
                        ra = p[l + 1][r];
                    }
                    int max = Math.max(l, la - 10);
                    int min = Math.min(r, ra + 10);
                    for (int i = max; i < min; i++) {
                        long temp = d[l][i] + d[i + 1][r];
                        if (temp < res) {
                            res = temp;
                            p[l][r] = i;
                        }
                    }
                    d[l][r] = res + sum(l, r);
                }
            }
        }
    }

    public static void main(String args[]) {
        try {
            in = new BufferedReader(new FileReader("optimalcode.in"));
            out = new BufferedWriter(new FileWriter("optimalcode.out"));
            int n = nextInt();
            f = new int[n];
            w = new long[n];
            d = new long[n][n];
            p = new int[n][n];
            for (int i = 0; i < n; i++) {
                f[i] = nextInt();
            }
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    d[i][j] = -1;
                }
            }
            for (int i = 0; i < n; i++) {
                d[i][i] = 0;
                p[i][i] = i;
            }
            //print(d);
            //print(p);
            w[0] = f[0];
            for (int i = 1; i < n; i++) {
                w[i] = w[i - 1] + f[i];
            }
            count(n);
            long l = d[0][n - 1];
            out.write(l + "\n");
            generate(0, n - 1, "");
            out.write(ans);

            in.close();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }
}
