package tests;

import java.util.Iterator;

import classes.SeededStream;
import interfaces.Stream;

public class MyTests {

	public static void main(String[] args) {
		SeededStream<Integer> stream = new SeededStream<Integer>(1, x -> x + 1, x -> x < 10);
		Iterator<Integer> it = stream.iterator();

		System.out.println(it.next());
		System.out.println(it.next());
		System.out.println(it.next());

		System.out.println("#####");

		System.out.println(stream.get(0));
		System.out.println(stream.get(2));
		System.out.println(stream.get(3));
		System.out.println(stream.get(1));

		System.out.println("#####");
		
		Stream<Integer> s = stream.filter(x -> x%2 == 0);

		Iterator<Integer> it2 = s.iterator();

		System.out.println(it2.next());
		System.out.println(it2.next());
		System.out.println(it2.next());
		
		System.out.println("#####");
		
		Iterator<Integer> ite = stream.filter(x -> true).iterator();

		System.out.println(ite.next());
		System.out.println(ite.next());
		System.out.println(ite.next());
	}
}
