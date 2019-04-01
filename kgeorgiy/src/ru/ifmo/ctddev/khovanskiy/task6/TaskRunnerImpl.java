package ru.ifmo.ctddev.khovanskiy.task6;

import java.util.LinkedList;

public class TaskRunnerImpl implements TaskRunner {

	private static class Worker implements Runnable {

		/**
		 * 
		 * @param list
		 *            conveyor of tasks
		 */
		public Worker(LinkedList<Command> queue) {
			this.queue = queue;
		}

		@Override
		public void run() {
			while (true) {
				Command command;
				synchronized (queue) {
					while (queue.isEmpty()) {
						try {
							queue.wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					command = queue.poll();
				}
				synchronized (command) {
					command.run();
					command.notify();
				}
			}
		}

		private LinkedList<Command> queue;
	}

	private static class Command<X, Y> implements Runnable {
		public Command(Task<X, Y> task, Y value) {
			this.task = task;
			this.result = null;
			this.value = value;
		}

		final Task<X, Y> task;
		X result;
		final Y value;

		@Override
		public void run() {
			result = task.run(value);
		}
	}

	private LinkedList<Command> queue;

	/**
	 * 
	 * @param nThreads
	 *            count of threads
	 */
	public TaskRunnerImpl(int nThreads) {
		queue = new LinkedList<Command>();
		for (int i = 0; i < nThreads; ++i) {
			Thread t = new Thread(new Worker(queue));
			t.start();
		}
	}

	@Override
	public <X, Y> X run(Task<X, Y> task, Y value) {
		Command<X, Y> command = new Command<X, Y>(task, value);
		synchronized (command) {
			synchronized (queue) {
				queue.add(command);
				queue.notify();
			}
			try {
				command.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return command.result;
	}

}
