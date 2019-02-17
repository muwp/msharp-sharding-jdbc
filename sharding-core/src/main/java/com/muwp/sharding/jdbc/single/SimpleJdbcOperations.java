package com.muwp.sharding.jdbc.single;

import org.springframework.jdbc.core.RowMapper;

import java.util.List;

/**
 * SimpleJdbcOperations
 *
 * @author mwup
 * @version 1.0
 * @created 2019/02/15 13:51
 **/
public interface SimpleJdbcOperations {

    <T> int insert(T bean);

    <T> int update(T bean);

    <T> int delete(long id, Class<T> clazz);

    <T> T load(long id, final Class<T> clazz);

    <T> T get(String name, Object value, final Class<T> clazz);

    <Request> List<Request> query(final Request bean);

    <Request, Result> List<Result> query(final Request bean, RowMapper<Result> rowMapper);

    <T> List<T> query(String sql, Object[] params, final Class<T> clazz);
}
