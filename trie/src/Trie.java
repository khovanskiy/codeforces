import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Trie {
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

	final static int K = 26;
	
	static class Node
	{
		public Node[] next = new Node[K];
		public char c;
		public int parent = 1;
		public int id = 1;
	}
	
	public static void main(String[] args) {
		try {
			in = new BufferedReader(new FileReader("trie.in"));
			out = new BufferedWriter(new FileWriter("trie.out"));

			String s = nextToken();
			
			Node root = new Node();
			
			int id = 1;
			for (int i = 0; i < s.length(); ++i)
			{
				Node current = root;
				for (int j = i; j < s.length(); ++j)
				{
					int c = s.charAt(j) - 'a';
					if (current.next[c] == null)
					{
						current.next[c] = new Node();
						current.next[c].parent = current.id;
						current.next[c].c = s.charAt(j);
						current.next[c].id = ++id;
					}
					current = current.next[c];
				}
			}
			out.write(id + " " + (id - 1) + "\n");
			
			Queue<Node> q = new LinkedList<>();
			q.add(root);
			while (!q.isEmpty())
			{
				Node current = q.poll();
				if (current.id != 1)
				{
					out.write(current.parent + " " + current.id + " " + current.c + "\n");
				}
				for (int i = 0; i < current.next.length; ++i)
				{
					if (current.next[i] != null)
					{
						q.add(current.next[i]);
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