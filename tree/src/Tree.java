import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Tree {
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

	static void trace(int s) {
		System.out.println(s);
	}

	static void trace(String s) {
		System.out.println(s);
	}

	static StringTokenizer st;
	static BufferedReader in;
	static BufferedWriter out;

	static String BUFFEROFA = "abcdefghijklmnopqrstuvwxyz\1\2";
	static int BUFFERASIZE = BUFFEROFA.length();

	static class Node {
		public static int ids = 1;
		int oceanheighttobottom;
		int startPoint;
		int finishPoint;
		Node[] inners;
		Node thenodewhobornme;
		Node sl;
		public int id;

		Node(int begin, int end, int depth, Node parent) {
			inners = new Node[BUFFERASIZE];
			this.startPoint = begin;
			this.finishPoint = end;
			this.thenodewhobornme = parent;
			this.oceanheighttobottom = depth;
			this.id = ids++;
		}

		boolean isIn(int d) {
			return oceanheighttobottom <= d && d < oceanheighttobottom + (finishPoint - startPoint);
		}
	}

	public static Node generate(String s) {
		int n = s.length();
		byte[] a = new byte[n];
		for (int i = 0; i < n; i++) {
			a[i] = (byte) BUFFEROFA.indexOf(s.charAt(i));
		}
		Node root = new Node(0, 0, 0, null);
		Node cn = root;
		root.sl = root;
		Node rsl = null;
		int odlr = 0;
		int j = 0;
		for (int i = -1; i < n - 1; i++) {
			int cur = a[i + 1];
			for (; j <= i + 1; j++) {
				int currentDepthOfThisBigTree = i + 1 - j;
				if (odlr != 3) {
					cn = cn.sl != null ? cn.sl
							: cn.thenodewhobornme.sl;
					int k = j + cn.oceanheighttobottom;
					while (currentDepthOfThisBigTree > 0 && !cn.isIn(currentDepthOfThisBigTree - 1)) {
						k += cn.finishPoint - cn.startPoint;
						cn = cn.inners[a[k]];
					}
				}
				if (!cn.isIn(currentDepthOfThisBigTree)) {
					if (rsl != null) {
						rsl.sl = cn;
						rsl = null;
					}
					if (cn.inners[cur] == null) {
						cn.inners[cur] = new Node(i + 1, n, currentDepthOfThisBigTree, cn);
						odlr = 2;
					} else {
						cn = cn.inners[cur];
						odlr = 3;
						break;
					}
				} else {
					int end = cn.startPoint + currentDepthOfThisBigTree - cn.oceanheighttobottom;
					if (a[end] != cur) {
						Node cache = new Node(cn.startPoint, end, cn.oceanheighttobottom, cn.thenodewhobornme);
						cache.inners[cur] = new Node(i + 1, n, currentDepthOfThisBigTree, cache);
						cache.inners[a[end]] = cn;
						cn.thenodewhobornme.inners[a[cn.startPoint]] = cache;
						if (rsl != null) {
							rsl.sl = cache;
						}
						cn.startPoint = end;
						cn.oceanheighttobottom = currentDepthOfThisBigTree;
						cn.thenodewhobornme = cache;
						cn = rsl = cache;
						odlr = 2;
					} else if (cn.finishPoint != n || cn.startPoint - cn.oceanheighttobottom < j) {
						odlr = 3;
						break;
					} else {
						odlr = 1;
					}
				}
			}
		}
		root.sl = null;
		return root;
	}

	public static void main(String[] args) {
		try {
			in = new BufferedReader(new FileReader("tree.in"));
			out = new BufferedWriter(new FileWriter("tree.out"));
			
			String s = nextToken();
			Node root = generate(s);
		    
			Queue<Node> q = new LinkedList<Node>();
			q.add(root);
			
			out.write((Node.ids - 1) + " " + (Node.ids - 2) + "\n");
			while (!q.isEmpty())
			{
				Node current = q.poll();
				if (current.id != 1)
				{
					out.write(current.thenodewhobornme.id + " " + current.id + " " + (current.startPoint + 1) + " " + current.finishPoint + "\n");
				}
				for (int i = 0; i < current.inners.length; ++i)
				{
					if (current.inners[i] != null)
					{
						q.add(current.inners[i]);
					}
				}
			}
		    
			in.close();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}