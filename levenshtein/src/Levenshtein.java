import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.StringTokenizer;

public class Levenshtein {
    static BufferedReader in;
    static BufferedWriter out;
    static StringTokenizer st;
    static String s1;
    static String s2;
    static int[][] d;

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

    static int D(int i, int j) {
        if (d[i][j] != -1) {
            return d[i][j];
        }
        int cost = (s1.charAt(i - 1) == s2.charAt(j - 1) ? 0 : 1);
        d[i][j] = Math.min(Math.min(D(i, j - 1) + 1, D(i - 1, j) + 1), D(i - 1, j - 1) + cost);
        return d[i][j];
    }

    static void print(int[][] d) {
        for (int i = 0; i < d.length; i++) {
            for (int j = 0; j < d[i].length; j++) {
                System.out.print(d[i][j] + " ");
            }
            System.out.print("\n");
        }
    }

    public static void main(String args[]) {
        try {
            in = new BufferedReader(new FileReader("levenshtein.in"));
            out = new BufferedWriter(new FileWriter("levenshtein.out"));
            s1 = nextToken();
            s2 = nextToken();
            int n = s1.length() + 1;
            int m = s2.length() + 1;
            d = new int[n][m];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    d[i][j] = -1;
                }
            }
            for (int i = 0; i < n; i++) {
                d[i][0] = i;
            }
            for (int j = 0; j < m; j++) {
                d[0][j] = j;
            }
            d[0][0] = 0;
            out.write(D(n - 1, m - 1) + "");
            in.close();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }
}
