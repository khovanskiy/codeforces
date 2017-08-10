import java.io.*;
import java.util.StringTokenizer;
import java.util.Vector;

public class Nextvector {

    static BufferedReader in;
    static BufferedWriter out;
    static StringTokenizer st;
    static Vector<int[]> v;

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

    public static void main(String args[]) {
        try {
            in = new BufferedReader(new FileReader("nextvector.in"));
            out = new BufferedWriter(new FileWriter("nextvector.out"));
            String s = nextToken();
            int[] mas = new int[s.length()];
            for (int i = 0; i < s.length(); i++) {
                mas[i] = (int) s.charAt(i) - 48;
            }
            if (isNull(mas)) {
                out.write("-\n");
            } else {
                print(prev(mas.clone()));
            }
            if (isMax(mas)) {
                out.write("-\n");
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

    static boolean isMax(int[] mas) {
        for (int i = 0; i < mas.length; i++) {
            if (mas[i] != 1) {
                return false;
            }
        }
        return true;
    }

    static boolean isNull(int[] mas) {
        for (int i = 0; i < mas.length; i++) {
            if (mas[i] != 0) {
                return false;
            }
        }
        return true;
    }

    static int[] prev(int[] mas) {
        int i = mas.length - 1;
        int b = 1;
        do {
            int a = mas[i];
            mas[i] = a ^ b;
            b = a & b ^ 1;
            i--;
        } while (b != 0 && i >= 0);
        return mas;
    }

    static int[] next(int[] mas) {
        int i = mas.length - 1;
        int b = 1;
        do {
            int a = mas[i];
            mas[i] = a ^ b;
            b = a & b;
            i--;
        } while (b != 0 && i >= 0);
        return mas;
    }

    static void print(int[] mas) throws IOException {
        for (int i = 0; i < mas.length; i++) {
            out.write(mas[i] + "");
        }
        out.write("\n");
    }

    static class R {
        int[] mas;
        int l;

        public R(int[] mas, int l) {
            this.mas = mas;
            this.l = l;
        }
    }
}
