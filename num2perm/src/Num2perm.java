import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.StringTokenizer;


public class Num2perm {

    static BufferedReader in;
    static BufferedWriter out;
    static StringTokenizer st;

    public static void main(String args[]) {
        try {
            in = new BufferedReader(new FileReader("num2perm.in"));
            out = new BufferedWriter(new FileWriter("num2perm.out"));
            int n = nextInt();
            long id = nextLong() + 1;
            boolean used[] = new boolean[n + 1];
            long res[] = new long[n];
            long fact[] = {1, 1, 2, 6, 24, 120, 720, 5040, 40320, 362880, 3628800, 39916800, 479001600, 6227020800L, 87178291200L, 1307674368000L, 20922789888000L, 355687428096000L};
            for (int i = 0; i < n; i++) {
                long blockNum = (id - 1) / fact[n - i - 1] + 1;
                int j = notUsed(used, blockNum);
                res[i] = j;
                used[j] = true;
                id = (id - 1) % fact[n - i - 1] + 1;
            }
            for (int i = 0; i < res.length; i++) {
                out.write(res[i] + " ");
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

    public static int notUsed(boolean[] used, long blockNum) {
        int j = 0;
        int pos = 0;
        for (j = 1; j < used.length; j++) {
            if (!used[j]) {
                pos++;
            }
            if (blockNum == pos) {
                break;
            }
        }
        return j;
    }
}
