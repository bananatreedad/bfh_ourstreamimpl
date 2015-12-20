package classes;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

import com.sun.org.apache.bcel.internal.generic.RETURN;

import exceptions.IsInfiniteException;

import interfaces.Mapping;
import interfaces.Operator;
import interfaces.Predicate;
import interfaces.Stream;

/**
 * Implements all the methods of the {@link Stream<E>} interface except
 * {@link Iterator<E>} iterator().
 * <p>
 * "Lazy" means that the elements of the stream are not stored explicitly, they
 * are computed only when a terminal operation (see below) is called.
 * 
 * @param <E>
 */
public abstract class LazyStream<E> implements Stream<E> {

	private boolean isFinite = true;

	/**
	 * With this function a child class is able to set itself to infinite, e.g.
	 * a {@link SeededStream} without an ending condition.
	 * <p>
	 */
	protected void setInfinite() {
		isFinite = false;
	}

	/**
	 * Checks if every element of the {@link LazyStream} matches the given
	 * predicate.
	 * 
	 * @param predicate
	 *            The predicate to match.
	 * 
	 * @return True if all elements are matching the given predicate.
	 */
	@Override
	public boolean matchAll(Predicate<? super E> predicate) {
		// iterating over 'all' elements in an infinite Stream would create an endless loop
		if (!isFinite)
			throw new IsInfiniteException();

		Iterator<E> iterator = this.iterator();

		while (iterator.hasNext()) {
			if (!predicate.test(iterator.next()))
				return false;
		}

		return true;
	}

	/**
	 * Checks if minimum one element of the {@link LazyStream} matches the given
	 * predicate.
	 * 
	 * @param predicate
	 *            The predicate to match.
	 * 
	 * @return True if predicate matches one ore more elements.
	 * 
	 */
	@Override
	public boolean matchAny(Predicate<? super E> predicate) {
		// only commented out because of the tests. like this the user is
		// responsible to create no infinite loop.

		// if (!isFinite)
		// throw new IsInfiniteException();

		Iterator<E> iterator = this.iterator();

		while (iterator.hasNext()) {
			if (predicate.test(iterator.next()))
				return true;
		}

		return false;
	}

	/**
	 * Counts all the elements of the {@link LazyStream}.
	 * 
	 * @return The number of elements the {@link LazyStream} contains.
	 */
	@Override
	public int countAll() {
		// iterating over 'all' elements in an infinite Stream would create an endless loop
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

	/**
	 * Counts all the elements of the {@link LazyStream} matching the given {@link Predicate}.
	 * 
	 * @param predicate The predicate to match.
	 * 
	 * @return The number of elements the {@link LazyStream} contains.
	 */
	@Override
	public int count(Predicate<? super E> predicate) {
		// iterating over 'all' elements in an infinite Stream would create an endless loop
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

	/**
	 * Gets the element of a specific index out of the {@link LazyStream}.
	 * 
	 * @param index The index of the wanted element.
	 * 
	 * @return Element at the given index.
	 */ 
	@Override
	public E get(int index) throws IndexOutOfBoundsException {
		Iterator<E> it = iterator();

		E e = null;
		if (index < 0)
			throw new IndexOutOfBoundsException();

		for (int i = 0; i <= index; i++) {
			if (!it.hasNext())
				throw new IndexOutOfBoundsException();

			e = it.next();
		}

		return e;
	}

	/**
	 * Finds and returns the first element in the {@link LazyStream} that matches the given {@link Predicate};
	 */
	@Override
	public E find(Predicate<? super E> predicate) {
		// only commented out because of the tests. like this the user is
		// responsible to create no infinite loop.

		// if (!isFinite)
		// throw new IsInfiniteException();
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

		if (iterator.hasNext())
			e = iterator.next();

		while (iterator.hasNext()) {
			e = operator.apply(e, iterator.next());
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
		if (n < 0)
			throw new IllegalArgumentException();

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
		if (n < 0)
			throw new IllegalArgumentException();

		final Stream<E> oldStream = this;

		Stream<E> stream = new LazyStream<E>() {

			@Override
			public Iterator<E> iterator() {
				return new Iterator<E>() {

					final Iterator<E> iterator = oldStream.iterator();

					boolean skipped = false;

					@Override
					public boolean hasNext() {
						skipIfNecessary();
						return iterator.hasNext();
					}

					@Override
					public E next() {
						skipIfNecessary();
						return iterator.next();
					}

					private void skipIfNecessary() {
						if (!skipped) {
							for (int i = 0; i < n; i++) {
								if (iterator.hasNext())
									iterator.next();
								// throw error if user tries to skip more
								// elements
								// than the stream
								// actually contains
							}
							skipped = true;
						}
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
