package tests;

import classes.SeededStream;

public class MyTests {

	public static void main(String[] args) {
		SeededStream<Integer> stream = new SeededStream<Integer>(1, x -> x + 1);

		System.out.println(stream.get(0));
		System.out.println(stream.get(1));
		System.out.println(stream.get(2));
		System.out.println(stream.get(3));
		System.out.println(stream.get(4));
	}
}
