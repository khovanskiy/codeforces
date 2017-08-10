import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.StringTokenizer;


public class Perm2num {

    static BufferedReader in;
    static BufferedWriter out;
    static StringTokenizer st;

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
            in = new BufferedReader(new FileReader("perm2num.in"));
            out = new BufferedWriter(new FileWriter("perm2num.out"));
            int n = nextInt();
            int mas[] = new int[n];
            for (int i = 0; i < n; i++) {
                mas[i] = nextInt();
            }
            boolean used[] = new boolean[n + 1];
            long fact[] = {1, 1, 2, 6, 24, 120, 720, 5040, 40320, 362880, 3628800, 39916800, 479001600, 6227020800L, 87178291200L, 1307674368000L, 20922789888000L, 355687428096000L};
            long id = 0;
            for (int i = 0; i < n; i++) {
                long blockNum = getBlockNum(used, mas[i]);
                id += (blockNum - 1) * fact[n - 1 - i];
                used[mas[i]] = true;
            }
            out.write(id + "");
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

    static long getBlockNum(boolean[] used, int value) {
        long blockNum = 0;
        for (int i = 1; i < used.length; i++) {
            if (!used[i]) {
                blockNum++;
                if (i == value) {
                    break;
                }
            }
        }
        return blockNum;
    }
}
