import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.Vector;
 
public class Search4 {
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
 
    static <T> void trace(T s) {
        System.out.println(s);
    }
 
    static void trace(String s) {
        System.out.println(s);
    }
 
    static StringTokenizer st;
    static BufferedReader in;
    static BufferedWriter out;
 
    private static final int SIZE = 30;
 
    static class Node {
 
        Node[] children = new Node[SIZE];
        List<Integer> leaf = null;
        Node parent;
        Node up;
        char charToParent;
        Node suffLink;
        Node[] go = new Node[SIZE];
    }
 
    static class Matcher 
    {
        private Node root = null;
        private Node next = null;
 
        private boolean[] found;
        
        public Matcher(int n) 
        {
            root = buildRoot();
            next = root;
            found = new boolean[n];
        }
        
        public boolean[] getFound()
        {
        	return found;
        }
 
        public static Node buildRoot() 
        {
            Node node = new Node();
            node.suffLink = node;
            node.up = node;
            return node;
        }
 
        private void addPattern(String pattern, int patternNumber) 
        {
            Node current = root;
            for (int i = 0; i < pattern.length(); ++i) 
            {
                char ch = pattern.charAt(i);
                int l = pattern.charAt(i) - 'a';
 
                if (current.children[l] == null) 
                {
                    Node n = new Node();
                    n.parent = current;
                    n.charToParent = ch;
                    current.children[l] = n;
                }
                current = current.children[l];
            }
            if (current.leaf == null)
            {
            	current.leaf = new ArrayList<Integer>();
            }
            current.leaf.add(patternNumber);
        }
 
        public void next(char ch) 
        {
            next = go(next, ch);
            Node v = next;
            while (v != root)
            {
            	if (v.leaf != null)
        		{
            		for (int i = 0; i < v.leaf.size(); ++i)
            		{
            			found[v.leaf.get(i)] = true;
            		}
        		}
            	Node temp = v;
            	v = getUp(v);
            	temp.leaf = null;
            	temp.up = root;
            }
        }
 
        public Node go(Node node, char ch) {
            int c = ch - 'a';
            if (node.go[c] == null) {
              if (node.children[c] != null) {
                node.go[c] = node.children[c];
              } else {
                node.go[c] = node.parent == null ? node : go(suffLink(node), ch);
              }
            }
            return node.go[c];
          }
 
        public Node suffLink(Node node) 
        {
	            if (node.suffLink == null) 
	            {
		              if (node.parent.parent == null) 
		              {
		            	  node.suffLink = node.parent;
		              } 
		              else 
		              {
		            	  node.suffLink = go(suffLink(node.parent), node.charToParent);
		              }
	            }
	            return node.suffLink;
        }
        
        private Node getUp(Node v) 
        {
            if (v.up == null) 
            {
                if (suffLink(v).leaf != null) 
                {
                    v.up = suffLink(v);
                } 
                else if (suffLink(v) == root)
                {
                    v.up = root;
                } 
                else 
                {
                    v.up = getUp(suffLink(v));
                }
            }
            return v.up;
        }
    }
 
    public static void main(String[] args) {
        try {
            in = new BufferedReader(new FileReader("search4.in"));
            out = new BufferedWriter(new FileWriter("search4.out"));
 
            int n = nextInt();
            Matcher m = new Matcher(n);
             
            for (int i = 0; i < n; ++i)
            {
            	m.addPattern(nextToken(), i);
            }
            String t = nextToken();
            
            for (int i = 0; i < t.length(); ++i)
            {
            	m.next(t.charAt(i));
            }
            
            boolean[] found = m.getFound();
            for (int i = 0; i < n; ++i)
            {
            	if (found[i])
            	{
            		out.write("YES\n");
            	}
            	else
            	{
            		out.write("NO\n");
            	}
            }
 
            in.close();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}