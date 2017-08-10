import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.StringTokenizer;

public class Matrix {
    static BufferedReader in;
    static BufferedWriter out;
    static StringTokenizer st;
    static int[][] d;
    static int[][] p;
    static int inf = Integer.MAX_VALUE;
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

    static void print(String[][] d) {
        for (int i = 0; i < d.length; i++) {
            for (int j = 0; j < d[i].length; j++) {
                System.out.print(d[i][j] + " ");
            }
            System.out.print("\n");
        }
    }

    static int rec(int i, int j, int[] a) {
        if (d[i][j] != -1) {
            return d[i][j];
        }
        int res = rec(i + 1, j, a) + a[i - 1] * a[i] * a[j];
        p[i][j] = i;
        for (int k = i + 1; k < j; k++) {
            if (res > rec(i, k, a) + rec(k + 1, j, a) + a[i - 1] * a[k] * a[j]) {
                res = rec(i, k, a) + rec(k + 1, j, a) + a[i - 1] * a[k] * a[j];
                p[i][j] = k;
            }
        }
        d[i][j] = res;
        return d[i][j];
    }

    public static void generate(int i, int j) {
        if (i == j) {
            ans += "A";
        } else if (i < j) {
            ans += "(";
            generate(i, p[i][j]);
            generate(p[i][j] + 1, j);
            ans += ")";
        }
    }

    public static void main(String args[]) {
        try {
            in = new BufferedReader(new FileReader("matrix.in"));
            out = new BufferedWriter(new FileWriter("matrix.out"));
            int n = nextInt();
            int[] a = new int[n + 1];
            for (int i = 0; i < n - 1; i++) {
                a[i] = nextInt();
                nextInt();
            }
            a[n - 1] = nextInt();
            a[n] = nextInt();
            d = new int[n + 10][n + 10];
            p = new int[n + 10][n + 10];
            for (int i = 0; i < n + 1; i++) {
                for (int j = 0; j < n + 1; j++) {
                    if (i == j) {
                        d[i][j] = 0;
                    } else {
                        d[i][j] = -1;
                    }
                }
            }

            rec(1, n, a);
            generate(1, n);
            out.write(ans + "");
            in.close();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }
}
