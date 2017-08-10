import java.io.*;
import java.util.StringTokenizer;


public class Choose2num {

    static BufferedReader in;
    static BufferedWriter out;
    static StringTokenizer st;
    static long fact[] = {1, 1, 2, 6, 24, 120, 720, 5040, 40320, 362880, 3628800, 39916800, 479001600, 6227020800L, 87178291200L, 1307674368000L, 20922789888000L, 355687428096000L};
    static long c[][] = new long[35][35];

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

    static void print(int[] mas) throws IOException {
        for (int i = 0; i < mas.length; i++) {
            out.write((mas[i] + 1) + " ");
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
            in = new BufferedReader(new FileReader("choose2num.in"));
            out = new BufferedWriter(new FileWriter("choose2num.out"));
            int n = nextInt();
            int k = nextInt();
            int mas[] = new int[k];
            for (int i = 0; i < k; i++) {
                mas[i] = nextInt();
            }
            c = new long[35][35];
            for (int i = 1; i < c.length; i++) {
                c[i][1] = i;
                c[i][0] = 1;
            }
            for (int i = 1, j = 1; i < c.length; i++, j++) {
                c[i][j] = 1;
            }
            long m = 0;
            int l = 0;
            for (int i = 0; i < k; i++) {
                for (int j = l + 1; j < mas[i]; j++) {
                    m += C(n - j, k - i - 1);
                }
                l = mas[i];
            }
            out.write(m + "");
            in.close();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }
}
