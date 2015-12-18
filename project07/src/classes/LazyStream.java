package classes;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

import exceptions.IsInfiniteException;
import interfaces.Mapping;
import interfaces.Operator;
import interfaces.Predicate;
import interfaces.Stream;

public abstract class LazyStream<E> implements Stream<E> {

	private boolean isFinite = true;

	// a child class is able to set itself to infinite
	protected void setInfinite() {
		isFinite = false;
	}

	// <3
	@Override
	public boolean matchAll(Predicate<? super E> predicate) {
		if (!isFinite)
			throw new IsInfiniteException();
		Iterator<E> iterator = this.iterator();

		while (iterator.hasNext()) {
			if (!predicate.test(iterator.next()))
				return false;
		}

		return true;
	}

	@Override
	public boolean matchAny(Predicate<? super E> predicate) {
		if (!isFinite)
			throw new IsInfiniteException();
		Iterator<E> iterator = this.iterator();

		while (iterator.hasNext()) {
			if (predicate.test(iterator.next()))
				return true;
		}

		return false;
	}

	@Override
	public int countAll() {
		if (!isFinite)
			throw new IsInfiniteException();

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
		if (!isFinite)
			throw new IsInfiniteException();
		Iterator<E> iterator = this.iterator();

		int i = 0;
		while (iterator.hasNext()) {
			if (predicate.test(iterator.next()))
				i++;
		}
		return i;
	}

	@Override
	public E get(int index) throws IndexOutOfBoundsException {
		Iterator<E> it = iterator();

		E e = null;
		if (index < 0)
			throw new IndexOutOfBoundsException();

		// TODO check if again i'm not in the mood right now
		for (int i = 0; i <= index; i++) {
			if (!it.hasNext())
				throw new IndexOutOfBoundsException();

			e = it.next();
		}

		return e;
	}

	@Override
	public E find(Predicate<? super E> predicate) {
		if (!isFinite)
			throw new IsInfiniteException();
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
		if (!isFinite)
			throw new IsInfiniteException();
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
		if (!isFinite)
			throw new IsInfiniteException();
		Iterator<E> iterator = this.iterator();
		List<E> list = new LinkedList<>();

		while (iterator.hasNext()) {
			list.add(iterator.next());
		}

		return list;
	}

	@Override
	public Stream<E> limit(int n) throws IllegalArgumentException {

		final Stream<E> oldStream = this;

		Stream<E> stream = new LazyStream<E>() {

			@Override
			public Iterator<E> iterator() {

				return new Iterator<E>() {

					final Iterator<E> thisIt = oldStream.iterator();

					int counter = 0;

					@Override
					public boolean hasNext() {
						if (thisIt.hasNext() && counter < n) {
							return true;
						}
						return false;
					}

					@Override
					public E next() {
						counter++;
						return thisIt.next();
					}
				};
			}
		};

		return stream;
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

		Stream<E> thisStream = this;

		Stream<E> newStream = new LazyStream<E>() {

			@Override
			public Iterator<E> iterator() {
				return new Iterator<E>() {

					Iterator<E> thisIterator = thisStream.iterator();
					E nextIfThere = null;

					@Override
					public boolean hasNext() {
						if (!isFinite)
							return true;

						try {
							E e = next();
							nextIfThere = e;
							return true;
						} catch (NoSuchElementException e) {
							return false;
						}
					}

					@Override
					public E next() {
						if (nextIfThere != null) {
							E e = nextIfThere;
							nextIfThere = null;
							return e;
						} else {
							while (thisIterator.hasNext()) {
								E e = thisIterator.next();
								if (predicate.test(e))
									return e;
							}
							throw new NoSuchElementException();
						}
					}
				};
			}
		};

		return newStream;
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
