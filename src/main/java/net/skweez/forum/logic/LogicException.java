/**
 * 
 */
package net.skweez.forum.logic;

/**
 * Exception used by the logic.
 * 
 * @author elm
 * 
 */
@SuppressWarnings("serial")
public class LogicException extends Exception {

	/**
	 * 
	 */
	public LogicException() {
		super();
	}

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public LogicException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public LogicException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message
	 */
	public LogicException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public LogicException(Throwable cause) {
		super(cause);
	}

}
