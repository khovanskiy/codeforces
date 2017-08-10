import java.io.*;
import java.util.StringTokenizer;

public class Num2part {

    static BufferedReader in;
    static BufferedWriter out;
    static StringTokenizer st;
    static long[][] d;

    static void print(int[] mas) throws IOException {
        for (int i = 0; i < mas.length - 1; i++) {
            System.out.print(mas[i] + "+");
        }
        System.out.print(mas[mas.length - 1]);
        System.out.print("\n");
    }

    static void print(long[][] mas) {
        for (int i = 0; i < mas.length; i++) {
            for (int j = 0; j < mas[i].length; j++) {
                System.out.print(mas[i][j] + " ");
            }
            System.out.print("\n");
        }
    }

    public static void main(String args[]) {
        try {
            in = new BufferedReader(new FileReader("num2part.in"));
            out = new BufferedWriter(new FileWriter("num2part.out"));
            int n = nextInt();
            long r = nextLong();
            int[] mas = new int[n];
            d = new long[n + 1][n + 1];
            for (int i = 1; i <= n; i++) {
                d[i][i] = 1;
            }
            for (int i = 1; i <= n; i++) {
                for (int j = 1; j <= i; j++) {
                    for (int k = j; k <= i; k++) {
                        d[i][j] += d[i - j][k];
                    }
                }
            }
            int prev = 1;
            int s = n;
            int l = 0;
            while (s > 0) {
                int temp = prev;
                while (temp <= n && r - D(s, temp) >= 0) {
                    r -= D(s, temp);
                    temp++;
                }
                mas[l++] = temp;
                s -= temp;
                prev = temp;
            }
            for (int i = 0; i < l - 1; i++) {
                out.write(mas[i] + "+");
            }
            out.write(mas[l - 1] + "");

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

    static long nextLong() {
        return Long.parseLong(nextToken());
    }

    static long D(int i, int j) {
        return d[i][j];
    }
}
