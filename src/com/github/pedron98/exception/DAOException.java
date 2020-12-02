package com.github.pedron98.exception;

public class DAOException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public DAOException(String message, int code) {
		super(message);
	}	
}
