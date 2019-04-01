package ru.ifmo.ctddev.khovanskiy.task1;

public class Console {
	public static <T> void print(T string) {
		System.out.print(string + "\n");
	}

	public static void print(byte[] array) {
		System.out.print("[");
		for (int i = 0; i < array.length - 1; ++i) {
			System.out.print((array[i] & 0xFF) + ",");
		}
		System.out.print((array[array.length - 1] & 0xFF) + "]\n");
	}
}
