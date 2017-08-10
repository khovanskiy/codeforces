import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.StringTokenizer;


public class Lzw {
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
            in = new BufferedReader(new FileReader("lzw.in"));
            out = new BufferedWriter(new FileWriter("lzw.out"));
            String s = nextToken();
            HashMap<String, Integer> map = new HashMap<String, Integer>();
            for (int i = 0; i < 26; i++) {
                //System.out.println(i+" "+(char)(i+97));
                map.put((char) (i + 97) + "", i);
            }
            int j = 26;
            String w = s.charAt(0) + "";
            for (int i = 1; i < s.length(); i++) {
                char c = s.charAt(i);
                String wk = w + c;

                wk += c;
                if (map.containsKey(wk)) {
                    w = wk;
                } else {
                    out.write(map.get(w) + " ");
                    map.put(wk, j++);
                    w = c + "";
                }
            }
            out.write(map.get(w) + " ");
            in.close();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }
}
