package com.msharp.single.jdbc.exception;

/**
 * MSharpException
 *
 * @author mwup
 * @version 1.0
 * @created 2018/10/29 13:51
 **/
public class MSharpException extends RuntimeException {

	private static final long serialVersionUID = -8628442877335107998L;

	public MSharpException() {
		super();
	}

	public MSharpException(String message) {
		super(message);
	}

	public MSharpException(String message, Throwable cause) {
		super(message, cause);
	}

	public MSharpException(Throwable cause) {
		super(cause);
	}
}
