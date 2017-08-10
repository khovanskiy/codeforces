import java.io.*;
import java.util.StringTokenizer;


public class Nextperm {

    static BufferedReader in;
    static BufferedWriter out;
    static StringTokenizer st;
    static long fact[] = {1, 1, 2, 6, 24, 120, 720, 5040, 40320, 362880, 3628800, 39916800, 479001600, 6227020800L, 87178291200L, 1307674368000L, 20922789888000L, 355687428096000L};

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
            in = new BufferedReader(new FileReader("nextperm.in"));
            out = new BufferedWriter(new FileWriter("nextperm.out"));
            int n = nextInt();
            int mas[] = new int[n];
            for (int i = 0; i < n; i++) {
                mas[i] = nextInt();
            }
            int[] nulling = new int[n];
            for (int i = 0; i < n; i++) {
                nulling[i] = 0;
            }
            if (isBegin(mas)) {
                print(nulling);
            } else {
                print(prev(mas.clone()));
            }
            if (isEnd(mas)) {
                print(nulling);
            } else {
                print(next(mas.clone()));
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

    static boolean isBegin(int[] mas) {
        int n = mas.length;
        for (int i = 0; i < n; i++) {
            if (mas[i] != i + 1) {
                return false;
            }
        }
        return true;
    }

    static boolean isEnd(int[] mas) {
        int n = mas.length;
        for (int i = 0; i < n; i++) {
            if (mas[i] != n - i) {
                return false;
            }
        }
        return true;
    }

    static int[] prev(int[] mas) {
        int k = -1;
        int n = mas.length;
        for (int i = 0; i < n - 1; i++) {
            if (mas[i] > mas[i + 1]) {
                k = i;
            }
        }
        int l = k;
        for (int i = k; i < n; i++) {
            if (mas[k] > mas[i]) {
                l = i;
            }
        }
        int t;
        t = mas[k];
        mas[k] = mas[l];
        mas[l] = t;
        int p = (n - k - 1) / 2 + k + 1;
        for (int i = k + 1; i < p; i++) {
            t = mas[i];
            mas[i] = mas[n - i + k];
            mas[n - i + k] = t;
        }
        return mas;
    }

    static int[] next(int[] mas) {
        int k = -1;
        int n = mas.length;
        for (int i = 0; i < n - 1; i++) {
            if (mas[i] < mas[i + 1]) {
                k = i;
            }
        }
        int l = k;
        for (int i = k; i < n; i++) {
            if (mas[k] < mas[i]) {
                l = i;
            }
        }
        int t;
        t = mas[k];
        mas[k] = mas[l];
        mas[l] = t;
        int p = (n - k - 1) / 2 + k + 1;
        for (int i = k + 1; i < p; i++) {
            t = mas[i];
            mas[i] = mas[n - i + k];
            mas[n - i + k] = t;
        }
        return mas;
    }

    static void print(int[] mas) throws IOException {
        for (int i = 0; i < mas.length; i++) {
            out.write(mas[i] + " ");
        }
        out.write("\n");
    }
}
