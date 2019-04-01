package ru.ifmo.ctddev.khovanskiy.task7;

import java.util.concurrent.BlockingQueue;

public class Publisher implements IEmployee {

	private BlockingQueue<Task> input;
	private int pub = 0;
	private static int uids = 0;

	/**
	 * Default constructor
	 * 
	 * @param input
	 *            input queue
	 */
	public Publisher(BlockingQueue<Task> input) {
		this.input = input;
		pub = uids++;
	}

	@Override
	public void doSomething() {
		try {
			if (input.isEmpty()) {
				return;
			}
			Task task = input.take();
			System.out.println(pub + " " + task.result);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
