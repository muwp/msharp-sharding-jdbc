package com.msharp.single.jdbc.log;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * LoggerFactory
 *
 * @author mwup
 * @version 1.0
 * @created 2019/02/22 13:51
 **/
public class LoggerFactory {

    private static Map<String, Logger> cache = new ConcurrentHashMap<>();

    public static Logger getLogger(Class clazz) {
        return getLogger(clazz.getName());
    }

    public static Logger getLogger(String loggerName) {
        Logger logger = cache.get(loggerName);
        if (null != logger) {
            return logger;
        }

        try {
            logger = new LoggerImpl(loggerName);
            cache.put(loggerName, logger);
        } catch (Throwable t) {
            throw new RuntimeException("Error creating LOGGER for LOGGER '" + loggerName + "'.  Cause: " + t, t);
        }
        return logger;
    }
}
