package com.work.exceptions;

/**
 * Throws to indicate that data which is given to parse does't have valid json format.
 *
 * @author lilit on 7/22/16.
 */
public class JsonFormatException extends RuntimeException {

	public JsonFormatException(String message) {
		super(message);
	}

	public JsonFormatException(String message, Throwable origin) {
		super(message, origin);
	}
}
