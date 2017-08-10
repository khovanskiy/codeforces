import java.io.*;
import java.util.StringTokenizer;
import java.util.Vector;

public class Brackets {

    static BufferedReader in;
    static BufferedWriter out;
    static StringTokenizer st;
    static Vector<int[]> v;
    static int[] c;

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

    static String pc(byte a) {
        if (a == -1) {
            return "(";
        } else {
            return ")";
        }
    }

    static void print(byte[] mas) throws IOException {
        for (int i = 0; i < mas.length; i++) {
            out.write(pc(mas[i]));
        }
        out.write("\n");
    }

    static byte[] next(byte[] mas) {
        int n = mas.length;
        int d = 0;
        byte[] temp = new byte[n];
        for (int i = n - 1; i >= 0; i--) {
            d += mas[i];
            if (mas[i] == -1 && d > 0) {
                d--;
                int open = (n - i - 1 - d) / 2;
                int close = n - i - 1 - open;
                int j = 0;
                for (; j < i; j++) {
                    temp[j] = mas[j];
                }
                temp[j] = 1;
                for (int k = 0; k < open; k++) {
                    j++;
                    temp[j] = -1;
                }
                for (int k = 0; k < close; k++) {
                    j++;
                    temp[j] = 1;
                }
                break;
            }
        }
        return temp;
    }

    static int C(int n) {
        c[0] = 1;
        c[1] = 1;
        if (c[n] != 0) return c[n];
        int res = 0;
        for (int i = 0; i <= n - 1; i++) {
            res += C(i) * C(n - 1 - i);
        }
        c[n] = res;
        return res;
    }

    public static void main(String args[]) {
        try {
            in = new BufferedReader(new FileReader("brackets.in"));
            out = new BufferedWriter(new FileWriter("brackets.out"));
            int n = nextInt();
            byte[] mas = new byte[2 * n];
            c = new int[2 * n];
            for (int i = 0; i < n; i++) {
                mas[i] = -1;
            }
            for (int i = n; i < 2 * n; i++) {
                mas[i] = 1;
            }
            print(mas);
            int k = C(n);
            for (int i = 1; i < k; i++) {
                mas = next(mas);
                print(mas);
            }
            in.close();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }
}
