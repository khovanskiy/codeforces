import java.io.*;
import java.util.StringTokenizer;
import java.util.Vector;

public class Nextpartition {

    static BufferedReader in;
    static BufferedWriter out;
    static StringTokenizer st;
    static long[][] r;

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
            in = new BufferedReader(new FileReader("nextpartition.in"));
            out = new BufferedWriter(new FileWriter("nextpartition.out"));
            String s = nextToken();
            String[] sm = s.split("=");
            int n = Integer.parseInt(sm[0]);
            sm = sm[1].split("\\+");
            int l = sm.length;
            int[] mas = new int[n];
            for (int i = 0; i < l; i++) {
                mas[i] = Integer.parseInt(sm[i]);
            }
            if (l == 1) {
                out.write("No solution");
            } else {
                Vector<Integer> temp = next(mas, l);
                out.write(n + "=");

                for (int i = 0; i < temp.size() - 1; i++) {
                    out.write(temp.get(i) + "+");
                }
                out.write(temp.get(temp.size() - 1) + "");
            }
            in.close();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }

    static Vector<Integer> next(int[] mas, int l) {
        Vector<Integer> temp = new Vector<Integer>();
        int prev = mas[l - 2] + 1;
        mas[l - 2] += mas[l - 1];
        int k = mas[l - 2] / prev;
        for (int i = 0; i < l - 2; i++) {
            temp.add(mas[i]);
        }
        for (int i = 0; i < k - 1; i++) {
            temp.add(prev);
        }
        temp.add(prev + mas[l - 2] % prev);
        return temp;
    }
}
