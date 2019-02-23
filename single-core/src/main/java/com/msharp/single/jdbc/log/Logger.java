package com.msharp.single.jdbc.log;

/**
 * Logger
 *
 * @author mwup
 * @version 1.0
 * @created 2019/02/22 13:51
 **/
public interface Logger {

    boolean isDebugEnabled();

    void error(String msg, Throwable e);

    void error(String msg);

    void info(String msg);

    void debug(String msg);

    void debug(String msg, Throwable e);

    void warn(String msg);

    void warn(String msg, Throwable e);
}
