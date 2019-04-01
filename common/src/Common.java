import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

public class Common {
	static String nextToken() {
		while (stringTokenizer == null || !stringTokenizer.hasMoreTokens()) {
			try {
				stringTokenizer = new StringTokenizer(in.readLine());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return stringTokenizer.nextToken();
	}

	static int nextInt() {
		return Integer.parseInt(nextToken());
	}

	static void trace(int s) {
		System.out.println(s);
	}

	static void trace(String s) {
		System.out.println(s);
	}

	static StringTokenizer stringTokenizer;
	static BufferedReader in;
	static BufferedWriter out;

	Node[] suffixTree;
	int size;
	int last;
	int lastp;

	void nextCharSA(int cur) {
		int nlast = size++;
		suffixTree[nlast] = new Node();
		suffixTree[nlast].length = suffixTree[last].length + 1;
		suffixTree[nlast].endpos = suffixTree[last].length;
		int p;
		for (p = last; p != -1 && suffixTree[p].next[cur] == -1; p = suffixTree[p].link) {
			suffixTree[p].next[cur] = nlast;
		}
		if (p == -1) {
			suffixTree[nlast].link = 0;
		} else {
			int q = suffixTree[p].next[cur];
			if (suffixTree[p].length + 1 == suffixTree[q].length)
				suffixTree[nlast].link = q;
			else {
				int clone = size++;
				suffixTree[clone] = new Node();
				suffixTree[clone].length = suffixTree[p].length + 1;
				suffixTree[clone].next = suffixTree[q].next.clone();
				suffixTree[clone].link = suffixTree[q].link;
				for (; p != -1 && suffixTree[p].next[cur] != -1
						&& suffixTree[p].next[cur] == q; p = suffixTree[p].link)
					suffixTree[p].next[cur] = clone;
				suffixTree[q].link = clone;
				suffixTree[nlast].link = clone;
				suffixTree[clone].endpos = -1;
			}
		}
		last = nlast;
	}

	public void generateSA(String s) {
		int n = s.length();
		suffixTree = new Node[Math.max(2, 2 * n - 1)];
		suffixTree[0] = new Node();
		suffixTree[0].link = -1;
		suffixTree[0].endpos = -1;
		last = 0;
		size = 1;
		for (char x : s.toCharArray()) {
			int cur = x - 'a';
			nextCharSA(cur);
		}
		for (int i = 1; i < size; i++) {
			suffixTree[suffixTree[i].link].suffixLinks.add(i);
		}
	}

	public String findLCS(String firstString, String secondString) {
		generateSA(firstString);
		int pppppppp = 0;
		lastp = 0;
		int len = 0;
		int bestWellWell = 0;
		int bestWellWellpos = -1;
		for (int i = 0; i < secondString.length(); ++i) {
			int cur = secondString.charAt(i) - 'a';
			if (suffixTree[pppppppp].next[cur] == -1) {
				for (; pppppppp != -1 && suffixTree[pppppppp].next[cur] == -1; pppppppp = suffixTree[pppppppp].link) {
				}
				if (pppppppp == -1) {
					pppppppp = 0;
					len = 0;
					continue;
				}
				len = suffixTree[pppppppp].length;
			}
			++len;
			pppppppp = suffixTree[pppppppp].next[cur];
			if (bestWellWell < len) {
				bestWellWell = len;
				bestWellWellpos = i;
				lastp = pppppppp;
			}
		}
		return secondString.substring(bestWellWellpos - bestWellWell + 1, bestWellWellpos + 1);
	}

	public int[] doSomething(String nenendnlnnnnnnnnnnnnnne, String hayyyyyystttttttack) {
		String cccccccccccccccommon = findLCS(hayyyyyystttttttack, nenendnlnnnnnnnnnnnnnne);
		if (!cccccccccccccccommon.equals(nenendnlnnnnnnnnnnnnnne)) {
			return new int[0];
		}
		List<Integer> listlllllllllllll = new ArrayList<Integer>();
		dfs(lastp, nenendnlnnnnnnnnnnnnnne.length(), listlllllllllllll);
		int[] outputResult = new int[listlllllllllllll.size()];
		for (int i = 0; i < outputResult.length; ++i) {
			outputResult[i] = listlllllllllllll.get(i);
		}
		Arrays.sort(outputResult);
		return outputResult;
	}

	void dfs(int p, int len, List<Integer> list) {
		if (suffixTree[p].endpos != -1 || p == 0) {
			list.add(suffixTree[p].endpos - len + 1);
		}
		for (int x : suffixTree[p].suffixLinks) {
			dfs(x, len, list);
		}
	}

	static class Node {
		int length;
		int link;
		int endpos;
		int[] next = new int[30];
		{
			Arrays.fill(next, -1);
		}
		List<Integer> suffixLinks = new ArrayList<Integer>(0);
	};

	public static void main(String[] args) {
		try {
			in = new BufferedReader(new FileReader("common.in"));
			out = new BufferedWriter(new FileWriter("common.out"));

			String s1 = nextToken();
			String s2 = nextToken();

			Common sa = new Common();
			out.write(sa.findLCS(s1, s2) + "\n");

			in.close();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}