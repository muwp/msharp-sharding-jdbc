package com.msharp.single.jdbc.log;

/**
 * EmptyLogger
 *
 * @author mwup
 * @version 1.0
 * @created 2019/02/22 13:51
 **/
public class EmptyLogger implements Logger {

    public EmptyLogger(String loggerName) {

    }

    @Override
    public boolean isDebugEnabled() {
        return false;
    }

    @Override
    public void error(String msg, Throwable e) {

    }

    @Override
    public void error(String msg) {

    }

    @Override
    public void info(String msg) {

    }

    @Override
    public void debug(String msg) {

    }

    @Override
    public void debug(String msg, Throwable e) {

    }

    @Override
    public void warn(String msg) {

    }

    @Override
    public void warn(String msg, Throwable e) {

    }
}
