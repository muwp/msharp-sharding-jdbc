package com.msharp.single.jdbc.exception;

/**
 * MSharpConfigException
 *
 * @author mwup
 * @version 1.0
 * @created 2018/10/29 13:51
 **/
public class MSharpConfigException extends MSharpException {

	private static final long serialVersionUID = -7049590514431591540L;

	public MSharpConfigException() {
		super();
	}

	public MSharpConfigException(String message) {
		super(message);
	}

	public MSharpConfigException(String message, Throwable cause) {
		super(message, cause);
	}

	public MSharpConfigException(Throwable cause) {
		super(cause);
	}
}
