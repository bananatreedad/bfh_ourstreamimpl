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
	public E get(int index) throws IndexOutOfBoundsException {
		if(index == 0) return seed;
		else return super.get(index);
	};

	@Override
	public Iterator<E> iterator() {
		Iterator<E> it = new Iterator<E>() {
			
			@Override
			public boolean hasNext() {
				boolean hasNext = true;
				if(condition != null) hasNext = condition.test(update.apply(seed));
				return hasNext;
			}

			@Override
			public E next() {
				seed = update.apply(seed);
				return seed;
			}
		};

		return it;
	}
}