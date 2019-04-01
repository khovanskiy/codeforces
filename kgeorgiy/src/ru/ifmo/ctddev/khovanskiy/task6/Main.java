package ru.ifmo.ctddev.khovanskiy.task6;

public class Main {

	private static final int THREADS_COUNT = 3;
	private static final int CLIENTS_COUNT = 3;

	public static void main(String[] args) {
		TaskRunnerImpl runner = new TaskRunnerImpl(THREADS_COUNT);

		for (int i = 0; i < CLIENTS_COUNT; ++i) {
			Thread thread = new Thread(new Client(runner));
			thread.start();
		}
	}
}
