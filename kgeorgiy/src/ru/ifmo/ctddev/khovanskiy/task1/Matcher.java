package ru.ifmo.ctddev.khovanskiy.task1;

import java.nio.charset.Charset;

public class Matcher {
	static class Node {

		Node[] children = new Node[ALPHABET_SIZE];
		int leaf = 0;
		Node parent;
		int charToParent;
		Node suffLink;
		Node[] go = new Node[ALPHABET_SIZE];
	}

	private static final int ALPHABET_SIZE = 256;

	private static final String[] encodings = { "UTF-8", "windows-1251",
			"KOI8-R", "IBM866" };// , "UTF-16", "UTF-16BE", "UTF-16LE",
									// "x-IBM1383", "x-IBM935"};

	private Node root = null;
	private Node current = null;

	public Matcher() {
		root = createRoot();
	}

	public static Node createRoot() {
		Node node = new Node();
		node.suffLink = node;
		return node;
	}

	public void addString(String s) {
		for (int i = 0; i < encodings.length; ++i) {
			addPattern(s.toLowerCase().getBytes(Charset.forName(encodings[i])),
					s.toUpperCase().getBytes(Charset.forName(encodings[i])));
		}
	}

	private void addPattern(byte[] lower, byte[] upper) {
		Node current = root;
		for (int i = 0; i < lower.length; ++i) {
			int l = lower[i] & 0xFF;
			int u = upper[i] & 0xFF;

			if (current.children[l] == null) {
				Node n = new Node();
				n.parent = current;
				n.charToParent = l;
				current.children[l] = n;
				current.children[u] = n;
			}
			current = current.children[l];
		}
		current.leaf = Math.max(current.leaf,
				Math.max(lower.length, upper.length));
	}

	public int next(byte ch) {
		if (current == null) {
			current = root;
		}
		current = go(current, ch & 0xFF);

		return current.leaf;
	}

	private Node go(Node node, int c) {
		if (node.go[c] == null) {
			if (node.children[c] != null) {
				node.go[c] = node.children[c];
			} else {
				node.go[c] = node.parent == null ? node : go(suffLink(node), c);
			}
		}
		return node.go[c];
	}

	private Node suffLink(Node node) {
		if (node.suffLink == null) {
			if (node.parent.parent == null) {
				node.suffLink = node.parent;
			} else {
				node.suffLink = go(suffLink(node.parent), node.charToParent);
			}
		}
		return node.suffLink;
	}
}
