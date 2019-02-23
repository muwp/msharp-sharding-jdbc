package com.msharp.single.jdbc.log;

import com.msharp.single.jdbc.exception.MSharpException;
import org.apache.logging.log4j.core.LoggerContext;

import java.net.URL;

/**
 * Log4j2Logger
 *
 * @author mwup
 * @version 1.0
 * @created 2019/02/22 13:51
 **/
public class Log4j2Logger implements Logger {

	private static LoggerContext context = null;
	private org.apache.logging.log4j.Logger logger;

	static {
		init();
	}

	private static synchronized void init() {
		URL url = Log4j2Logger.class.getClassLoader().getResource("zebra_log4j2.xml");

		try {
			if (null == url) {
				throw new MSharpException("log4j2 resource load failed");
			}

			LoggerContext ctx = new LoggerContext("Zebra", null, url.toURI());
			ctx.start();
			context = ctx;
		} catch (Exception e) {
			throw new MSharpException("Fail to initialize log4j2", e);
		}
	}

	public Log4j2Logger(String loggerName) {
		if (context == null) {
			init();
		}

		this.logger = context.getLogger(loggerName);
	}

	@Override
	public void debug(String message) {
		logger.debug(message);
	}

	@Override
	public void debug(String msg, Throwable e) {

	}

	@Override
	public void info(String message) {
		logger.info(message);
	}

	@Override
	public void warn(String message) {
		logger.warn(message);
	}

	@Override
	public void warn(String message, Throwable t) {
		logger.warn(message, t);
	}

	@Override
	public void error(String message) {
		logger.error(message);
	}

	@Override
	public void error(String message, Throwable t) {
		logger.error(message, t);
	}

	@Override
	public boolean isDebugEnabled() {
		return logger.isDebugEnabled();
	}
}
