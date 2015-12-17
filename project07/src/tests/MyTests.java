package tests;

import java.util.Iterator;

import classes.SeededStream;

public class MyTests {

	public static void main(String[] args) {
		SeededStream<Integer> stream = new SeededStream<Integer>(1, x -> x + 1);
		Iterator<Integer> it = stream.iterator();

		System.out.println(it.next());
		System.out.println(it.next());
		System.out.println(it.next());

		System.out.println("#####");
		System.out.println(stream.get(0));
		System.out.println(stream.get(2));
		System.out.println(stream.get(3));
		System.out.println(stream.get(1));
	}
}
