import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.AbstractList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.StringTokenizer;

public class FastMinimization {

	private static class HopcroftAlgorithm {
		private STDPseudoIntList[] going;
		private STDPseudoIntList goingUnion;
		private STDPseudoIntList.STDPseudoPointer[] isEnterF;
		private STDPseudoIntList.STDPseudoPointer[] isEnterS;
		private boolean[] isGoing;
		private boolean[] isEnterGoing;
		private int kkk = 2;
		private boolean[][] bigQ;
		private LinkedList<STDPair> littleQ;
		private int nodes_count;
		private STDPseudoV[][] matrix;
		private STDPseudoV[] inverted;
		private STDPseudoIntList[] unionF;
		private STDPseudoIntList[] unionS;

		HopcroftAlgorithm(int n, STDPseudoV[][] _matrix, STDPseudoV[] _inverted) {
			this.nodes_count = n;
			this.unionF = buildSTDIntList(n + 1);
			this.unionS = buildSTDIntList(n + 1);
			this.going = buildSTDIntList(n + 1);
			this.isEnterF = new STDPseudoIntList.STDPseudoPointer[n];
			this.isEnterS = new STDPseudoIntList.STDPseudoPointer[n];
			this.goingUnion = new STDPseudoIntList();
			this.isGoing = new boolean[this.nodes_count];
			this.isEnterGoing = new boolean[this.nodes_count];
			this.bigQ = new boolean[this.nodes_count + 1][SYMBOLS_COUNT_IN_ABC];
			this.matrix = _matrix;
			this.inverted = _inverted;
		}

		protected LinkedList<STDPair> buildLinkedPairs(int value) {
			LinkedList<STDPair> queue = new LinkedList<STDPair>();
			for (int current_symbol = 0; current_symbol < SYMBOLS_COUNT_IN_ABC; ++current_symbol) {
				queue.add(new STDPair(value, current_symbol));
			}
			return queue;
		}

		protected STDPseudoIntList[] buildSTDIntList(int minimum_count) {
			STDPseudoIntList[] temporary_list = new STDPseudoIntList[minimum_count];
			for (int index = 0; index < minimum_count; ++index) {
				temporary_list[index] = new STDPseudoIntList();
			}
			return temporary_list;
		}

		protected void zeroStep(boolean[] collectors, int[] current_union) {
			for (int current_node_id = 0; current_node_id < this.nodes_count; ++current_node_id) {
				int current_union_id = collectors[current_node_id] ? 0 : 1;
				current_union[current_node_id] = current_union_id;
				isEnterS[current_node_id] = unionS[current_union_id]
						.pushAndTake(current_node_id);
				if (!inverted[current_node_id].isEmpty()
						|| current_node_id == 0) {
					isEnterF[current_node_id] = unionF[current_union_id]
							.pushAndTake(current_node_id);
				}
			}
			int value = ((unionF[1].size() < unionF[0].size() && current_union[0] != 1) ? 1
					: 0);
			littleQ = buildLinkedPairs(value);
			Arrays.fill(bigQ[value], true);
		}

		protected void step1(int current_union_id, int symbol, int[] current_union) {
			for (Integer pointer : unionF[current_union_id]) {
				if (!matrix[pointer][symbol].isEmpty()) {
					for (int q = 0; q < matrix[pointer][symbol].getCountOfElements(); q++) {
						int i = matrix[pointer][symbol].takeElement(q);
						if (!isGoing[i]) {
							going[current_union[i]].add(i);
							isGoing[i] = true;
						}
						if (!isEnterGoing[current_union[i]]) {
							goingUnion.add(current_union[i]);
							isEnterGoing[current_union[i]] = true;
						}
					}
				}
			}
		}

		protected void step2() {
			for (Integer i : goingUnion) {
				if (going[i].size() == unionS[i].size()) {
					for (Integer j : going[i]) {
						isGoing[j] = false;
					}
					going[i] = new STDPseudoIntList();
					isEnterGoing[i] = false;
				}
			}
		}

		protected void step3(int[] current_union) {
			Iterator<Integer> iterator = goingUnion.iterator();
			while (iterator.hasNext()) {
				int i = iterator.next();
				if (isEnterGoing[i]) {
					step4(i, current_union);
					step5(i, current_union);
					++kkk;
				}
				isEnterGoing[i] = false;
				iterator.remove();
			}
		}

		private void step4(int i, int[] current_union) {
			Iterator<Integer> j = going[i].iterator();
			while (j.hasNext()) {
				int current_j = j.next();
				j.remove();
				isGoing[current_j] = false;
				STDPseudoIntList.STDPseudoPointer b = isEnterS[current_j];
				b.remove();
				isEnterS[current_j] = unionS[kkk].pushAndTake(current_j);
				current_union[current_j] = kkk;
				if (isEnterF[current_j] != null) {
					b = isEnterF[current_j];
					b.remove();
					isEnterF[current_j] = unionF[kkk].pushAndTake(current_j);
				}
			}
		}

		private void step5(int i, int[] current_union) {
			for (int current_symbol = 0; current_symbol < SYMBOLS_COUNT_IN_ABC; ++current_symbol) {
				if (bigQ[i][current_symbol]
						|| (unionF[kkk].size() < unionF[i].size() && current_union[COLLECTOR_NODE_TYPE] != kkk)
						|| current_union[COLLECTOR_NODE_TYPE] == i) {
					littleQ.add(new STDPair(kkk, current_symbol));
					bigQ[kkk][current_symbol] = true;
				} else {
					littleQ.add(new STDPair(i, current_symbol));
					bigQ[i][current_symbol] = true;
				}
			}
		}

		public int execute(boolean[] collectors, int[] current_union) {
			zeroStep(collectors, current_union);
			while (!littleQ.isEmpty()) {
				STDPair p = littleQ.poll();
				bigQ[p.s_union][p.symbol] = false;
				step1(p.s_union, p.symbol, current_union);
				step2();
				step3(current_union);
			}
			return kkk;
		}
	}

	public static class STDPseudoV {
		private int[] elements_of_stdpseudov;
		private int elements_count;

		public STDPseudoV() {
			this(10);
		}

		private STDPseudoV(int initial_size) {
			this.elements_of_stdpseudov = new int[initial_size];
		}

		public int getCountOfElements() {
			return elements_count;
		}

		public boolean isEmpty() {
			return elements_count == 0;
		}

		public void putElement(int element) {
			ensureCapacityInternal(elements_count + 1);
			elements_of_stdpseudov[elements_count++] = element;
		}

		public int takeElement(int position) {
			return elements_of_stdpseudov[position];
		}
		
		private void ensureCapacityInternal(int minimun_count) {
			if (minimun_count - elements_of_stdpseudov.length > 0) {
				makebigger(minimun_count);
			}
		}

		private void makebigger(int minimun_count) {
			int prev_value = elements_of_stdpseudov.length;
			int next_value = prev_value + (prev_value >> 1);
			elements_of_stdpseudov = Arrays.copyOf(elements_of_stdpseudov,
					next_value);
		}
	}

	public static class STDPseudoIntList extends AbstractList<Integer> {
		private STDPseudoPointer start_iterator;
		private STDPseudoPointer end_iterator;
		private int elements_count;

		public STDPseudoIntList() {
			start_iterator = new STDPseudoPointer(null, null, null);
			end_iterator = new STDPseudoPointer(null, start_iterator, null);
			start_iterator.up_pointer = end_iterator;
		}

		@Override
		public boolean isEmpty() {
			return start_iterator.up_pointer == end_iterator;
		}

		@Override
		public boolean add(Integer element) {
			new STDPseudoPointer(end_iterator, end_iterator.down_pointer,
					element);
			return true;
		}

		public STDPseudoPointer pushAndTake(Integer element) {
			return new STDPseudoPointer(end_iterator,
					end_iterator.down_pointer, element);
		}

		@Override
		public Iterator<Integer> iterator() {
			return new Iterator<Integer>() {
				STDPseudoPointer current_pointer = start_iterator;

				@Override
				public boolean hasNext() {
					return current_pointer.hasNext();
				}

				@Override
				public Integer next() {
					current_pointer = current_pointer.up_pointer;
					return current_pointer.current_element_value;
				}

				@Override
				public void remove() {
					if (current_pointer != start_iterator
							&& current_pointer != end_iterator) {
						current_pointer.remove();
					} else {
						throw (new IllegalStateException());
					}
				}
			};
		}

		@Override
		public Integer remove(int position) {
			return null;
		}

		@Override
		public Integer get(int position) {
			return null;
		}

		@Override
		public int size() {
			return elements_count;
		}

		class STDPseudoPointer {
			STDPseudoPointer up_pointer;
			STDPseudoPointer down_pointer;
			Integer current_element_value;

			public boolean hasNext() {
				return up_pointer != null && up_pointer != end_iterator;
			}

			public STDPseudoPointer(STDPseudoPointer up_p,
					STDPseudoPointer down_p, Integer e_value) {
				this.current_element_value = e_value;
				if (down_p != null) {
					down_p.up_pointer = this;
				}
				if (up_p != null) {
					up_p.down_pointer = this;
				}
				this.up_pointer = up_p;
				this.down_pointer = down_p;
				STDPseudoIntList.this.elements_count++;
			}

			public void remove() {
				down_pointer.up_pointer = up_pointer;
				up_pointer.down_pointer = down_pointer;
				STDPseudoIntList.this.elements_count--;
			}
		}
	}

	public static class STDPair {
		int s_union;
		int symbol;

		public STDPair(int set, int ch) {
			this.s_union = set;
			this.symbol = ch;
		}
	}

	public static final int SYMBOLS_COUNT_IN_ABC = 26;
	public static final int COLLECTOR_NODE_TYPE = 0;
	public static final int SOURCE_NODE_TYPE = 1;

	private static int initial_nodes_count;
	private static boolean[] collectorsFlag;
	private static int[] current_classes;
	private static int[][] edges_to;

	private static STDPseudoV[][] generateEdgesFrom() {
		STDPseudoV[][] reverse = new STDPseudoV[initial_nodes_count][SYMBOLS_COUNT_IN_ABC];
		for (int current_node_id = 0; current_node_id < initial_nodes_count; ++current_node_id) {
			for (int current_symbol = 0; current_symbol < SYMBOLS_COUNT_IN_ABC; ++current_symbol) {
				reverse[current_node_id][current_symbol] = new STDPseudoV();
			}
		}
		return reverse;
	}

	private static STDPseudoV[][] getEdgesFrom(STDPseudoV[] rev) {
		boolean[] comingFlag = new boolean[initial_nodes_count];
		for (int current_node_id = 0; current_node_id < initial_nodes_count; ++current_node_id) {
			if (!comingFlag[current_node_id] && collectorsFlag[current_node_id]) {
				dfs(current_node_id, comingFlag, rev);
			}
		}

		STDPseudoV[][] reverse = generateEdgesFrom();
		for (int current_node_id = 0; current_node_id < initial_nodes_count; ++current_node_id) {
			for (int current_symbol = 0; current_symbol < SYMBOLS_COUNT_IN_ABC; ++current_symbol) {
				if (!comingFlag[edges_to[current_node_id][current_symbol]]) {
					edges_to[current_node_id][current_symbol] = COLLECTOR_NODE_TYPE;
				}
				reverse[edges_to[current_node_id][current_symbol]][current_symbol]
						.putElement(current_node_id);
			}
		}
		return reverse;
	}

	private static void dfs(int current_node_id, boolean[] comingFlag,
			STDPseudoV[] edges_from) {
		comingFlag[current_node_id] = true;
		for (int current_child_position = 0, size = edges_from[current_node_id]
				.getCountOfElements(); current_child_position < size; current_child_position++) {
			int current_child_id = edges_from[current_node_id]
					.takeElement(current_child_position);
			if (!comingFlag[current_child_id]) {
				dfs(current_child_id, comingFlag, edges_from);
			}
		}
	}

	public static String nextToken() {
		while (st == null || !st.hasMoreTokens()) {
			try {
				st = new StringTokenizer(in.readLine());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return st.nextToken();
	}

	public static int nextInt() {
		return Integer.parseInt(nextToken());
	}

	static StringTokenizer st;
	static BufferedReader in;
	static BufferedWriter out;

	public static void main(String[] args) {
		try {
			in = new BufferedReader(new FileReader("fastminimization.in"));
			out = new BufferedWriter(new FileWriter("fastminimization.out"));

			initial_nodes_count = nextInt() + 1;
			int initial_edges_count = nextInt();
			int initial_terms_count = nextInt();

			collectorsFlag = new boolean[initial_nodes_count];
			for (int i = 0; i < initial_terms_count; ++i) {
				collectorsFlag[nextInt()] = true;
			}
			edges_to = new int[initial_nodes_count][SYMBOLS_COUNT_IN_ABC];
			STDPseudoV[] edges_from = new STDPseudoV[initial_nodes_count];
			for (int i = 0; i < initial_nodes_count; ++i) {
				edges_from[i] = new STDPseudoV();
			}
			for (int i = 0; i < initial_edges_count; ++i) {
				int from = nextInt();
				int to = nextInt();
				int ch = nextToken().charAt(0) - (int) 'a';
				edges_to[from][ch] = to;
				edges_from[to].putElement(from);
			}

			current_classes = new int[initial_nodes_count];

			HopcroftAlgorithm algorithm = new HopcroftAlgorithm(initial_nodes_count,
					getEdgesFrom(edges_from), edges_from);

			int classes_count = algorithm.execute(collectorsFlag, current_classes);

			int[] classes = new int[classes_count];
			classes[current_classes[SOURCE_NODE_TYPE]] = 1;

			int new_count_of_nodes = 1;
			int new_count_of_edges = 0;
			int new_count_of_terms = 0;

			StringBuilder generatedTerms = new StringBuilder();
			if (collectorsFlag[SOURCE_NODE_TYPE]) {
				++new_count_of_terms;
				generatedTerms.append(SOURCE_NODE_TYPE).append(" ");
			}

			StringBuilder generatedEdges = new StringBuilder();
			LinkedList<Integer> queue = new LinkedList<Integer>();
			queue.add(SOURCE_NODE_TYPE);
			while (!queue.isEmpty()) {
				int from = queue.poll();
				for (int current_symbol = 0; current_symbol < SYMBOLS_COUNT_IN_ABC; ++current_symbol) {
					if (edges_to[from][current_symbol] != COLLECTOR_NODE_TYPE) {
						int to = edges_to[from][current_symbol];
						if (classes[current_classes[to]] == 0) {
							classes[current_classes[to]] = ++new_count_of_nodes;
							if (collectorsFlag[to]) {
								generatedTerms.append(classes[current_classes[to]])
										.append(" ");
								++new_count_of_terms;
							}
							queue.add(to);
						}
						generatedEdges.append(classes[current_classes[from]]).append(" ")
								.append(classes[current_classes[to]]).append(" ");
						generatedEdges.append((char) (current_symbol + (int)'a')).append("\n");
						++new_count_of_edges;
					}
				}
			}

			StringBuilder generatedResult = new StringBuilder();
			generatedResult.append(new_count_of_nodes).append(" ")
					.append(new_count_of_edges).append(" ")
					.append(new_count_of_terms).append("\n");
			generatedResult.append(generatedTerms);
			generatedResult.append("\n");
			generatedResult.append(generatedEdges);

			out.write(generatedResult.toString());

			in.close();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}