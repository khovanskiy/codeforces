package ru.ifmo.ctddev.khovanskiy.task6;

import java.util.Random;

public class Client implements Runnable {
	static class SampleTask implements Task<Integer, Integer> {
		private Integer fact(Integer value) {
			int result = 1;
			for (int i = 2; i <= value.intValue(); ++i) {
				result *= i;
				result %= Integer.MAX_VALUE / 2;
			}
			return result;
		}

		@Override
		public Integer run(Integer value) {
			return fact(fact(value));
		}
	}

	private static int ids = 0;
	private TaskRunner taskRunner;
	private int id;

	public Client(TaskRunner taskRunner) {
		this.id = ids++;
		this.taskRunner = taskRunner;
	}

	@Override
	public void run() {
		Random rand = new Random();
		while (true) {
			Integer result = taskRunner.run(new SampleTask(),
					rand.nextInt(Integer.MAX_VALUE) % 10);
			System.out.println("Client #" + id + ": " + result);
		}
	}
}
