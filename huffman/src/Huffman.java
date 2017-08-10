import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Collections;
import java.util.Comparator;
import java.util.StringTokenizer;
import java.util.Vector;


public class Huffman {
    static BufferedReader in;
    static BufferedWriter out;
    static StringTokenizer st;

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

    static int nextInt() {
        return Integer.parseInt(nextToken());
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

    static public long getDepth(Vertex v, int k) {
        if (v.children.size() == 0) return k * v.p;
        long res = 0;
        for (int i = 0; i < v.children.size(); ++i) {
            res += getDepth(v.children.get(i), k + 1);
        }
        return res;
    }

    public static void main(String args[]) {
        try {
            in = new BufferedReader(new FileReader("huffman.in"));
            out = new BufferedWriter(new FileWriter("huffman.out"));
            int n = nextInt();
            Vector<Vertex> v = new Vector<Vertex>();
            for (int i = 0; i < n; ++i) {
                long p = nextInt();
                v.add(new Vertex(p));
            }
            Comparator cmp = new MyComparator();
            while (v.size() > 1) {
                Collections.sort(v, cmp);
                Vertex v1 = v.get(0);
                Vertex v2 = v.get(1);
                Vertex vn = new Vertex(v1.p + v2.p);
                vn.children.add(v1);
                vn.children.add(v2);
                v.remove(0);
                v.remove(0);
                v.add(vn);
            }
            long d = getDepth(v.get(0), 0);
            out.write(d + "");
            in.close();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }

    static class Vertex {
        public String c;
        public long p;
        public Vector<Vertex> children;

        public Vertex(long p) {
            children = new Vector<Vertex>();
            this.p = p;
        }
    }

    static class MyComparator implements Comparator {
        public int compare(Object obj1, Object obj2) {
            Vertex v1 = (Vertex) obj1;
            Vertex v2 = (Vertex) obj2;
            if (v1.p < v2.p) return -1;
            if (v1.p > v2.p) return 1;
            return 0;
        }
    }
}
