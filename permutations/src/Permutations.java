import java.io.*;
import java.util.StringTokenizer;


public class Permutations {

    static BufferedReader in;
    static BufferedWriter out;
    static StringTokenizer st;
    static int[] p;
    static boolean[] used;

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

    static void pgen(int n, int pos) throws IOException {
        if (pos == n) {
            for (int i = 0; i < n; i++) {
                out.write((p[i] + 1) + " ");
            }
            out.write("\n");
            return;
        }
        for (int i = 0; i < n; i++) {
            if (!used[i]) {
                used[i] = true;
                p[pos] = i;
                pgen(n, pos + 1);
                //p[pos]=0;
                used[i] = false;
            }
        }
    }

    public static void main(String args[]) {
        try {
            in = new BufferedReader(new FileReader("permutations.in"));
            out = new BufferedWriter(new FileWriter("permutations.out"));
            int n = nextInt();
            p = new int[n];
            used = new boolean[n];
            pgen(n, 0);
            in.close();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }
}
