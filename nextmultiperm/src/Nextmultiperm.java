import java.io.*;
import java.util.StringTokenizer;

public class Nextmultiperm {

    static BufferedReader in;
    static BufferedWriter out;
    static StringTokenizer st;
    static boolean[] used;
    static int[] arr;
    static int[] group;

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

    static void print(long[][] mas) {
        for (int i = 0; i < mas.length; i++) {
            for (int j = 0; j < mas[i].length; j++) {
                System.out.print(mas[i][j] + " ");
            }
            System.out.print("\n");
        }
    }

    static boolean isNulling(int[] mas) {
        for (int i = 0; i < mas.length; i++) {
            if (mas[i] != 0) {
                return false;
            }
        }
        return true;
    }

    public static void main(String args[]) {
        try {
            in = new BufferedReader(new FileReader("nextmultiperm.in"));
            out = new BufferedWriter(new FileWriter("nextmultiperm.out"));
            int n = nextInt();
            int[] mas = new int[n];
            for (int i = 0; i < n; i++) {
                mas[i] = nextInt() - 1;
            }
            mas = next(mas, n);
            print(mas);

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

    static void print(int[] mas) throws IOException {
        for (int i = 0; i < mas.length; i++) {
            out.write((mas[i] + 1) + " ");
        }
        out.write("\n");
    }

    static int[] next(int[] mas, int n) {
        int[] p = new int[n];
        int[] temp = new int[n];
        int x = n - 2;
        while (x >= 0 && mas[x] >= mas[x + 1]) {
            x--;
        }
        if (x != -1) {
            int min = mas[x + 1];
            for (int i = x + 1; i < n; i++) {
                if (mas[i] > mas[x] && mas[i] < min) {
                    min = mas[i];
                }
            }
            p[min] = -1;
            for (int i = x; i < n; i++) {
                p[mas[i]]++;
            }
            int a = 0;
            for (int i = 0; i < x; i++, a++) {
                temp[a] = mas[i];
            }
            temp[a++] = min;
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < p[i]; j++, a++) {
                    temp[a] = i;
                }
            }
        } else {
            for (int i = 0; i < n; i++) {
                temp[i] = -1;
            }
        }
        return temp;
    }
}
