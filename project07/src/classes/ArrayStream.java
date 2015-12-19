package classes;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import interfaces.Mapping;
import interfaces.Operator;
import interfaces.Predicate;
import interfaces.Stream;

public class ArrayStream<E> extends LazyStream<E>{
 	
	ArrayList<E> list = new ArrayList<>();
	
	private E[] array;

	@SafeVarargs
	public ArrayStream(E... args) {
		array = args;
	}

	@Override
	public Iterator<E> iterator() {
		return new Iterator<E>() {
			
			int currentIndex = 0;

			@Override
			public boolean hasNext() {
				return currentIndex < array.length;
			}

			@Override
			public E next() {
				E e = array[currentIndex];
				currentIndex++;
				return e;
			}
		};
	}
}
