package ru.ifmo.ctddev.khovanskiy.task7;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Office {
	private BlockingQueue<IEmployee> people = new LinkedBlockingQueue<>();

	private static class Room implements Runnable {

		private BlockingQueue<IEmployee> people;

		/**
		 * Default constructor
		 * 
		 * @param people
		 *            all employees in office
		 */
		public Room(BlockingQueue<IEmployee> people) {
			this.people = people;
		}

		@Override
		public void run() {
			while (true) {
				try {
					IEmployee em = people.take();
					em.doSomething();
					people.put(em);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private int roomsCount;
	private boolean isRunning = false;

	public Office(int roomsCount) {
		this.roomsCount = roomsCount;
	}

	/**
	 * Add the employer to this office
	 * 
	 * @param employee
	 *            employee for adding
	 */
	void add(IEmployee employee) {
		people.add(employee);
	}

	/**
	 * Start office activity
	 */
	void start() {
		if (isRunning) {
			return;
		}
		isRunning = true;
		for (int i = 0; i < roomsCount; ++i) {
			new Thread(new Room(people)).start();
		}
	}
}
