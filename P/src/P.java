import java.math.BigDecimal;
import java.util.Arrays;


public class P {
	
	public static <T> void trace(T o) {
		System.out.println(o.toString());
	}
	
	public static double[] P(int[] x, double p) {
		double[] pp = new double[x.length];
		for (int i = 0; i < x.length; ++i) {
			BigDecimal result;
			if (i == 0) {
				result = BigDecimal.valueOf(1 - p);
			} else if (i == x.length - 1) {
				result = BigDecimal.valueOf(p).pow(i);
			} else {
				result = BigDecimal.valueOf(1 - p).multiply(BigDecimal.valueOf(p).pow(i));
			}
			pp[i] = result.doubleValue();
		}
		return pp;
	}
	
	public static double sum(double[] p) {
		BigDecimal s = BigDecimal.ZERO;
		for (int i = 0; i < p.length; ++i) {
			s = s.add(BigDecimal.valueOf(p[i]));
		}
		return s.doubleValue();
	}
	
	public static double ExpectedValue(int[] x, double[] p) {
		assert(x.length == p.length);
		
		BigDecimal e = BigDecimal.ZERO;
		int n = x.length;
		for (int i = 0; i < n; ++i) {
			e = e.add(BigDecimal.valueOf(p[i]).multiply(BigDecimal.valueOf(x[i])));
		}
		
		return e.doubleValue();
	}
	
	public static double Variance(int[] x, double[] p) {
		int[] temp = new int[x.length];
		for (int i = 0; i < temp.length; ++i) {
			temp[i] = x[i] * x[i];
		}
		return BigDecimal.valueOf(ExpectedValue(temp, p)).subtract(BigDecimal.valueOf(ExpectedValue(x, p)).pow(2)).doubleValue();
	}
	
	public static void main(String[] args) {
		int[] x = {0, 1, 2, 3, 4, 5};
		double p = 0.6;
		double[] pp = P(x, p);
		double e = ExpectedValue(x, pp);
		trace("X = " + Arrays.toString(x));
		trace("P = " + p + " Q = " + (1 - p));
		trace("P[X] = " + Arrays.toString(pp) + " Sum = " + sum(pp));
		trace("E[X] = " + e + " " + ((1 - p)/p));
		double d = Variance(x, pp);
		trace("D[X] = " + d);
		trace("Sigma[x] = " + Math.sqrt(d));
	}
}
