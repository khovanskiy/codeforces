import java.io.*;
import java.math.BigInteger;
import java.util.StringTokenizer;

public class Brackets2num2 {

    static BufferedReader in;
    static BufferedWriter out;
    static StringTokenizer st;
    static BigInteger[][] d;

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
            in = new BufferedReader(new FileReader("brackets2num2.in"));
            out = new BufferedWriter(new FileWriter("brackets2num2.out"));
            String s = nextToken();
            int n = s.length() / 2;
            d = new BigInteger[2 * n + 1][n + 1];
            for (int i = 0; i < 2 * n + 1; i++) {
                for (int j = 0; j < n + 1; j++) {
                    d[i][j] = BigInteger.ZERO;
                }
            }
            d[0][0] = BigInteger.ONE;
            for (int i = 0; i < 2 * n; i++) {
                for (int j = 0; j < n + 1; j++) {
                    if (j > 0) {
                        d[i + 1][j - 1] = d[i + 1][j - 1].add(d[i][j]);
                    }
                    if (j < n) {
                        d[i + 1][j + 1] = d[i + 1][j + 1].add(d[i][j]);
                    }
                }
            }
            int depth = 0;
            int index = 0;
            char[] v = new char[2 * n + 1];
            BigInteger m = BigInteger.ZERO;
            for (int i = 0; i < 2 * n; i++) {
                BigInteger temp = BigInteger.ZERO;
                char c = s.charAt(i);
                if (depth < n) {
                    int x = 2 * n - 2 - i - depth;
                    temp = d[2 * n - 1 - i][depth + 1].shiftLeft(x / 2);
                }
                if (c == '(') {
                    v[index] = c;
                    index++;
                    depth++;
                } else {
                    m = m.add(temp);
                    if (c == ')') {
                        if (index > 0) {
                            index--;
                            depth--;
                        }
                    } else {
                        if (depth > 0 && index > 0) {
                            if (v[index - 1] == '(') {
                                int x = 2 * n - i - depth;
                                m = m.add(d[2 * n - 1 - i][depth - 1].shiftLeft(x / 2));
                            }
                        }
                        if (c == '[') {
                            v[index] = c;
                            index++;
                            depth++;
                        } else {
                            if (depth < n) {
                                int x = 2 * n - 2 - i - depth;
                                m = m.add(d[2 * n - 1 - i][depth + 1].shiftLeft(x / 2));
                            }
                            index--;
                            depth--;
                        }
                    }
                }
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
