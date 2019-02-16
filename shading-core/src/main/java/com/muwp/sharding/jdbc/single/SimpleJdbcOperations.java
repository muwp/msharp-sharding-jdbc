package com.muwp.sharding.jdbc.single;

import java.util.List;

/**
 * SimpleJdbcOperations
 *
 * @author mwup
 * @version 1.0
 * @created 2019/02/15 13:51
 **/
public interface SimpleJdbcOperations {

    <T> void insert(T bean);

    <T> void update(T bean);

    <T> void delete(long id, Class<T> clazz);

    <T> T get(long id, final Class<T> clazz);

    <T> T get(String name, Object value, final Class<T> clazz);

    <T> List<T> query(final T bean);

    <T> List<T> query(String sql, Object[] params, final Class<T> clazz);
}
