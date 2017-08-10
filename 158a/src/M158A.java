import java.util.Scanner;


public class M158A {
    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int k = in.nextInt();
        int mas[] = new int[n];
        for (int i = n - 1; i >= 0; i--) {
            mas[i] = in.nextInt();
        }
        int sum = 0;
        for (int i = 0; i < n; i++) {
            if (mas[i] >= mas[n - k] && mas[i] > 0) {
                sum++;
            }
        }
        System.out.print(sum);
    }
}
