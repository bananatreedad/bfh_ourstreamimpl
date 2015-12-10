package classes;

import java.util.Iterator;
import java.util.List;

import interfaces.Mapping;
import interfaces.Operator;
import interfaces.Predicate;
import interfaces.Stream;

public abstract class LazyStream<E> implements Stream<E> {

	@Override
	public boolean matchAll(Predicate<? super E> predicate) {
		Iterator<E> iterator = this.iterator();

		while (iterator.hasNext()) {
			if (!predicate.test(iterator.next()))
				return false;
		}

		return true;
	}

	@Override
	public boolean matchAny(Predicate<? super E> predicate) {
		Iterator<E> iterator = this.iterator();

		while (iterator.hasNext()) {
			if (predicate.test(iterator.next()))
				return true;
		}

		return false;
	}

	@Override
	public int countAll() {
		Iterator<E> iterator = this.iterator();

		int i = 0;
		while (iterator.hasNext()) {
			i++;
			iterator.next();
		}

		return i;
	}

	@Override
	public int count(Predicate<? super E> predicate) {
		Iterator<E> iterator = this.iterator();

		int i = 0;
		while(iterator.hasNext()) {
			if(predicate.test(iterator.next())) i++;
		}
		return i;
	}

	@Override
	public E get(int index) throws IndexOutOfBoundsException {
		Iterator<E> iterator = this.iterator();
		
		for (int i = 0; i < index; i++) {
			if(!iterator.hasNext()) throw new IndexOutOfBoundsException();
			
			if(i == index) return iterator.next();
			else iterator.next();
		}

		return null;
		//TODO other return value if ran through without change?
	}

	@Override
	public E find(Predicate<? super E> predicate) {
		Iterator<E> iterator = this.iterator();
		
		while(iterator.hasNext()) {
			E e = iterator.next();

			if(predicate.test(e)) return e;
		}

		return null;
	}

	@Override
	public E reduce(Operator<E> operator) {
		Iterator<E> iterator = this.iterator();
		
		E e = null;
		while(iterator.hasNext()) {
			e = operator.apply(e, iterator.next());
			// TODO funktioniert das mit null am anfang?
		}

		return e;
	}

	@Override
	public List<E> toList() {
		// TODO
		return null;
	}

	@Override
	public Stream<E> limit(int n) throws IllegalArgumentException {
		// TODO
		return null;
	}

	@Override
	public Stream<E> skip(int n) throws IllegalArgumentException {
		// TODO
		return null;
	}

	@Override
	public Stream<E> filter(Predicate<? super E> predicate) {
		// TODO
		return null;
	}

	public <F> Stream<F> map(Mapping<? super E, ? extends F> mapping) {
		// TODO
		return null;
	}
}