import java.io.*;
import java.util.StringTokenizer;
import java.util.Vector;

public class Partition {

    static BufferedReader in;
    static BufferedWriter out;
    static StringTokenizer st;
    static Vector<int[]> v;

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

    static void gen(int[] mas, int n, int sum, int l, int c) throws IOException {
        if (sum > n) {
            return;
        }
        if (sum == n) {
            for (int i = 0; i < l - 1; i++) {
                out.write(mas[i] + "+");
            }
            out.write(mas[l - 1] + "\n");
            return;
        }
        for (int i = c; i < n; i++) {
            mas[l] = i + 1;
            gen(mas, n, sum + i + 1, l + 1, i);
        }
    }

    public static void main(String args[]) {
        try {
            in = new BufferedReader(new FileReader("partition.in"));
            out = new BufferedWriter(new FileWriter("partition.out"));
            int n = nextInt();
            int[] mas = new int[n];
            gen(mas, n, 0, 0, 0);
            in.close();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }

    static class R {
        int[] mas;
        int l;

        public R(int[] mas, int l) {
            this.mas = mas;
            this.l = l;
        }
    }
}
