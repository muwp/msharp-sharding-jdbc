package com.muwp.sharding.jdbc.core.jdbc;

import java.util.List;

/**
 * HighLevelShardJdbcOperations
 *
 * @author mwup
 * @version 1.0
 * @created 2019/02/15 13:51
 **/
public interface HighLevelShardJdbcOperations extends ShardJdbcOperations {

    <K, T> void insert(K splitKey, T bean);

    <K, T> void update(K splitKey, T bean);

    <K, T> void delete(K splitKey, long id, Class<T> clazz);

    <K, T> T get(K splitKey, long id, final Class<T> clazz);

    <K, T> T get(K splitKey, String key, String value, final Class<T> clazz);

    <K, T> List<T> search(K splitKey, T bean);

    <K, T> List<T> search(K splitKey, T bean, String name, Object valueFrom, Object valueTo);

    <K, T> List<T> search(K splitKey, T bean, String name, Object value);
}
