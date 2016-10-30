package org.rss.db.dao;

/**
 * Created by Alain on 11/09/2016.
 */
public class ErrorJpaException extends Exception {

	public ErrorJpaException(String message) {
		super(message);
	}

	public ErrorJpaException(String message, Throwable cause) {
		super(message, cause);
	}
}
