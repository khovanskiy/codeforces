import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.StringTokenizer;
import java.util.TreeSet;

public class Schedule {
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

	static <T> void trace(T s) {
		System.out.println(s);
	}

	static void trace(String s) {
		System.out.println(s);
	}

	static StringTokenizer st;
	static BufferedReader in;
	static BufferedWriter out;

	static class Task {
		public int d;
		public int w;

		public Task(int d, int w) {
			this.d = d;
			this.w = w;
		}

		@Override
		public String toString() {
			return "[" + d + ", " + w + "]";
		}
	}

	static class WeightDown implements Comparator<Task> {

		@Override
		public int compare(Task o1, Task o2) {
			return -Integer.compare(o1.w, o2.w);
		}

	}

	public static void main(String[] args) {
		try {
			in = new BufferedReader(new FileReader("schedule.in"));
			out = new BufferedWriter(new FileWriter("schedule.out"));

			int totalTasksCount = nextInt();
			Task[] schedulersTasks = new Task[totalTasksCount];
			for (int iterator = 0; iterator < totalTasksCount; ++iterator) {
				int d = nextInt();
				int w = nextInt();
				schedulersTasks[iterator] = new Task(d, w);
			}
			Arrays.sort(schedulersTasks, new WeightDown());

			TreeSet<Integer> schedulersTimes = new TreeSet<Integer>();
			for (int iterator = 0; iterator < totalTasksCount; ++iterator) {
				schedulersTimes.add(iterator);
			}

			long result = 0;
			for (int iterator = 0; iterator < totalTasksCount; ++iterator) {
				Integer nearestTime = schedulersTimes.lower(schedulersTasks[iterator].d);
				if (nearestTime != null) {
					schedulersTimes.remove(nearestTime);
				} else {
					result += schedulersTasks[iterator].w;
				}
			}

			out.write(result + "\n");

			in.close();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}