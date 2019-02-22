package com.msharp.sharding.jdbc.handler;

/**
 * SqlHandler
 *
 * @author mwup
 * @version 1.0
 * @created 2019/02/15 13:51
 **/
public interface SqlHandler {

    void handle(int index, String columnName, Object value);
}
