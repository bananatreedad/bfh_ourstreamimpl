package classes;

import java.util.Iterator;
import interfaces.Mapping;
import interfaces.Predicate;

public class SeededStream<E> extends LazyStream<E> {
	
	private E seed;
	private Mapping<E, E> update;
	private Predicate<E> condition = null;
	
	public SeededStream(E seed, Mapping<E, E> update) {
		this.seed = seed;
		this.update = update;
		
		setInfinite();
	}

	public SeededStream(E seed, Mapping<E, E> update, Predicate<E> condition) {
		this.seed = seed;
		this.update = update;

		this.condition = condition;
	}

	@Override
	public Iterator<E> iterator() {
		Iterator<E> it = new Iterator<E>() {
			
			E itSeed = seed;
			boolean isFirstNext = true;

			@Override
			public boolean hasNext() {
				boolean hasNext = true;
				if(condition != null) hasNext = condition.test(update.apply(itSeed));
				return hasNext;
			}

			@Override
			public E next() {
				if(isFirstNext) {
					isFirstNext = false;
					return itSeed;
				}
				itSeed = update.apply(itSeed);
				return itSeed;
			}
		};

		return it;
	}
}