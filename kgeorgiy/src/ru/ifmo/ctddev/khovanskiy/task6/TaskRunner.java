package ru.ifmo.ctddev.khovanskiy.task6;

public interface TaskRunner {
	<X, Y> X run(Task<X, Y> task, Y value);
}
