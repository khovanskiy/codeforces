import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;


public class Mtf {
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

    public static void main(String args[]) {
        try {
            in = new BufferedReader(new FileReader("mtf.in"));
            out = new BufferedWriter(new FileWriter("mtf.out"));
            String s = nextToken();
            List<String> list = new ArrayList<String>();
            for (int i = 1; i <= 26; i++) {
                list.add((char) (i + 96) + "");
            }
            for (int i = 0; i < s.length(); ++i) {
                char c = s.charAt(i);
                int p = list.indexOf(c + "");
                out.write((p + 1) + " ");
                list.remove(p);
                list.add(0, c + "");
            }
            in.close();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }
}
