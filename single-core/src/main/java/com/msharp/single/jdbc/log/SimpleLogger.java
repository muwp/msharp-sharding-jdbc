package com.msharp.single.jdbc.log;

/**
 * SimpleLogger
 *
 * @author mwup
 * @version 1.0
 * @created 2019/02/22 13:51
 **/
public class SimpleLogger implements Logger {

	public SimpleLogger(String loggerName) {

	}

	@Override
	public void debug(String message) {
		System.out.println(message);
	}

	@Override
	public void debug(String msg, Throwable e) {

	}

	@Override
	public void info(String message) {
		System.out.println(message);
	}

	@Override
	public void warn(String message) {
		System.out.println(message);
	}

	@Override
	public void warn(String message, Throwable t) {
		System.out.println(message);
		t.printStackTrace(System.out);
	}

	@Override
	public void error(String message) {
		System.err.println(message);
	}

	@Override
	public boolean isDebugEnabled() {
		return false;
	}

	@Override
	public void error(String message, Throwable t) {
		System.err.println(message);
		t.printStackTrace(System.err);
	}
}
