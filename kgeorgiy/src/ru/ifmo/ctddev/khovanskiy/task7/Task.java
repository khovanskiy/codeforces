package ru.ifmo.ctddev.khovanskiy.task7;

public class Task {
	/**
	 * Initial value of this task
	 */
	public int value;
	/**
	 * Result of this task
	 */
	public int result;

	/**
	 * 
	 * @param producer_id
	 *            producer id
	 * @param value
	 *            initial value
	 */
	public Task(int value) {
		this.value = value;
	}

	/**
	 * Execute this task
	 */
	public void run() {
		result = f(f(value));
	}

	private int f(int n) {
		int result = 1;
		for (int i = 2; i <= n; ++i) {
			result *= i;
			result %= Integer.MAX_VALUE / 2;
		}
		return result;
	}
}
