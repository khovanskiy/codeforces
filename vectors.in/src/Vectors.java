import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.StringTokenizer;
import java.util.Vector;

public class Vectors {

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

    static void gen(int n, int p, int[] mas, int k) {
        if (k == 2) {
            return;
        }
        if (n == p) {
            v.add(mas.clone());
            return;
        }
        mas[p] = 0;
        gen(n, p + 1, mas, 0);

        mas[p] = 1;
        k++;
        gen(n, p + 1, mas, k);
    }

    public static void main(String args[]) {
        try {
            in = new BufferedReader(new FileReader("vectors.in"));
            out = new BufferedWriter(new FileWriter("vectors.out"));
            int n = nextInt();
            int mas[] = new int[n];
            v = new Vector<int[]>();
            gen(n, 0, mas, 0);
            out.write(v.size() + "\n");
            for (int i = 0; i < v.size(); i++) {
                for (int j = 0; j < n; j++) {
                    out.write(v.get(i)[j] + "");
                }
                out.write("\n");
            }
            in.close();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }
}
