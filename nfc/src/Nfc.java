import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Nfc {
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

	public static final int ALPHABET = 26;
	
	static StringTokenizer st;
	static BufferedReader in;
	static BufferedWriter out;
	
	public static <T> void trace(T s) {
		System.out.println(s);
	}
	
	public static class Grammar {
		
		private static class Rule {
			
			private int nonterminal;
			private String right;
			
			public Rule(int nonterminal, String right) {
				this.nonterminal = nonterminal;
				this.right = right;
			}
			
			@Override
			public String toString() {
				return (char)(this.nonterminal + 'A') + " -> " + this.right;
			}
		}
		
		private int s;
		private ArrayList<Rule> rules = new ArrayList<Rule>();
		
		public Grammar(char s) {
			this.s = s - 'A';
		}
		
		private void dumpTable(int c, long[][] table, boolean last) {
			System.out.print("{| border=\"1\" class=\"wikitable\" style=\"width: 150px; height: 150px; "+(true?"float: left;":"")+"\" \n");
			System.out.print("! colspan=\"" + table[0].length+ "\" style=\"background:#ffdead;\"|"+(char)(c + 'A')+"\n");
			System.out.print("|-\n");
			System.out.print("! \n");
			for (int i = 1; i < table.length; ++i) {
				System.out.print("! " + (i) + "\n");
			}
			for (int i = 1; i < table.length; ++i) {
				System.out.print("|-\n! "+ (i) + "\n");
				for (int j = 1; j < table[i].length; ++j) {
					if (table[i][j] != 0) {
						System.out.print("| align=\"center\"| ● \n");
					} else {
						System.out.print("| \n");
					}
					if (table[i].length != j + 1) {
						//System.out.print("||");
					}
				}
				//System.out.print("\n");
			}
			System.out.print("|}\n");
		}
		
		private void dumpTable(long[][][] table) {
			/*System.out.print("__\t");
			for (int i = 0; i < table.length; ++i) {
				System.out.print((char)(i + 'A' - 1) + "_\t");
			}
			System.out.print("\n");
			for (int i = 0; i < table.length; ++i) {
				System.out.print((char)(i + 'A' - 1) + "|\t");
				for (int j = 0; j < table[i].length; ++j) {
					System.out.print((table[i][j]?1:0)+"\t");
				}
				System.out.print("\n");
			}*/
			/*{| border="1" class="wikitable"
					|+ The table's caption
					! Column heading 1
					! Column heading 2
					! Column heading 3
					|-
					! Row heading 1
					| Cell 2 || Cell 3
					|-
					! Row heading A
					| Cell B
					| Cell C
					|}*/
			//System.out.print("{| border=\"1\" class=\"wikitable\"\n");
			/*int alp =  table.length;
			int rows = table[0].length;
			int cols = rows;
			System.out.print("! \n");
			for (int j = 0; j < alp; ++j) {
				System.out.print("! colspan=\""+ (cols - 1) +"\" | " + (char)(j + 'A') + "\n");
			}
			System.out.print("|-\n");
			System.out.print("! \n");
			for (int j = 0; j < alp; ++j) {
				for (int k = 1; k < cols; ++k) {
					System.out.print("! " + (k) + "\n");
				}
			}
			for (int i = 1; i < rows; ++i) {
				System.out.print("|-\n! "+ (i) + "\n");
				for (int j = 0; j < alp; ++j) {
					for (int k = 1; k < cols; ++k) {
						System.out.print("| "+(table[j][i][k] != 0 ? "true" : "")+" || ");
					}
					System.out.print("\n");
				}
			}*/
			for (int i = 0; i < table.length; ++i) {
				dumpTable(i, table[i], i == table.length - 1);
			}
			System.out.print("<div style=\"clear:both;\"></div>\n");
			/*for (int i = 0; i < table.length; ++i) {
				System.out.print("|- \n! rowspan=\"" + (table[0].length - 1) + "\"|"+ (char)(i + 'A') + "\n");
				for (int j = 1; j < table[i].length; ++j) {
					System.out.print("\n! rowspan=\"" + (table[0][0].length - 1) + "\"|"+ (j) + "\n| ");
					for (int k = 1; k < table[i][j].length; ++k) {
						System.out.print(""+(table[i][j][k] != 0 ? "true" : "")+"||");
					}
				}
			}*/
			//System.out.print("|}\n");
		}
		
		public long count(String w, long mod) {
			int n = w.length();
			
			long[][][] d = new long[5][n + 1][n + 1];
			for (int i = 1; i <= n; ++i) {
				for (Rule rule : rules) {
					//trace(rule);
					if (rule.right.length() == 1 && rule.right.charAt(0) == w.charAt(i - 1)) {
						d[rule.nonterminal][i][i] = 1;
					}
				}
			}
			trace("\nИнициализация массива <tex>d</tex>.\n");
			
			dumpTable(d);
			
			trace("\nЗаполнение массива <tex>d</tex>.\n");
			
			for (int len = 1; len <= n - 1; ++len) {
				trace("\nИтерация <tex>m = " + len+"</tex>.\n");
		         for (int i = 1; i <= n - len; ++i) {
		            for (int k = i; k <= i + len - 1; ++k) {
		            	for (Rule rule : rules) {
		            		int t = rule.nonterminal;
		            		if (rule.right.length() == 2) {
		            			int l = rule.right.charAt(0) - 'A';
		            			int r = rule.right.charAt(1) - 'A';
		            			d[t][i][i + len] = (d[t][i][i + len] + d[l][i][k] * d[r][k + 1][i + len]) % mod;
		            		}
		            	}
		            }
		         }
		         dumpTable(d);
		      }
			
			//trace(Arrays.deepToString(d));
			//trace(d[this.s][1][n]);
			
			return d[this.s][1][n];
		}
		
		public void addRule(char nonterminal, String rule) {
			rules.add(new Rule(nonterminal - 'A', rule));
		}
		
		private boolean isTerminal(char c) {
			return c >= 'a' && c <= 'z';
		}
		
		private boolean isNonterminal(char c) {
			return !isTerminal(c);
		}
	}

	public static void main(String[] args) {
		try {
			in = new BufferedReader(new FileReader("nfc.in"));
			out = new BufferedWriter(new FileWriter("nfc.out"));

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
			
			String w = in.readLine();
			
			out.write(grammar.count(w, 1000000007) + "\n");
			
			in.close();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}