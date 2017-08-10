import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.StringTokenizer;
import java.util.Vector;

public class Lis {
    static BufferedReader in;
    static BufferedWriter out;
    static StringTokenizer st;
    static String s1;
    static String s2;
    static int[][] d;

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

    static void print(int[] d) {
        for (int i = 0; i < d.length; i++) {
            System.out.print(d[i] + " ");
        }
        System.out.print("\n");
    }

    public static void main(String args[]) {
        try {
            in = new BufferedReader(new FileReader("lis.in"));
            out = new BufferedWriter(new FileWriter("lis.out"));
            int n = nextInt();
            int[] mas = new int[n];
            int[] d = new int[n];
            int[] p = new int[n];
            for (int i = 0; i < n; i++) {
                mas[i] = nextInt();
            }
            for (int i = 0; i < n; ++i) {
                d[i] = 1;
                p[i] = -1;
                for (int j = 0; j < i; ++j) {
                    if (mas[j] < mas[i]) {
                        if (d[i] < d[j] + 1) {
                            d[i] = d[j] + 1;
                            p[i] = j;
                        }
                    }
                }
            }
            int ans = d[0];
            int pos = 0;
            for (int i = 0; i < n; ++i) {
                if (d[i] > ans) {
                    ans = d[i];
                    pos = i;
                }
            }
            Vector<Integer> v = new Vector<Integer>();
            while (pos != -1) {
                v.add(pos);
                pos = p[pos];
            }
            out.write(ans + "\n");
            for (int i = v.size() - 1; i >= 0; i--) {
                out.write(mas[v.get(i)] + " ");
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
}
