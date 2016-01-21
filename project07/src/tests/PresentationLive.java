package tests;

import classes.ArrayStream;
import classes.SeededStream;

public class PresentationLive {

	public static void main(String[] args) {
		ArrayStream<Integer> intArrStream = new ArrayStream<>(1,2,3,4,5,6,7,8,9,10);
	
		System.out.println(intArrStream.countAll());

		System.out.println(intArrStream.filter(x -> x % 2 == 0).countAll());
		
		
		SeededStream<Integer> intSeedStream = new SeededStream<Integer>(0, x -> x +2);
		
		System.out.println(intSeedStream.get(5));
		
		intSeedStream.limit(1000).forEach(System.out::println);
	}
}
