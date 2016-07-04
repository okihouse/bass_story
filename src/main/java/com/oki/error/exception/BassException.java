package com.oki.error.exception;

public abstract class BassException extends Exception{

	private static final long serialVersionUID = -1135602677055419438L;

	public abstract int errorCode();
	
	public abstract String errorMessage();
	
}
