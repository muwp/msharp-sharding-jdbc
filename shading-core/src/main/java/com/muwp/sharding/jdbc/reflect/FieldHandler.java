package com.muwp.sharding.jdbc.reflect;

import java.lang.reflect.Field;

/**
 * FieldHandler
 *
 * @author mwup
 * @version 1.0
 * @created 2019/02/15 13:51
 **/
public interface FieldHandler {

	 void handle(int index, Field field, Object value);
}
