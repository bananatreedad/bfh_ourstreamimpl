package exceptions;

import interfaces.Stream;

/**
 * Exception which can be thrown if {@link Stream} should be infinite. 
 */
public class IsInfiniteException extends RuntimeException {
	private static final long serialVersionUID = 1L;
}
