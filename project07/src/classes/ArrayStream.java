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
	
	@SafeVarargs
	public ArrayStream(E... args) {
		for (E e : args) {
			list.add(e);
		}
	}

	@Override
	public Iterator<E> iterator() {
		return new Iterator<E>() {

			@Override
			public boolean hasNext() {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public E next() {
				// TODO Auto-generated method stub
				return null;
			}
		};
	}
}
