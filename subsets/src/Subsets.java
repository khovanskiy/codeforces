import java.io.*;
import java.util.StringTokenizer;
import java.util.Vector;

public class Subsets {

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

    static void print(int[] mas, int m) throws IOException {
        for (int i = 0; i < m; i++) {
            out.write((mas[i] + 1) + " ");
            //System.out.print((mas[i]+1)+" ");
        }
        out.write("\n");
        //System.out.print("\n");
    }

    static void gen(int[] mas, int n, int p, int l) throws IOException {
        if (n == p) {
            return;
        }
        for (int i = l + 1; i < n; i++) {
            mas[p] = i;
            print(mas, p + 1);
            gen(mas, n, p + 1, i);
        }
    }

    public static void main(String args[]) {
        try {
            in = new BufferedReader(new FileReader("subsets.in"));
            out = new BufferedWriter(new FileWriter("subsets.out"));
            int n = nextInt();
            int[] mas = new int[n];
            out.write(" \n");
            gen(mas, n, 0, -1);
            in.close();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }
}
