import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.StringTokenizer;

public class Palindrome {

    static BufferedReader in;
    static BufferedWriter out;
    static StringTokenizer st;
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

    static void print(int[] d) {
        for (int i = 0; i < d.length; i++) {
            System.out.print(d[i] + " ");
        }
        System.out.print("\n");
    }

    static void print(int[][] d) {
        for (int i = 0; i < d.length; i++) {
            for (int j = 0; j < d[i].length; j++) {
                System.out.print(d[i][j] + " ");
            }
            System.out.print("\n");
        }
    }

    static void print(String[][] d) {
        for (int i = 0; i < d.length; i++) {
            for (int j = 0; j < d[i].length; j++) {
                System.out.print(d[i][j] + " ");
            }
            System.out.print("\n");
        }
    }

    static int rec(int l, int r, String s) {
        if (d[l][r] == -1) {
            if (s.charAt(l) == s.charAt(r)) {
                d[l][r] = rec(l + 1, r - 1, s) + 2;
            } else {
                d[l][r] = Math.max(rec(l, r - 1, s), rec(l + 1, r, s));
            }
        }
        return d[l][r];
    }

    public static void main(String args[]) {
        try {
            in = new BufferedReader(new FileReader("palindrome.in"));
            out = new BufferedWriter(new FileWriter("palindrome.out"));
            String s = nextToken();
            int n = s.length();
            d = new int[n][n];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (i > j) {
                        d[i][j] = 0;
                    } else if (i == j) {
                        d[i][j] = 1;
                    } else {
                        d[i][j] = -1;
                    }
                }
            }
            int l = rec(0, n - 1, s);
            out.write(l + "\n");

            int i = 0;
            int j = n - 1;
            int la = 0;
            int ra = l - 1;
            char[] res = new char[l];
            while (i <= j) {
                if (i == j && d[i][j] == 1) {
                    res[la++] = s.charAt(j);
                    i++;
                } else if (s.charAt(i) == s.charAt(j)) {
                    res[la++] = s.charAt(i);
                    res[ra--] = s.charAt(j);
                    i++;
                    j--;
                } else if (d[i][j - 1] < d[i + 1][j]) {
                    i++;
                } else {
                    j--;
                }
            }
            for (int q = 0; q < l; q++) {
                out.write(res[q]);
            }
            in.close();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }
}
