import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Arrays;
import java.util.StringTokenizer;


public class Bwt {
    static BufferedReader in;
    static BufferedWriter out;
    static StringTokenizer st;

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

    public static void main(String args[]) {
        try {
            in = new BufferedReader(new FileReader("bwt.in"));
            out = new BufferedWriter(new FileWriter("bwt.out"));
            String s = nextToken();

            String[] ss = gen(s);
            Arrays.sort(ss);
            for (int i = 0; i < ss.length; i++) {
                out.write(ss[i].charAt(s.length() - 1));
            }
            in.close();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }

    static String[] gen(String s) {
        String[] temp = new String[s.length()];
        StringBuilder sb = new StringBuilder(s);
        for (int i = 0; i < s.length(); i++) {
            char c = sb.charAt(0);
            sb.delete(0, 1);
            sb.append(c);
            temp[i] = sb.toString();
        }
        return temp;
    }
}
