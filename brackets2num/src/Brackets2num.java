import java.io.*;
import java.util.StringTokenizer;

public class Brackets2num {

    static BufferedReader in;
    static BufferedWriter out;
    static StringTokenizer st;
    static long[][] r;

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
        for (int i = 0; i < mas.length; i++) {
            out.write((mas[i] + 1) + " ");
        }
        out.write("\n");
    }

    static void print(long[][] mas) {
        for (int i = 0; i < mas.length; i++) {
            for (int j = 0; j < mas[i].length; j++) {
                System.out.print(mas[i][j] + " ");
            }
            System.out.print("\n");
        }
    }

    static long R(int n, int k) {
        if (n == 0 && k == 0) {
            return 1;
        }
        if (n == 0 || k > n || k < 0) {
            return 0;
        }
        if (r[n][k] != -1) {
            return r[n][k];
        }
        r[n][k] = R(n - 1, k - 1) + R(n - 1, k + 1);
        return r[n][k];
    }

    public static void main(String args[]) {
        try {
            in = new BufferedReader(new FileReader("brackets2num.in"));
            out = new BufferedWriter(new FileWriter("brackets2num.out"));
            r = new long[45][45];
            for (int i = 0; i < r.length; i++) {
                for (int j = 0; j < r[i].length; j++) {
                    r[i][j] = -1;
                }
            }
            r[0][0] = 1;
            long k = 0;
            int b = 0;
            String s = nextToken();
            int n = s.length();
            for (int i = 0; i < n; i++) {
                char c = s.charAt(i);
                //System.out.println(c);
                if (c == '(') {
                    //System.out.println("B++="+(b+1));
                    b++;
                } else {
                    //System.out.println("R("+(n-i-1)+","+(b+1)+")="+R(n-i-1, b+1));
                    k += R(n - i - 1, b + 1);
                    b--;
                }
            }
            out.write(k + "");
            in.close();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }
}
