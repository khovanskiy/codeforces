import java.io.*;
import java.util.StringTokenizer;
import java.util.Vector;

public class Nextchoose {

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
            in = new BufferedReader(new FileReader("nextchoose.in"));
            out = new BufferedWriter(new FileWriter("nextchoose.out"));
            int n = nextInt();
            int k = nextInt();
            int[] mas = new int[k];
            for (int i = 0; i < k; i++) {
                mas[i] = nextInt() - 1;
            }
            if (mas[0] == n - k) {
                out.write("-1");
            } else {
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
    }
}
