import java.io.*;
import java.util.StringTokenizer;

public class Part2num {

    static BufferedReader in;
    static BufferedWriter out;
    static StringTokenizer st;
    static long[][] d;

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

    static void print(int[] mas) throws IOException {
        for (int i = 0; i < mas.length - 1; i++) {
            System.out.print(mas[i] + "+");
        }
        System.out.print(mas[mas.length - 1]);
        System.out.print("\n");
    }

    static void print(long[][] mas) {
        for (int i = 0; i < mas.length; i++) {
            for (int j = 0; j < mas[i].length; j++) {
                System.out.print(mas[i][j] + " ");
            }
            System.out.print("\n");
        }
    }

    public static void main(String args[]) {
        try {
            in = new BufferedReader(new FileReader("part2num.in"));
            out = new BufferedWriter(new FileWriter("part2num.out"));
            String s = nextToken();
            String[] sm = s.split("\\+");
            int l = sm.length;
            int n = 0;
            for (int i = 0; i < l; i++) {
                n += Integer.parseInt(sm[i]);
            }
            int[] mas = new int[n];
            for (int i = 0; i < l; i++) {
                mas[i] = Integer.parseInt(sm[i]);
            }
            //System.out.println(l+" "+n);
            d = new long[n + 1][n + 1];
            for (int i = 1; i <= n; i++) {
                d[i][i] = 1;
            }
            for (int i = 1; i <= n; i++) {
                for (int j = 1; j <= i; j++) {
                    for (int k = j; k <= i; k++) {
                        d[i][j] += d[i - j][k];
                    }
                }
            }
            boolean[] used = new boolean[n * 2];
            long m = 0;
            int prev = 1;
            for (int i = 0; i < l; i++) {
                if (!used[mas[i]]) {
                    for (int j = prev; j < mas[i]; j++) {
                        m += d[n][j];
                    }
                    used[mas[i]] = true;
                    prev = mas[i];
                }
                n -= mas[i];
            }
            out.write(m + "");
            in.close();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }
}
