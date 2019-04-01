package ru.ifmo.ctddev.khovanskiy.task7;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Factory {

	private static final int THREADS_COUNT = 21;
	private static final int HANDLERS_COUNT = 10;
	private static final int CONVEYOR_CAPACITY = 25;

	public static void main(String[] args) {
		BlockingQueue<Task> first = new ArrayBlockingQueue<>(CONVEYOR_CAPACITY);
		BlockingQueue<Task> second = new ArrayBlockingQueue<>(CONVEYOR_CAPACITY);

		Office office = new Office(THREADS_COUNT);

		for (int i = 0; i < HANDLERS_COUNT; ++i) {
			office.add(new Producer(first));
		}
		for (int i = 0; i < HANDLERS_COUNT; ++i) {
			office.add(new Worker(first, second));
		}
		for (int i = 0; i < HANDLERS_COUNT; ++i) {
			office.add(new Publisher(second));
		}

		office.start();
	}
}
