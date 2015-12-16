package classes;

import java.util.Iterator;
import interfaces.Mapping;
import interfaces.Predicate;

public class SeededStream<E> extends LazyStream<E> {
	
	E seed;
	Mapping<E, E> update;
	Predicate<E> condition = null;
	
	public SeededStream(E seed, Mapping<E, E> update) {
		this.seed = seed;
		this.update = update;
	}

	public SeededStream(E seed, Mapping<E, E> update, Predicate<E> condition) {
		this(seed, update);
		this.condition = condition;
	}

	@Override
	public Iterator<E> iterator() {
		Iterator<E> it = new Iterator<E>() {

			@Override
			public boolean hasNext() {
				boolean hasNext = true;
				if(condition != null) hasNext = condition.test(seed);
				return hasNext;
			}

			@Override
			public E next() {
				return update.apply(seed);
			}
		};

		return it;
	}
}