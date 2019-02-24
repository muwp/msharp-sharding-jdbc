package com.msharp.single.jdbc.log;

import org.slf4j.LoggerFactory;

/**
 * Logger
 *
 * @author mwup
 * @version 1.0
 * @created 2019/02/22 13:51
 **/
public class LoggerImpl implements Logger {

    private org.slf4j.Logger logger;

    public LoggerImpl(String name) {
        logger = LoggerFactory.getLogger(name);
    }

    @Override
    public boolean isDebugEnabled() {
        return logger.isDebugEnabled();
    }

    @Override
    public void error(String msg, Throwable e) {
        logger.error(msg, e);
    }

    @Override
    public void error(String msg) {
        logger.error(msg);
    }

    @Override
    public void info(String msg) {
        logger.info(msg);
    }

    @Override
    public void debug(String msg) {
        logger.debug(msg);
    }

    @Override
    public void debug(String msg, Throwable e) {
        logger.debug(msg, e);
    }

    @Override
    public void warn(String msg) {
        logger.warn(msg);
    }

    @Override
    public void warn(String msg, Throwable e) {
        logger.warn(msg, e);
    }
}
