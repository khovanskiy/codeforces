import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.StringTokenizer;

public class Shooter {
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
            in = new BufferedReader(new FileReader("shooter.in"));
            out = new BufferedWriter(new FileWriter("shooter.out"));
            int n = nextInt();
            int m = nextInt();
            int k = nextInt();
            double c = 0;
            double total = 0;
            for (int i = 0; i < n; i++) {
                double t = nextDouble();
                double p = 1;
                for (int j = 0; j < m; j++) {
                    p *= (1 - t);
                }
                if (i + 1 == k) {
                    c = p;
                }
                total += p;
            }
            if (total == 0) {
                out.write("0");
            } else {
                double res = c / total;
                out.write(res + "");
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
