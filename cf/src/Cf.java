import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.StringTokenizer;

public class Cf {
	static String nextToken() {
		while (st == null || !st.hasMoreTokens()) {
			try {
				st = new StringTokenizer(in.readLine());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return st.nextToken();
	}

	static int nextInt() {
		return Integer.parseInt(nextToken());
	}

	static StringTokenizer st;
	static BufferedReader in;
	static BufferedWriter out;

	public static <T> void trace(T s) {
		System.out.println(s);
	}
	
	public static class Pair<F, S> {
		F first;
		S second;
		
		public Pair(F first, S second) {
			this.first = first;
			this.second = second;
		}
		
		public F getFirst() {
			return this.first;
		}
		
		public S getSecond() {
			return this.second;
		}
		
		@Override
		public String toString() {
			return "(" + first + ", " + second + ")";
		}
	}

	public static class Grammar {

		private static class Rule {
			private final int MAX_LENGTH = 5;
			
			private int nonterminal;
			private int[] symbols = new int[MAX_LENGTH];
			private boolean[] type = new boolean[MAX_LENGTH];
			private int size = 0;

			public Rule(int nonterminal) {
				this.nonterminal = nonterminal;
			}
			
			public int length() {
				return this.size;
			}
			
			public void set(int index, int symbol, boolean isTerminal) {
				this.symbols[index] = symbol;
				this.type[index] = isTerminal;
			}
			
			public int get(int index) {
				return this.symbols[index];
			}
			
			public boolean isTerminal(int index) {
				return this.type[index];
			}
			
			public void add(int symbol, boolean isTerminal) {
				this.symbols[this.size] = symbol;
				this.type[this.size] = isTerminal;
				++this.size;
			}
			
			public boolean equals(int index, char c) {
				if (c >= 'a' && c <= 'z') {
					int cc = c - 'a';
					return isTerminal(index) && cc == get(index);
				}
				int cc = c - 'A';
				return !isTerminal(index) && cc == get(index);
			}
			
			public boolean isLongRule() {
				return this.size > 2;
			}
			
			public boolean isUnitRule() {
				if (this.size != 1) {
					return false;
				}
				return !isTerminal(0);
			}

			@Override
			public String toString() {
				//return (char) (this.nonterminal + 'A') + " -> " + this.right;
				String temp = "";
				temp += "" + (char)(this.nonterminal + 'A') + " -> ";
				for (int i = 0; i < this.size; ++i) {
					if (this.type[i]) {
						temp += (char)(this.symbols[i] + 'a') + "";
					} else {
						temp += ""+ (char)(this.symbols[i] + 'A')+ "";
					}
				}
				return temp;
			}
		}

		private int s;
		private HashSet<Integer> nonterminals = new HashSet<Integer>();
		private ArrayList<Rule> rules = new ArrayList<Rule>();
		private int max_id = 0;

		public Grammar(char s) {
			this.s = s - 'A';
		}
		
		/**
		 * Removes all long rules from grammar
		 */
		private void removeLongRules() {
			ArrayList<Rule> newRules = new ArrayList<Rule>();
			for (Rule current : rules) {
				if (current.isLongRule()) {
					int k = current.length();
					Rule rule;
					
					int prev;
					int next = current.nonterminal;
					
					for (int i = 0; i < k - 2; ++i) {
						prev = next;
						next = nextNonterminal();
						rule = new Rule(prev);
						rule.add(current.get(i), current.isTerminal(i));
						rule.add(next, false);
						newRules.add(rule);
					}
					prev = next;
					
					rule = new Rule(prev);
					rule.add(current.get(k - 2), current.isTerminal(k - 2));
					rule.add(current.get(k - 1), current.isTerminal(k - 1));
					newRules.add(rule);
				} else {
					newRules.add(current);
				}
			}
			rules = newRules;
		}
		
		private void removeEpsilonRules() {
			boolean[] epsilon = new boolean[getAlphabetSize()];
			boolean epsProduction = false; // S -> eps ?
			
			for (Rule rule : rules) {
				epsilon[rule.nonterminal] |= rule.length() == 0;
			}
			epsProduction = epsilon[this.s];
			
			boolean updated = true;
			while (updated) {
				updated = false;
				for (Rule rule : rules) {
					if (!epsilon[rule.nonterminal]) {
						if (isEpsilon(rule, epsilon)) {
							epsilon[rule.nonterminal] = true;
							updated = true;
						}
					}
				}
			}
			
			ArrayList<Rule> newRules = new ArrayList<Rule>();
			for (Rule rule : rules) {
				Rule temp = new Rule(rule.nonterminal);
				newRules.add(temp);
				splitRules(newRules, epsilon, rule, temp, 0);
			}
			HashMap<String, Rule> rulesMap = new HashMap<String, Rule>();
			rules.clear();
			for (Rule rule : newRules) {
				if (rule.length() > 0) {
					if (rule.length() == 1 && rule.nonterminal == rule.get(0) && !rule.isTerminal(0)) {
						continue;
					}
					String key = rule.toString();
					if (!rulesMap.containsKey(key)) {
						rulesMap.put(key, rule);
						rules.add(rule);
					}
				}
			}
			if (epsProduction) {
				int new_start = nextNonterminal();
				Rule r1 = new Rule(new_start); // S'->eps
				
				Rule r2 = new Rule(new_start); // S'->S
				r2.add(this.s, false);
				rules.add(r1);
				rules.add(r2);
				
				this.s = new_start;
			}
		}
		
		private void splitRules(ArrayList<Rule> newRules, boolean[] epsilon, Rule source, Rule current, int p) {
			if (source.length() == p) {
				return;
			}
			if (source.isTerminal(p)) {
				current.add(source.get(p), true);
			} else if (!epsilon[source.get(p)]){
				current.add(source.get(p), false);
			} else {
				Rule r1 = new Rule(source.nonterminal);
				for (int i = 0; i < current.length(); ++i) {
					r1.add(current.get(i), current.isTerminal(i));
				}
				current.add(source.get(p), false);
				newRules.add(r1);
				splitRules(newRules, epsilon, source, r1, p + 1);
			}
			splitRules(newRules, epsilon, source, current, p + 1);
		}
		
		private boolean isEpsilon(Rule rule, boolean[] epsilon) {
			for (int i = 0; i < rule.length(); ++i) {
				if (rule.isTerminal(i)) {
					return false;
				}
				if (!epsilon[rule.get(i)]) {
					return false;
				}
			}
			return true;
		}
		
		private boolean isGenerating(Rule r, boolean[] generating) {
			boolean terminals = true;
			boolean noterminals = true;
			for (int i = 0; i < r.length(); ++i) {
				if (r.isTerminal(i)) {
					continue;
				} else {
					terminals = false;
				}
				noterminals &= generating[r.get(i)];
			}
			return terminals || noterminals;
		}
		
		private void removeUnitPairRules() {
			ArrayList<Pair<Integer, Integer>> pairs = new ArrayList<Pair<Integer, Integer>>();
			for (Integer nonterminal : nonterminals) {
				pairs.add(new Pair<Integer, Integer>(nonterminal, nonterminal));
			}
			for (int i = 0; i < pairs.size(); ++i) {
				Pair<Integer, Integer> pair = pairs.get(i);
				for (Rule rule : rules) {
					if (!rule.isUnitRule()) {
						continue;
					}
					if (rule.nonterminal == pair.getSecond()) {
						pairs.add(new Pair<Integer, Integer>(pair.getFirst(), rule.get(0)));
					}
				}
			}
			ArrayList<Rule> newRules = new ArrayList<Rule>();
			HashMap<String, Rule> rulesMap = new HashMap<String, Rule>();
			for (Pair<Integer, Integer> pair : pairs) {
				for (Rule rule : rules) {
					if (pair.getSecond() == rule.nonterminal) {
						Rule temp = new Rule(pair.getFirst());
						for (int i = 0; i < rule.length(); ++i) {
							temp.add(rule.get(i), rule.isTerminal(i));
						}
						if (!temp.isUnitRule() && !rulesMap.containsKey(temp.toString())) {
							rulesMap.put(temp.toString(), temp);
							newRules.add(temp);
						}
					}
				}
			}
			rules = newRules;
		}
		
		private void removeNotgeneratingNonterminals() {
			boolean[] generating = new boolean[getAlphabetSize()];
			
			boolean updated = true;
			while (updated) {
				updated = false;
				for (Rule rule : rules) {
					if (!generating[rule.nonterminal]) {
						if (isGenerating(rule, generating)) {
							generating[rule.nonterminal] = true;
							updated = true;
						}
					}
				}
			}
			
			ArrayList<Rule> newRules = new ArrayList<Rule>();
			for (Rule rule : rules) {
				if (!generating[rule.nonterminal]) {
					continue;
				}
				if (isGenerating(rule, generating)) {
					newRules.add(rule);
				}
			}
			rules = newRules;
		}
		
		private void removeUnreachedbleNonterminals() {
			boolean[] reachable = new boolean[getAlphabetSize()];
			
			LinkedList<Integer> queue = new LinkedList<Integer>();
			reachable[s] = true;
			queue.add(s);
			
			while (!queue.isEmpty()) {
				int u = queue.poll();
				
				for (Rule rule : rules) {
					if (rule.nonterminal != u) {
						continue;
					}
					for (int j = 0; j < rule.length(); ++j) {
						if (rule.isTerminal(j)) {
							continue;
						}
						int v = rule.get(j);
						if (!reachable[v]) {
							reachable[v] = true;
							queue.add(v);
						}
					}
				}
			}
			
			ArrayList<Rule> newRules = new ArrayList<Rule>();
			for (Rule rule : rules) {
				if (reachable[rule.nonterminal]) {
					newRules.add(rule);
				}
			}
			rules = newRules;
		}
		
		private void removeUselessSymbols() {
			removeNotgeneratingNonterminals();
			removeUnreachedbleNonterminals();
		}
		
		private void changeTerminalsByNonTerminals() {
			ArrayList<Rule> newRules = new ArrayList<Rule>();
			for (Rule rule : rules) {
				if (rule.length() == 2) {
					for (int i = 0; i < rule.length(); ++i) {
						if (rule.isTerminal(i)) {
							int next = nextNonterminal();
							
							Rule r1 = new Rule(next);
							r1.add(rule.get(i), true);
							newRules.add(r1);
							
							rule.set(i, next, false);
						}
					}
				}
				newRules.add(rule);
			}
			rules = newRules;
		}
		
		public void toNcf() {
			trace(nonterminals + " S=" + this.s + " " + rules);
			removeLongRules();
			//trace(nonterminals + " S=" + this.s + " " + rules);
			removeEpsilonRules();
			//trace(nonterminals + " S=" + this.s + " " + rules);
			removeUnitPairRules();
			//trace(nonterminals + " S=" + this.s + " " + rules);
			removeUselessSymbols();
			//trace(nonterminals + " S=" + this.s + " " + rules);
			changeTerminalsByNonTerminals();
			trace(nonterminals + " S=" + this.s + " " + rules);
		}

		
		
		public boolean check(String w) {
			int n = w.length();

			boolean[][][] d = new boolean[getAlphabetSize()][n + 1][n + 1];
			for (int i = 1; i <= n; ++i) {
				for (Rule rule : rules) {
					if (rule.length() == 1 && rule.equals(0, w.charAt(i - 1))) {
						d[rule.nonterminal][i][i] = true;
					}
				}
			}
			
			for (int len = 1; len <= n; ++len) {
				trace("M = " + len);
				for (int i = 1; i <= n - len; ++i) {
					for (int k = i; k <= i + len - 1; ++k) {
						for (Rule rule : rules) {
							int t = rule.nonterminal;
							if (rule.length() == 2) {
								int l = rule.get(0);
								int r = rule.get(1);
								d[t][i][i + len] = d[t][i][i + len] || d[l][i][k] && d[r][k + 1][i + len];
							}
						}
					}
				}
			}

			return d[this.s][1][n];
		}
		
		/**
		 * Adds nonterminal to grammar
		 * 
		 * @param id
		 */
		private void putNonterminal(int id) {
			nonterminals.add(id);
			if (id > max_id) {
				max_id = id;
			}
		}
		
		private int nextNonterminal() {
			int id = max_id + 1;
			putNonterminal(id);
			return id;
		}
		
		public int getAlphabetSize() {
			return max_id + 1;
		}

		public void addRule(char nonterminal, String right) {
			putNonterminal(nonterminal - 'A');
			Rule rule = new Rule(nonterminal - 'A');
			for (int i = 0; i < right.length(); ++i) {
				char c = right.charAt(i);
				if (c >= 'a' && c <= 'z') {
					rule.add(c - 'a', true);
				} else {
					rule.add(c - 'A', false);
				}
			}
			rules.add(rule);
		}
	}

	public static void main(String[] args) {
		try {
			in = new BufferedReader(new FileReader("cf.in"));
			out = new BufferedWriter(new FileWriter("cf.out"));

			String[] buffer;
			buffer = in.readLine().split(" ");

			int n = Integer.parseInt(buffer[0]);

			Grammar grammar = new Grammar(buffer[1].charAt(0));

			for (int i = 0; i < n; ++i) {
				String[] lexems = in.readLine().split(" ");
				if (lexems.length > 2) {
					grammar.addRule(lexems[0].charAt(0), lexems[2]);
				} else {
					grammar.addRule(lexems[0].charAt(0), "");
				}
			}

			grammar.toNcf();
			
			String w = in.readLine();
			if (grammar.check(w)) {
				out.write("yes\n");
			} else {
				out.write("no\n");
			}

			in.close();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}