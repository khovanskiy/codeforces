package ru.ifmo.ctddev.khovanskiy.task7;

import java.util.Random;
import java.util.concurrent.BlockingQueue;

public class Producer implements IEmployee {
	private BlockingQueue<Task> queue1;

	/**
	 * Default constructor
	 * 
	 * @param queue1
	 *            output queue
	 */
	public Producer(BlockingQueue<Task> queue1) {
		this.queue1 = queue1;
	}

	@Override
	public void doSomething() {
		Random rand = new Random();

		try {
			Task task = new Task(rand.nextInt(Integer.MAX_VALUE) % 10);
			queue1.put(task);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
