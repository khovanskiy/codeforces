import java.util.Scanner;


public class M1A {
    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        long n = in.nextLong();
        long m = in.nextLong();
        long a = in.nextLong();
        long c = (long) Math.ceil((double) n / a) * (long) Math.ceil((double) m / a);
        System.out.println(c);
    }
}
