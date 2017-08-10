import java.io.*;
import java.util.StringTokenizer;
import java.util.Vector;

public class Choose {

    static BufferedReader in;
    static BufferedWriter out;
    static StringTokenizer st;
    static Vector<int[]> v;

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

    public static void main(String args[]) {
        try {
            in = new BufferedReader(new FileReader("choose.in"));
            out = new BufferedWriter(new FileWriter("choose.out"));
            int n = nextInt();
            int k = nextInt();
            if (n == k) {
                for (int i = 0; i < k; i++) {
                    out.write((i + 1) + " ");
                }
                out.write("\n");
            } else {
                int[] mas = new int[k];
                for (int i = 0; i < k; i++) {
                    mas[i] = i;
                    out.write((mas[i] + 1) + " ");
                }
                out.write("\n");
                cgen(mas, n, k);
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

    static void cgen(int[] mas, int n, int k) throws IOException {
        while (true) {
            if (mas[k - 1] < n - 1) {
                mas[k - 1]++;
            } else {
                for (int i = k - 2; i >= 0; i--) {
                    if (mas[i] < n - k + i) {
                        mas[i]++;
                        for (int j = i + 1; j < k; j++) {
                            mas[j] = mas[j - 1] + 1;
                        }
                        break;
                    }
                }
            }
            for (int i = 0; i < k; i++) {
                out.write((mas[i] + 1) + " ");
            }
            out.write("\n");
            if (mas[0] == n - k) {
                return;
            }
        }
    }
}
