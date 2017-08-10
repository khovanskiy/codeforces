import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.StringTokenizer;

public class Markchain {
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

    public static void main(String args[]) {
        try {
            in = new BufferedReader(new FileReader("markchain.in"));
            out = new BufferedWriter(new FileWriter("markchain.out"));
            int n = nextInt();
            double[][] mas = new double[n][n];
            double[][] temp = new double[n][n];
            double p = 0.0;
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    mas[i][j] = nextDouble();
                }
            }
            for (int q = 0; q < 100; q++) {
                for (int i = 0; i < n; i++) {
                    for (int j = 0; j < n; j++) {
                        temp[i][j] = mas[i][j];
                    }
                }
                for (int i = 0; i < n; i++) {
                    for (int j = 0; j < n; j++) {
                        mas[i][j] = 0;
                        for (int k = 0; k < n; k++) {
                            mas[i][j] += temp[i][k] * temp[k][j];
                        }
                    }
                }
                for (int i = 0; i < n; i++) {
                    p = 0;
                    for (int j = 0; j < n; j++) {
                        p += mas[i][j];
                    }
                    for (int j = 0; j < n; j++) {
                        mas[i][j] /= p;
                    }
                }
            }
            for (int i = 0; i < n; i++) {
                p = 0;
                for (int j = 0; j < n; j++) {
                    p += mas[j][i];
                }
                p /= n;
                out.write(p + "\n");
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

    static double nextDouble() {
        return Double.parseDouble(nextToken());
    }
}
