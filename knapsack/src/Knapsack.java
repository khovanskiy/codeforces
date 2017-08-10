import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayDeque;
import java.util.StringTokenizer;

public class Knapsack {
    static BufferedReader in;
    static BufferedWriter out;
    static StringTokenizer st;
    static String s1;
    static String s2;
    static int[][] d;

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

    static void print(int[] d) {
        for (int i = 0; i < d.length; i++) {
            System.out.print(d[i] + " ");
        }
        System.out.print("\n");
    }

    static void print(int[][] d) {
        for (int i = 0; i < d.length; i++) {
            for (int j = 0; j < d[i].length; j++) {
                System.out.print(d[i][j] + " ");
            }
            System.out.print("\n");
        }
    }

    public static void main(String args[]) {
        try {
            in = new BufferedReader(new FileReader("knapsack.in"));
            out = new BufferedWriter(new FileWriter("knapsack.out"));
            int N = nextInt();
            int M = nextInt();
            int[] m = new int[N];
            int[] c = new int[N];
            for (int i = 0; i < N; i++) {
                m[i] = nextInt();
            }
            for (int i = 0; i < N; i++) {
                c[i] = nextInt();
            }
            int[][] d = new int[N + 1][M + 1];
            for (int i = 0; i <= M; i++) {
                d[0][i] = 0;
            }

            for (int i = 1; i <= N; i++) {
                for (int j = 0; j <= M; j++) {
                    d[i][j] = d[i - 1][j];
                    if (j >= m[i - 1] && d[i - 1][j - m[i - 1]] + c[i - 1] > d[i][j]) {
                        d[i][j] = d[i - 1][j - m[i - 1]] + c[i - 1];
                    }
                }
            }

            ArrayDeque<Integer> stack = new ArrayDeque<Integer>();

            int i = N;
            int j = M;

            while (d[i][j] != 0) {
                if (d[i - 1][j] == d[i][j]) {
                    i--;
                } else {
                    stack.push(i);
                    j -= m[i - 1];
                    i--;
                }
            }
            out.write(stack.size() + "\n");

            while (!stack.isEmpty()) {
                int el = stack.pop();
                out.write(el + " ");
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
}
