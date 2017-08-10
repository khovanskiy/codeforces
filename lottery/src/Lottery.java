import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.StringTokenizer;

public class Lottery {
    static BufferedReader in;
    static BufferedWriter out;
    static StringTokenizer st;

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

    static double nextDouble() {
        return Double.parseDouble(nextToken());
    }

    public static void main(String args[]) {
        try {
            in = new BufferedReader(new FileReader("lottery.in"));
            out = new BufferedWriter(new FileWriter("lottery.out"));
            long n = nextLong();
            long m = nextLong();
            double s = 0.0;
            double k = 1;
            long old = 0;
            for (int i = 0; i < m; i++) {
                long a = nextLong();
                long b = nextLong();
                s += (k / a) * (b - old);
                k /= a;
                old = b;
            }
            s = n - s;
            out.write(s + "");
            in.close();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }

    static long nextLong() {
        return Long.parseLong(nextToken());
    }
}
