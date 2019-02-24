package com.msharp.single.jdbc.monitor.tracker;

/**
 * InitialInstanceThreadLocal
 *
 * @author mwup
 * @version 1.0
 * @created 2019/02/23 13:51
 **/
public class InitialInstanceThreadLocal<T> extends ThreadLocal<T> {

    private final Class<T> clazz;

    public InitialInstanceThreadLocal(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    protected T initialValue() {
        try {
            return clazz.newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
