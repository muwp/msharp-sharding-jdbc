package com.muwp.sharding.jdbc.handler;

/**
 * FieldHandler
 *
 * @author mwup
 * @version 1.0
 * @created 2019/02/15 13:51
 **/
public interface FieldHandler {

    void handle(int index, String columnName, Object value);
}
