package com.work.exceptions;

/**
 * Throws to indicate that connection to some url has failed.
 *
 * @author lilit on 7/22/16.
 */
public class DataFetchingException extends RuntimeException {

	public DataFetchingException(String message, Throwable origin) {
		super(message, origin);
	}

}
