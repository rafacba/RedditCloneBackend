package com.rafa.exceptions;

public class RedditException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public RedditException(String exMessage) {
		super(exMessage);
	}
}
