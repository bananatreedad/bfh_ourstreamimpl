package classes.teacherCorr;

import java.util.Iterator;

import interfaces.Mapping;
import interfaces.Predicate;

/**
 * The purpose of this class is to construct a stream from a given initial element ('the seed') and an update operation
 * 'update'.
 * 
 * Through the second constructor it is possible to set a certain condition under which the stream should end.
 *
 */
public class SeededStream<E> extends LazyStream<E> {

	private E seed;
	private Mapping<E, E> update;

	// KOMMENTAR: Ich empfehle, an dieser Stelle keine Zuweisung zu machen (ausser bei Kostanten). Das Zuweisen der
	// Initialwerte ist Aufgabe der Konstruktoren, selbst im Fall von null.
	private Predicate<E> condition = null;

	public SeededStream(E seed, Mapping<E, E> update) {
		this.seed = seed;
		this.update = update;

		// KOMMENTAR: Ich wuerde das Setzen der Variable isFinite in der Klasse LazyStream eher ueber den Konstruktor
		// machen, welcher dann hier mittels super(false) bzw. super(true) aufgerufen wird. Das Initialisieren der
		// Instanzvariablen ist normalerweise Aufgabe der Konstruktoren, vor allem wenn diese final (unveränderlich)
		// sind.
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

			// KOMMENTAR: Ich würde die Variable eher currentElement nennen, denn ein "Seed" is es nur ganz am Anfang
			// (siehe Kommentar weiter unten)
			E itSeed = seed;
			boolean isFirstNext = true;

			@Override
			public boolean hasNext() {
				// KOMMENTAR: Die Hilfsvariable hasNext braucht es nicht
				boolean hasNext = true;
				if (condition != null)
					// KOMMENTAR: Wenn hasNext() vor next() aufgerufen wird, z.B. in einer Schleife, dann wird
					// update.apply(itSeed) immer doppelt berechnet, was unschoen ist, besonders wenn das apply eine
					// aufwendige Operation ist. Besser wäre es, den Wert in hasNext() abzuspeichern und dann in next()
					// zurückzugeben.
					hasNext = condition.test(update.apply(itSeed));
				return hasNext;
			}

			@Override
			public E next() {
				if (isFirstNext) {
					isFirstNext = false;
					return itSeed;
				}
				itSeed = update.apply(itSeed);
				return itSeed;
			}
		};

		return it;
		// KOMMENTAR: man könnte auch direkt return new Iterator<>() {...} schreiben
	}
}