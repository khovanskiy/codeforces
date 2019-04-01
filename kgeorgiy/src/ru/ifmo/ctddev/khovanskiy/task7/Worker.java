package ru.ifmo.ctddev.khovanskiy.task7;

import java.util.concurrent.BlockingQueue;

public class Worker implements IEmployee {

	private BlockingQueue<Task> input;
	private BlockingQueue<Task> output;

	/**
	 * Default constructor
	 * 
	 * @param input
	 *            input queue
	 * @param output
	 *            output queue
	 */
	public Worker(BlockingQueue<Task> input, BlockingQueue<Task> output) {
		this.input = input;
		this.output = output;
	}

	@Override
	public void doSomething() {
		try {
			if (input.isEmpty()) {
				return;
			}
			Task task = input.take();
			task.run();
			output.put(task);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
