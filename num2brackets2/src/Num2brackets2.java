import java.io.*;
import java.math.BigInteger;
import java.util.StringTokenizer;

public class Num2brackets2 {

    static BufferedReader in;
    static BufferedWriter out;
    static StringTokenizer st;
    static BigInteger[][] d;

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
            in = new BufferedReader(new FileReader("num2brackets2.in"));
            out = new BufferedWriter(new FileWriter("num2brackets2.out"));
            int n = nextInt();
            BigInteger m = new BigInteger(nextToken()).add(BigInteger.ONE);
            StringBuilder sb = new StringBuilder();

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
            for (int i = 2 * n - 1; i >= 0; i--) {
                BigInteger temp = BigInteger.ZERO;
                if (depth < n) {
                    int x = i - 1 - depth;
                    temp = d[i][depth + 1].shiftLeft(x / 2);
                }
                if (m.compareTo(temp) <= 0) {
                    sb.append('(');
                    v[index] = '(';
                    index++;
                    depth++;
                } else {
                    m = m.subtract(temp);
                    temp = BigInteger.ZERO;
                    if (index > 0 && depth >= 1) {
                        if (v[index - 1] == '(') {
                            int x = i + 1 - depth;
                            temp = d[i][depth - 1].shiftLeft(x / 2);
                        }
                    }
                    if (m.compareTo(temp) <= 0) {
                        sb.append(')');
                        index--;
                        depth--;
                    } else {
                        m = m.subtract(temp);
                        temp = BigInteger.ZERO;
                        if (depth < n) {
                            int x = i - 1 - depth;
                            temp = d[i][depth + 1].shiftLeft(x / 2);
                        }
                        if (m.compareTo(temp) <= 0) {
                            sb.append('[');
                            v[index] = '[';
                            depth++;
                            index++;
                        } else {
                            m = m.subtract(temp);
                            sb.append(']');
                            index--;
                            depth--;
                        }
                    }
                }
            }
            out.write(sb.toString() + "");
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
}
