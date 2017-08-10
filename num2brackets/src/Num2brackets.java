import java.io.*;
import java.util.StringTokenizer;

public class Num2brackets {

    static BufferedReader in;
    static BufferedWriter out;
    static StringTokenizer st;
    static long[][] r;

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

    static int nextInt() {
        return Integer.parseInt(nextToken());
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
            in = new BufferedReader(new FileReader("num2brackets.in"));
            out = new BufferedWriter(new FileWriter("num2brackets.out"));
            int n = nextInt();
            long k = nextLong() + 1;
            r = new long[45][45];
            for (int i = 0; i < r.length; i++) {
                for (int j = 0; j < r[i].length; j++) {
                    r[i][j] = -1;
                }
            }
            r[0][0] = 1;
            int a = 2 * n;
            int b = 0;
            while (a > 0) {
                //System.out.println("R("+(a-1)+","+(b+1)+")="+R(a-1, b+1)+"; k="+k+"; 2*n="+2*n);
                if (R(a - 1, b + 1) >= k && a + b <= 2 * n) {
                    a--;
                    b++;
                    out.write('(');
                } else {
                    k = k - R(a - 1, b + 1);
                    a--;
                    b--;
                    out.write(')');
                }
            }
            in.close();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }
}
