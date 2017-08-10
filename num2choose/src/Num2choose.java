import java.io.*;
import java.util.StringTokenizer;


public class Num2choose {

    static BufferedReader in;
    static BufferedWriter out;
    static StringTokenizer st;
    static long c[][] = new long[35][35];

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

    static int nextInt() {
        return Integer.parseInt(nextToken());
    }

    static long nextLong() {
        return Long.parseLong(nextToken());
    }

    static void print(int[] mas) throws IOException {
        for (int i = 0; i < mas.length; i++) {
            out.write((mas[i]) + " ");
        }
        out.write("\n");
    }

    static void print(long[][] mas) {
        for (int i = 0; i < mas.length; i++) {
            for (int j = 0; j < mas[i].length; j++) {
                System.out.print(mas[i][j] + " ");
            }
            System.out.print("\n");
        }
    }

    static long C(int n, int k) {
        if (k < 0 || n < 0) {
            return 0;
        }
        if (c[n][k] != 0) {
            return c[n][k];
        }
        c[n][k] = C(n - 1, k) + C(n - 1, k - 1);
        return c[n][k];
    }

    public static void main(String args[]) {
        try {
            in = new BufferedReader(new FileReader("num2choose.in"));
            out = new BufferedWriter(new FileWriter("num2choose.out"));
            int n = nextInt();
            int k = nextInt();
            long m = nextLong() + 1;
            int[] mas = new int[k];
            c = new long[35][35];
            for (int i = 1; i < c.length; i++) {
                c[i][1] = i;
                c[i][0] = 1;
            }
            for (int i = 1, j = 1; i < c.length; i++, j++) {
                c[i][j] = 1;
            }
            c[0][0] = 1;
            int l = 0;
            for (int i = 0; i < k; i++) {
                int j = l + 1;
                long s = 0;
                while (j <= n) {
                    if (s + C(n - j, k - i - 1) >= m) {
                        mas[i] = j;
                        break;
                    }
                    s += C(n - j, k - i - 1);
                    j++;
                }
                l = mas[i];
                m -= s;
            }
            print(mas);
            in.close();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }
}
