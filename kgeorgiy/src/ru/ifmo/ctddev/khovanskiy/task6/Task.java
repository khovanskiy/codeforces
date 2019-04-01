package ru.ifmo.ctddev.khovanskiy.task6;

public interface Task<X, Y> {
	/**
	 * Execute current task
	 * 
	 * @param value
	 *            initial value
	 * @return result of tsak
	 */
	X run(Y value);
}
