import java.io.*;
import java.util.StringTokenizer;


public class Part2sets {

    static BufferedReader in;
    static BufferedWriter out;
    static StringTokenizer st;
    static boolean[] used;
    static int[] arr;
    static int[] group;

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
        for (int i = 0; i < mas.length; i++) {
            out.write((mas[i]) + " ");
        }
        out.write("\n");
    }

    static void print(long[][] mas) {
        for (int i = 0; i < mas.length; i++) {
            for (int j = 0; j < mas[i].length; j++) {
                System.out.print(mas[i][j] + " ");
            }
            System.out.print("\n");
        }
    }

    static void gen(int l, int c, int n, int k) throws IOException {
        if (l == n) {
            //System.out.print("�������:\n");
            out.write((arr[0] + 1) + " ");
            for (int i = 1; i < arr.length; i++) {
                if (group[i] != group[i - 1]) {
                    out.write("\n");
                }
                out.write((arr[i] + 1) + " ");
            }
            out.write("\n\n");
            return;
        }
        if (c < k) {
            int min = 0;
            while (used[min]) {
                min++;
            }
            //System.out.println("��� "+(min+1));
            used[min] = true;
            arr[l] = min;
            //System.out.println("a["+l+"]="+min);
            group[l] = c;
            //System.out.println("-b["+l+"]="+c);
            gen(l + 1, c + 1, n, k);
            used[min] = false;
        }
        if (c + n - l - 1 >= k) {
            for (int i = arr[l - 1]; i < n; i++) {
                if (!used[i]) {
                    used[i] = true;
                    arr[l] = i;
                    //System.out.println("-a["+l+"]="+i);
                    group[l] = c - 1;
                    //System.out.println("b["+l+"]="+(c-1));
                    gen(l + 1, c, n, k);
                    used[i] = false;
                }
            }
        }
    }

    public static void main(String args[]) {
        try {
            in = new BufferedReader(new FileReader("part2sets.in"));
            out = new BufferedWriter(new FileWriter("part2sets.out"));
            int n = nextInt();
            int k = nextInt();
            used = new boolean[n];
            arr = new int[n];
            group = new int[n];

            arr[0] = 0;
            group[0] = 0;
            used[0] = true;
            gen(1, 1, n, k);

            in.close();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }
}
