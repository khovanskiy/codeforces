/* 
 * Primitive recursive functions
 * 
 * Copyright (c) 2013 Nayuki Minase. All rights reserved.
 * http://nayuki.eigenstate.org/page/primitive-recursive-functions
 */

import java.util.Arrays;


public abstract class Prf {
	
	/* ---- Instance methods ---- */
	
	public Prf() {}
	
	// We can use long instead of bigint because PRFs work in unary,
	// so it'll take forever to overflow a long anyway
	public abstract long eval(long... xs);
	
	public abstract String toString();
	
	
	
	/* ---- Constants/factories for primitive recursive functions ---- */
	
	// Zero function: Z(x) = 0
	public static Prf Z = new Prf() {
		public long eval(long... xs) {
			if (xs.length != 1 || xs[0] < 0)
				throw new IllegalArgumentException();
			return 0;
		}
		public String toString() {
			return "Z";
		}
	};
	
	
	// Successor function: N(x) = x + 1
	public static Prf N = new Prf() {
		public long eval(long... xs) {
			if (xs.length != 1 || xs[0] < 0)
				throw new IllegalArgumentException();
			return xs[0] + 1;
		}
		public String toString() {
			return "S";
		}
	};
	
	
	// Projection function: I_{n,i}(x_0, ..., x_{n-1}) = x_i
	// n is the arity of the function, with n > 0. i is the index to take.
	public static Prf U(final int n, final int i) {
		if (n <= 0 || i < 0 || i >= n)
			throw new IllegalArgumentException();
		return new Prf() {
			public long eval(long... xs) {
				if (xs.length != n)
					throw new IllegalArgumentException();
				for (long x : xs) {
					if (x < 0)
						throw new IllegalArgumentException();
				}
				return xs[i];
			}
			public String toString() {
				return String.format("I(%d,%d)", n, i);
			}
		};
	}
	
	
	// Composition function: C_{f, g_0, ..., g_{k-1}}(xs) = f(g_0(xs), ..., g_{k-1}(xs))
	public static Prf S(final Prf f, Prf... gs) {
		if (f == null || gs == null)
			throw new NullPointerException();
		if (gs.length == 0)
			throw new IllegalArgumentException();
		final Prf[] myGs = gs.clone();  // Defensive copy
		for (Prf g : myGs) {
			if (g == null)
				throw new NullPointerException();
		}
		return new Prf() {
			public long eval(long... xs) {
				long[] temp = new long[myGs.length];
				for (int i = 0; i < myGs.length; i++)
					temp[i] = myGs[i].eval(xs);
				return f.eval(temp);
			}
			public String toString() {
				StringBuilder sb = new StringBuilder("C(").append(f).append(", [");
				boolean head = true;
				for (Prf g : myGs) {
					if (head) head = false;
					else sb.append(", ");
					sb.append(g);
				}
				return sb.append("])").toString();
			}
		};
	}
	
	
	// Primitive recursion: R_{f,g}(y, xs) = if (y == 0) then (f xs) else g(R_{f,g}(y-1, xs), y-1, xs)
	public static Prf R(final Prf f, final Prf g) {
		return new Prf() {
			// Efficient evaluation - less iteration overhead (faster) and does not recurse on self (constant stack space)
			public long eval(long... xs) {
				if (xs.length < 2)
					throw new IllegalArgumentException();
				long val = f.eval(Arrays.copyOfRange(xs, 1, xs.length));
				long[] temp = new long[xs.length + 1];
				System.arraycopy(xs, 1, temp, 2, xs.length - 1);
				for (long i = 0, n = xs[0]; i < n; i++) {
					temp[0] = val;
					temp[1] = i;
					val = g.eval(temp);
				}
				return val;
			}
			// Naive evaluation - directly from the mathematical definition
			public long evalNaive(long... xs) {
				if (xs.length < 2)
					throw new IllegalArgumentException();
				long y = xs[0];
				if (y == 0)
					return f.eval(Arrays.copyOfRange(xs, 1, xs.length));
				else {
					long[] tempA = xs.clone();
					tempA[0] = y - 1;
					long[] tempB = new long[xs.length + 1];
					tempB[0] = eval(tempA);
					System.arraycopy(tempA, 0, tempB, 1, tempA.length);
					return g.eval(tempB);
				}
			}
			public String toString() {
				return String.format("R(%s, %s)", f, g);
			}
		};
	}
	
	
	
	/* ---- Library of primitive recursive functions ---- */
	
	// The ordering is unnatural (especially compared to the Haskell version) because some functions depend on others, and the dependency must be at the top.
	
	// -- Early functions --
	
	// Constant: konst_{n}(x) = n
	// This is actually a PRF generator
	public static Prf konst(int n) {
		if (n < 0)
			throw new IllegalArgumentException();
		else if (n == 0)
			return Z;
		else
			return S(N, konst(n - 1));
	}
	
	// Is zero: z(x, y) = if x == 0 then 1 else 0
	public final static Prf z = S(R(konst(1), S(Z, U(3,0))), U(1,0), Z);
	
	// Multiplex/select: mux(x, y, z) = if x == true then y else z. (x is Boolean; y and z are numbers)
	public final static Prf Mux = R(U(2,1), U(4,2));
	
	
	// -- Boolean functions --
	// 0 means false, 1 means true, and all other input values yield arbitrary output values
	
	// Negation (NOT): not(x)
	public final static Prf not = z;
	
	// Conjunction (AND): and(x, y)
	public final static Prf and = R(Z, U(3,2));
	
	// Disjunction (OR): or(x, y)
	public final static Prf or = R(U(1,0), S(N, U(3,1)));
	
	// Exclusive OR (XOR): xor(x, y)
	public final static Prf xor = R(U(1,0), S(not, U(3,2)));
	
	
	// -- Arithmetic functions --
	
	// Predecessor: pred(0) = 0; pred(x) = x - 1
	public final static Prf pred = S(R(Z, U(3,1)), U(1,0), Z);
	
	// Addition/sum: add(x, y) = x + y
	public final static Prf plus = R(U(1,0), S(N, U(3,0)));
	
	// Reverse subtraction: subrev(x, y) = max(y - x, 0)
	public final static Prf subrev = R(U(1,0), S(pred, U(3,0)));
	
	// Subtraction/difference: sub(x, y) = max(x - y, 0)
	public final static Prf sub = S(subrev, U(2,1), U(2,0));
	
	// Absolute difference: diff(x, y) = abs(x - y)
	public final static Prf diff = S(plus, sub, subrev);
	
	// Minimum: min(x, y) = if x <= y then x else y
	public final static Prf min = S(subrev, subrev, U(2,1));
	
	// Maximum: max(x, y) = if x >= y then x else y
	public final static Prf max = S(plus, subrev, U(2,0));
	
	// Multiplication/product: mul(x, y) = x * y
	public final static Prf mul = R(Z, S(plus, U(3,0), U(3,2)));
	
	// Power/exponentiation: pow(x, y) = x ^ y
	public final static Prf pow = S(R(konst(1), S(mul, U(3,2), U(3,0))), U(2,1), U(2,0));
	
	// Factorial: factorial(x) = x!
	public final static Prf factorial = S(R(konst(1), S(mul, S(N, U(3,1)), U(3,0))), U(1,0), Z);
	
	
	// -- Comparison functions --
	// Every function returns only Boolean values, i.e. 0 or 1
	
	// Is nonzero: nz(x, y) = if x == 0 then 0 else 1
	public final static Prf nz = S(R(Z, S(konst(1), U(3,0))), U(1,0), Z);
	
	// Equal: eq(x, y) = if x == y then 1 else 0
	public final static Prf Eq = S(z, diff);
	
	// Not equal: neq(x, y) = if x != y then 1 else 0
	public final static Prf neq = S(nz, diff);
	
	// Less than: lt(x, y) = if x < y then 1 else 0
	public final static Prf lt = S(nz, subrev);
	
	// Less than or equal: le(x, y) = if x <= y then 1 else 0
	public final static Prf le = S(z, sub);
	
	// Greater than: gt(x, y) = if x > y then 1 else 0
	public final static Prf gt = S(nz, sub);
	
	// Greater than or equal: ge(x, y) = if x >= y then 1 else 0
	public final static Prf ge = S(z, subrev);
	
	
	// -- Late functions --
	
	// Is even: even(x) = if x mod 2 == 0 then 1 else 0
	public final static Prf even = S(R(konst(1), S(not, U(3,0))), U(1,0), Z);
	
	// Is odd: odd(x) = if x mod 2 == 1 then 1 else 0
	public final static Prf odd = S(R(Z, S(not, U(3,0))), U(1,0), Z);
	
	// Square root: sqrt(x) = floor(sqrt(x))
	public final static Prf sqrt = S(R(Z, S(Mux, S(le, S(mul, S(N, U(3,0)), S(N, U(3,0))), U(3,2)), S(N, U(3,0)), U(3,0))), U(1,0), U(1,0));
	
	// Logarithm: log(x, y) = if x >= 2 then (if y >= 1 then floor(ln(y) / ln(x)) else 0) else y
	public final static Prf log = S(R(S(Z, U(2,0)), S(Mux, S(le, S(pow, U(4,2), S(N, U(4,0))), U(4,3)), S(N, U(4,0)), U(4,0))), U(2,1), U(2,0), U(2,1));
	
	// Truncating division: div(x, y) = if y != 0 then floor(x / y) else x
	public final static Prf div = S(R(S(Z, U(2,0)), S(Mux, S(le, S(mul, S(N, U(4,0)), U(4,3)), U(4,2)), S(N, U(4,0)), U(4,0))), U(2,0), U(2,0), U(2,1));
	
	// Modulo: mod(x, y) = if y != 0 then (x mod y) else x
	public final static Prf mod = S(R(U(2,0), S(Mux, S(ge, U(4,0), U(4,3)), S(sub, U(4,0), U(4,3)), U(4,0))), U(2,0), U(2,0), U(2,1));
	
	// Is divisible: divisible(x, y) = if (y > 0 and x mod y == 0) or x == 0 then 1 else 0
	public final static Prf divisible = S(z, mod);
	
	// Is prime: prime(x) = if x is prime then 1 else 0
	public final static Prf isPrime = S(Eq, S(R(Z, S(plus, S(divisible, U(3,2), U(3,1)), U(3,0))), U(1,0), U(1,0)), konst(1));
	
	// Greatest common divisor: gcd(x, y) = if (x != 0 or y != 0) then (largest z such that z divides x and z divides y) else 0
	public final static Prf gcd = S(R(S(Z, U(2,0)), S(Mux, S(and, S(divisible, U(4,2), U(4,1)), S(divisible, U(4,3), U(4,1))), U(4,1), U(4,0))), S(N, max), U(2,0), U(2,1));
	
	// Least common multiple: lcm(x, y) = if (x != 0 and y != 0) then (smallest z such that x divides z and y divides z) else 0
	public final static Prf lcm = S(R(S(Z, U(2,0)), S(Mux, S(and, S(nz, U(4,0)), S(and, S(divisible, U(4,0), U(4,2)), S(divisible, U(4,0), U(4,3)))), U(4,0), U(4,1))), S(N, mul), U(2,0), U(2,1));
	
	// Divisibility count: divisiblecount(x, y) =
	//     if x == 0 or y == 0 then 0
	//     elseif y >= 2 then (the highest power of y that divides x)
	//     else y == 1 then x
	public final static Prf divisiblecount = S(R(S(Z, U(2,0)), S(Mux, S(divisible, U(4,2), S(pow, U(4,3), S(N, U(4,0)))), S(N, U(4,0)), U(4,0))), U(2,0), U(2,0), U(2,1));
	
	// Nth prime: nthprime(0) = 2, nthprime(1) = 3, nthprime(2) = 5, nthprime(3) = 7, nthprime(4) = 11, ...
	public final static Prf nthprime = S(Mux, U(1,0), S(R(Z, S(Mux, S(even, U(3,0)), S(Mux, S(isPrime, U(3,1)), S(Mux, S(Eq, U(3,0), S(plus, U(3,2), U(3,2))), U(3,1), S(N, S(N, U(3,0)))), U(3,0)), U(3,0))), S(pow, konst(2), S(N, U(1,0))), U(1,0)), konst(2));
	
	// Fibonacci number: fibonacci(0) = 0, fibonacci(1) = 1, fibonacci(2) = 1, fibonacci(3) = 2, fibonacci(4) = 3, fibonacci(5) = 5, ...
	// Private: fib2(n) = fibonacci(n) | fibonacci(n+1)<<n
	private static Prf fib2 = R(konst(1), S(S(S(plus, U(3,0), S(mul, S(plus, U(3,0), U(3,1)), U(3,2))), S(div, U(3,0), U(3,2)), S(mod, U(3,0), U(3,2)), S(plus, U(3,2), U(3,2))), U(3,0), U(3,1), S(pow, S(konst(2), U(3,0)), U(3,1))));
	public final static Prf fibonacci = S(mod, S(fib2, U(1,0), Z), S(pow, konst(2), U(1,0)));
	
	
	// -- Bitwise functions --
	
	// Left shift: shl(x, y) = x << y
	public final static Prf shl = S(mul, U(2,0), S(pow, S(konst(2), U(2,0)), U(2,1)));
	
	// Right shift: shr(x, y) = x >> y
	public final static Prf shr = S(div, U(2,0), S(pow, S(konst(2), U(2,0)), U(2,1)));
	
	// Private: log2p1(x) = if x != 0 then (floor(lg(x)) + 1) else 1
	private static Prf log2p1 = S(N, S(log, konst(2), U(1,0)));
	// Private: bitCombine f (x, y, s) = f(floor(x/s), floor(y/s)) * s. (This combines x and y at bit position log2(s) with the Boolean function f. The scaler s must be a power of 2.)
	private static Prf bitCombine(Prf f) {
		return S(mul, S(f, S(odd, S(div, U(3,0), U(3,2))), S(odd, S(div, U(3,1), U(3,2)))), U(3,2));
	}
	// Private: Takes a binary Boolean PRF (i.e. {0,1}*{0,1} -> {0,1}) and produces an integer PRF that applies it to each pair of corresponding bits in x and y
	private static Prf makeBitwiseOp(Prf f) {
		return S(R(S(Z, U(2,0)), S(plus, U(4,0), S(bitCombine(f), U(4,2), U(4,3), S(pow, S(konst(2), U(4,0)), U(4,1))))), S(log2p1, S(max, U(2,0), U(2,1))), U(2,0), U(2,1));
	}
	
	// Bitwise AND: band(x, y) = x & y
	public final static Prf band = makeBitwiseOp(and);
	
	// Bitwise AND-NOT: bandnot(x, y) = x & ~y
	public final static Prf bandnot = makeBitwiseOp(S(R(U(1,0), S(Z, U(3,0))), U(2,1), U(2,0)));
	
	// Bitwise OR: bor(x, y) = x | y
	public final static Prf bor = makeBitwiseOp(or);
	
	// Bitwise XOR: bxor(x, y) = x ^ y
	public final static Prf bxor = makeBitwiseOp(xor);
	
	// Get bit: getbit(x, y) = (x >> y) & 1
	public final static Prf getbit = S(odd, shr);
	
}