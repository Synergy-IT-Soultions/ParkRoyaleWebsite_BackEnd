/**
 * 
 */
package org.sits.pr.api.exception.custom;

/**
 * @author vprasath
 *
 */
public class TokenExpiredException extends Exception {

	/**
	 * 
	 */
	public TokenExpiredException() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 */
	public TokenExpiredException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param cause
	 */
	public TokenExpiredException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 * @param cause
	 */
	public TokenExpiredException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public TokenExpiredException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

}
