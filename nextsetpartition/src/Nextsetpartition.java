import java.io.*;
import java.util.StringTokenizer;
import java.util.Vector;

public class Nextsetpartition {
    static BufferedReader in;
    static BufferedWriter out;
    static StringTokenizer st;
    static Vector<Integer> dob;

    static int nextInt() {
        return Integer.parseInt(nextToken());
    }

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

    static void print(int n, Vector<Vector<Integer>> v) throws IOException {
        out.write(n + " " + v.size() + "\n");
        for (int i = 0; i < v.size(); i++) {
            for (int j = 0; j < v.get(i).size(); j++) {
                out.write(v.get(i).get(j) + " ");
            }
            out.write("\n");
        }
        out.write("\n");
    }

    static void print(int n, Vector<Vector<Integer>> v, Vector<Integer> dob) throws IOException {
        out.write(n + " " + (v.size() + dob.size()) + "\n");
        for (int i = 0; i < v.size(); i++) {
            for (int j = 0; j < v.get(i).size(); j++) {
                out.write(v.get(i).get(j) + " ");
            }
            out.write("\n");
        }
        for (int i = 0; i < dob.size(); i++) {
            out.write(dob.get(i) + "\n");
        }
        out.write("\n");
        out.write("\n");
    }

    static void func(int set, int t, Vector<Vector<Integer>> v) {
        //System.out.println("func "+set+" "+t);
        int m = dob.size();
        for (int i = 0; i < dob.size(); i++) {
            if (dob.get(i) > t) {
                m = i;
                break;
            }
        }
        //p(dob);
        if (m != dob.size()) {
            //System.out.println("Flag 1");
            v.get(set).add(dob.get(m));
            dob.remove(m);
            //p(dob);
            return;
        } else if (v.get(set).size() > 2) {
            //System.out.println("Flag 2");
            int x = v.get(set).get(v.get(set).size() - 2);
            m = dob.size();
            for (int i = 0; i < dob.size(); i++) {
                if (dob.get(i) > x) {
                    m = i;
                    break;
                }
            }
            dob.insertElementAt(x, m);
            dob.add(v.get(set).get(v.get(set).size() - 1));
            //System.out.print(v.get(set).size());
            v.get(set).remove(v.get(set).size() - 1);//pop
            //System.out.print(v.get(set).size());
            t = v.get(set).get(v.get(set).size() - 1);
            v.get(set).remove(v.get(set).size() - 1);//pop
            //System.out.println("Flag1");
            func(set, t, v);
        } else {
            //System.out.println("Flag 3");
            int temp = v.get(set).get(v.get(set).size() - 1);
            v.get(set).remove(v.get(set).size() - 1);//pop
            for (int i = 0; i < dob.size(); i++) {
                v.get(set).add(dob.get(i));
            }
            v.get(set).add(temp);
            dob = v.get(set);
            v.remove(v.size() - 1);
            if (set > 0) {
                func(set - 1, v.get(set - 1).get(v.get(set - 1).size() - 1), v);
            } else {
                return;
            }
        }
    }

    static void p(Vector<Integer> v) {
        for (int i = 0; i < v.size(); i++) {
            System.out.print(v.get(i) + " ");
        }
        System.out.print("\n");
    }

    public static void main(String args[]) {
        try {
            in = new BufferedReader(new FileReader("nextsetpartition.in"));
            out = new BufferedWriter(new FileWriter("nextsetpartition.out"));
            int n = -1;
            int k = -1;
            n = nextInt();
            k = nextInt();
            do {
                Vector<Vector<Integer>> v = new Vector<Vector<Integer>>();
                dob = new Vector<Integer>();
                for (int i = 0; i < k; i++) {
                    Vector<Integer> u = new Vector<Integer>();
                    String s = in.readLine();
                    String[] sm = s.split("\\ ");
                    for (int j = 0; j < sm.length; j++) {
                        u.add(Integer.parseInt(sm[j]));
                    }
                    v.add(u);
                }

                if (v.get(k - 1).size() > 2) {
                    v.add(new Vector<Integer>());
                    v.get(k).add(v.get(k - 1).get(v.get(k - 1).size() - 2));
                    v.get(k - 1).remove(v.get(k - 1).size() - 2);
                    print(n, v);
                } else {
                    for (int i = 0; i < v.get(k - 1).size(); i++) {
                        dob.add(v.get(k - 1).get(i));
                    }
                    v.remove(v.size() - 1);
                    if (k > 1) {
                        //p(v.get(k-2));
                        func(k - 2, v.get(k - 2).get(v.get(k - 2).size() - 1), v);
                    }
                    print(n, v, dob);
                }

                n = nextInt();
                k = nextInt();
            } while (n != 0);
            in.close();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }

    static class Set {
        public int n;
        public int k;
        public Vector<Vector<Integer>> v;

        public Set(int n, int k, Vector<Vector<Integer>> v) {
            this.n = n;
            this.k = n;
            this.v = v;
        }
    }
}
