package tests;

import java.util.Iterator;

import classes.SeededStream;
import interfaces.Stream;

/**
 * Tests we needed to do some tests more about our {@link Stream}-Implementation
 * than the given JUnit tests.
 *
 */
public class OurTests {

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

		Stream<Integer> s = stream.filter(x -> x % 2 == 0);

		Iterator<Integer> it2 = s.iterator();

		System.out.println(it2.next());
		System.out.println(it2.next());
		System.out.println(it2.next());

		System.out.println("#####");

		Iterator<Integer> it4 = s.filter(x -> true).iterator();

		System.out.println(it4.next());
		System.out.println(it4.next());
		System.out.println(it4.next());
		System.out.println(it4.next());

		System.out.println("###it5###");
		Iterator<Integer> it5 = stream.limit(2).iterator();

		System.out.println(it5.next());
		System.out.println(it5.next());
		System.out.println(it5.next());

		System.out.println("###foreach###");
		stream.filter(x -> true).forEach(System.out::println);

		System.out.println("####skip####");
		stream.skip(3).forEach(System.out::println);

		System.out.println("###limit###");
		SeededStream<Integer> stream2 = new SeededStream<Integer>(1, x -> x + 2);
		// endless loop
		// stream2.forEach(System.out::println);
		System.out.println(stream2.limit(5).countAll());

		tests2();
	}

	private static void tests2() {

		SeededStream<String> stream = new SeededStream<String>("", x -> x + "X", x -> x.length() < 20);

		// stream.forEach(System.out::println);

		String s = stream.reduce((param1, param2) -> param1 + param2);
		System.out.println(s.length());

		System.out.println(stream.skip(19).countAll());

		SeededStream<Integer> stream2 = new SeededStream<Integer>(0, x -> x + 1, x -> x < 10);

		System.out.println(stream2.countAll());

		// return a new filter containing the even numbers
		Stream<Integer> stream2Even = stream2.filter(x -> x % 2 == 0);

		// do for each entry in stream2Even
		stream2Even.forEach(x -> {
			System.out.print("blabla ");
			System.out.print(x + " ");
		});

		// mapping Integer to String
		Stream<String> stream2String = stream2.map(x -> "Nummer: " + x);
		stream2String.forEach(System.out::println);

		stream2.count(x -> x == 2);
	}
}
