package com.parksexpress.exceptions;

public class InvalidStoreException extends Exception {
	private static final long serialVersionUID = 3126648131555860713L;

	public InvalidStoreException() {
		super();
	}
	
	public InvalidStoreException(String error){
		super(error);
	}
}
