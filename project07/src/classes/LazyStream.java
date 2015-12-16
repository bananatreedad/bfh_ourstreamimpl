package classes;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import interfaces.Mapping;
import interfaces.Operator;
import interfaces.Predicate;
import interfaces.Stream;
import javafx.util.Callback;

public abstract class LazyStream<E> implements Stream<E> {

	// <3
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
		while (iterator.hasNext()) {
			if (predicate.test(iterator.next()))
				i++;
		}
		return i;
	}

	
	/**
	 * 
	 */
	@Override
	public E get(int index) throws IndexOutOfBoundsException {
		Iterator<E> iterator = this.iterator();
		
		E e = getRootValue();
	
		for (int i = 0; i < index ; i++) {
			//if nothing to take is left - throw exception
			if(!iterator.hasNext()) throw new IndexOutOfBoundsException();
			e = iterator.next();
		}
		
		return e;
	}

	/**
	 * getRootValue should be implemented to get the value in case of a get at index 0
	 * 
	 * @return
	 */
	public abstract E getRootValue(); 

	//TODO W T F ?

	@Override
	public E find(Predicate<? super E> predicate) {
		Iterator<E> iterator = this.iterator();

		while (iterator.hasNext()) {
			E e = iterator.next();

			if (predicate.test(e))
				return e;
		}

		return null;
	}

	@Override
	public E reduce(Operator<E> operator) {
		Iterator<E> iterator = this.iterator();

		E e = null;
		while (iterator.hasNext()) {
			e = operator.apply(e, iterator.next());
			// TODO funktioniert das mit null am anfang?
		}

		return e;
	}

	@Override
	public List<E> toList() {
		Iterator<E> iterator = this.iterator();
		List<E> list = new LinkedList<>();

		while (iterator.hasNext()) {
			list.add(iterator.next());
		}

		return list;
	}

	@Override
	public Stream<E> limit(int n) throws IllegalArgumentException {

		for (int i = 0; i < n; i++) {
			if (!iterator().hasNext())
				throw new IllegalArgumentException();
			iterator().next();
		}

		while (iterator().hasNext()) {
			iterator().remove();
		}

		return this;
	}

	@Override
	public Stream<E> skip(int n) throws IllegalArgumentException {

		// TODO add exception
		for (int i = 0; i < n; i++) {
			if (iterator().hasNext())
				iterator().next();
		}

		Stream<E> stream = new LazyStream<E>() {

			final Stream<E> oldStream = this;

			@Override
			public Iterator<E> iterator() {
				return new Iterator<E>() {

					final Iterator<E> iterator = oldStream.iterator();

					@Override
					public boolean hasNext() {
						return iterator.hasNext();
					}

					@Override
					public E next() {
						return iterator.next();
					}
				};
			};
		};

		return stream;
	}

	@Override
	public Stream<E> filter(Predicate<? super E> predicate) {

		final Stream<E> thisStream = this;

//		final Stream<E> newStream = new LazyStream<E>() {
//
//			@Override
//			public Iterator iterator() {
//				return new Iterator<E>() {
//
//					final Iterator<E> thisIterator = thisStream.iterator();
//
//					@Override
//					public boolean hasNext() {
//						return predicate.test(thisIterator.next());
//					}
//
//					@Override
//					public E next() {
//						if(predicate.test(thisIterator.next()))
//						return null;
//					}
//				};
//			}
//
//		};

		return thisStream;
	}

	public <F> Stream<F> map(Mapping<? super E, ? extends F> mapping) {

		// TODO this is the example solved with teacher

		final Stream<E> thisStream = this;

		Stream<F> newStream = new LazyStream<F>() {
			@Override
			public Iterator<F> iterator() {

				return new Iterator<F>() {

					final Iterator<E> it = thisStream.iterator();

					@Override
					public boolean hasNext() {
						return it.hasNext();
					}

					@Override
					public F next() {
						return mapping.apply(it.next());
					}
				};
			}

		};

		return newStream;
	}
}
