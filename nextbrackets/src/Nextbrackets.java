import java.io.*;
import java.util.StringTokenizer;
import java.util.Vector;

public class Nextbrackets {

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
            in = new BufferedReader(new FileReader("nextbrackets.in"));
            out = new BufferedWriter(new FileWriter("nextbrackets.out"));
            String s = nextToken();
            int n = s.length();
            byte[] mas = new byte[n];
            for (int i = 0; i < n; i++) {
                if (s.charAt(i) == '(') {
                    mas[i] = -1;
                } else {
                    mas[i] = 1;
                }
            }
            mas = next(mas);
            if (check(mas)) {
                print(mas);
            } else {
                out.write("-");
            }
            in.close();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }

    static void print(byte[] mas) throws IOException {
        for (int i = 0; i < mas.length; i++) {
            out.write(pc(mas[i]));
        }
        out.write("\n");
    }

    static String pc(byte a) {
        if (a == -1) {
            return "(";
        } else {
            return ")";
        }
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

    static boolean check(byte[] mas) {
        for (int i = 0; i < mas.length; i++) {
            if (mas[i] != 0) {
                return true;
            }
        }
        return false;
    }
}
