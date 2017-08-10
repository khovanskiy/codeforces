import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.StringTokenizer;

public class Exam {
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
            in = new BufferedReader(new FileReader("exam.in"));
            out = new BufferedWriter(new FileWriter("exam.out"));
            int k = nextInt();
            int n = nextInt();
            double s = 0.0;
            for (int i = 0; i < k; i++) {
                int p = nextInt();
                int m = nextInt();
                s += ((double) p / (double) n) * ((double) m / 100.0);
            }
            out.write(s + "");
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
