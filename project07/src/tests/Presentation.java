package tests;

import classes.ArrayStream;
import classes.SeededStream;

public class Presentation {

	public static void main(String[] args) {

		//ArrayStream
		ArrayStream<Integer> stringArrStr = new ArrayStream<>(1,2,3,4,5,6,7,8,9,10);
		
		System.out.println(stringArrStr.countAll());
		stringArrStr.filter(x -> x % 2 == 0).forEach(System.out::println);;
		stringArrStr.limit(3).forEach(System.out::println);
		
		SeededStream<Integer> seededStr = new SeededStream<Integer>(0, x -> x + 2);
		
		System.out.println(seededStr.get(2));
		
		

	}
}
