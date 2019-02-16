package com.muwp.sharding.jdbc.core;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.*;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * SplitJdbcOperations
 *
 * @author mwup
 * @version 1.0
 * @created 2019/02/15 13:51
 **/
public interface SplitJdbcOperations {

    <T, K> T execute(K splitKey, ConnectionCallback<T> action) throws DataAccessException;

    <T, K> T execute(K splitKey, StatementCallback<T> action) throws DataAccessException;

    <K> void execute(K splitKey, String sql) throws DataAccessException;

    <T, K> T query(K splitKey, String sql, ResultSetExtractor<T> rse) throws DataAccessException;

    <K> void query(K splitKey, String sql, RowCallbackHandler rch) throws DataAccessException;

    <T, K> List<T> query(K splitKey, String sql, RowMapper<T> rowMapper) throws DataAccessException;

    <T, K> T queryForObject(K splitKey, String sql, RowMapper<T> rowMapper) throws DataAccessException;

    <T, K> T queryForObject(K splitKey, String sql, Class<T> requiredType) throws DataAccessException;

    <K> Map<String, Object> queryForMap(K splitKey, String sql) throws DataAccessException;

    <T, K> List<T> queryForList(K splitKey, String sql, Class<T> elementType) throws DataAccessException;

    <K> List<Map<String, Object>> queryForList(K splitKey, String sql) throws DataAccessException;

    <K> SqlRowSet queryForRowSet(K splitKey, String sql) throws DataAccessException;

    <K> int update(K splitKey, String sql) throws DataAccessException;

    <K> int[] batchUpdate(K splitKey, String... sql) throws DataAccessException;

    <T, K> T execute(K splitKey, PreparedStatementCreator psc, PreparedStatementCallback<T> action) throws DataAccessException;

    <T, K> T execute(K splitKey, String sql, PreparedStatementCallback<T> action) throws DataAccessException;

    <T, K> T query(K splitKey, PreparedStatementCreator psc, ResultSetExtractor<T> rse) throws DataAccessException;

    <T, K> T query(K splitKey, String sql, PreparedStatementSetter pss, ResultSetExtractor<T> rse) throws DataAccessException;

    <T, K> T query(K splitKey, String sql, Object[] args, int[] argTypes, ResultSetExtractor<T> rse) throws DataAccessException;

    <T, K> T query(K splitKey, String sql, Object[] args, ResultSetExtractor<T> rse) throws DataAccessException;

    <T, K> T query(K splitKey, String sql, ResultSetExtractor<T> rse, Object... args) throws DataAccessException;

    <K> void query(K splitKey, PreparedStatementCreator psc, RowCallbackHandler rch) throws DataAccessException;

    <K> void query(K splitKey, String sql, PreparedStatementSetter pss, RowCallbackHandler rch) throws DataAccessException;

    <K> void query(K splitKey, String sql, Object[] args, int[] argTypes, RowCallbackHandler rch) throws DataAccessException;

    <K> void query(K splitKey, String sql, Object[] args, RowCallbackHandler rch) throws DataAccessException;

    <K> void query(K splitKey, String sql, RowCallbackHandler rch, Object... args) throws DataAccessException;

    <T, K> List<T> query(K splitKey, PreparedStatementCreator psc, RowMapper<T> rowMapper) throws DataAccessException;

    <T, K> List<T> query(K splitKey, String sql, PreparedStatementSetter pss, RowMapper<T> rowMapper) throws DataAccessException;

    <T, K> List<T> query(K splitKey, String sql, Object[] args, int[] argTypes, RowMapper<T> rowMapper) throws DataAccessException;

    <T, K> List<T> query(K splitKey, String sql, Object[] args, RowMapper<T> rowMapper) throws DataAccessException;

    <T, K> List<T> query(K splitKey, String sql, RowMapper<T> rowMapper, Object... args) throws DataAccessException;

    <T, K> T queryForObject(K splitKey, String sql, Object[] args, int[] argTypes, RowMapper<T> rowMapper) throws DataAccessException;

    <T, K> T queryForObject(K splitKey, String sql, Object[] args, RowMapper<T> rowMapper) throws DataAccessException;

    <T, K> T queryForObject(K splitKey, String sql, RowMapper<T> rowMapper, Object... args) throws DataAccessException;

    <T, K> T queryForObject(K splitKey, String sql, Object[] args, int[] argTypes, Class<T> requiredType) throws DataAccessException;

    <T, K> T queryForObject(K splitKey, String sql, Object[] args, Class<T> requiredType) throws DataAccessException;

    <T, K> T queryForObject(K splitKey, String sql, Class<T> requiredType, Object... args) throws DataAccessException;

    <K> Map<String, Object> queryForMap(K splitKey, String sql, Object[] args, int[] argTypes) throws DataAccessException;

    <K> Map<String, Object> queryForMap(K splitKey, String sql, Object... args) throws DataAccessException;

    <T, K> List<T> queryForList(K splitKey, String sql, Object[] args, int[] argTypes, Class<T> elementType) throws DataAccessException;

    <T, K> List<T> queryForList(K splitKey, String sql, Object[] args, Class<T> elementType) throws DataAccessException;

    <T, K> List<T> queryForList(K splitKey, String sql, Class<T> elementType, Object... args) throws DataAccessException;

    <K> List<Map<String, Object>> queryForList(K splitKey, String sql, Object[] args, int[] argTypes) throws DataAccessException;

    <K> List<Map<String, Object>> queryForList(K splitKey, String sql, Object... args) throws DataAccessException;

    <K> SqlRowSet queryForRowSet(K splitKey, String sql, Object[] args, int[] argTypes) throws DataAccessException;

    <K> SqlRowSet queryForRowSet(K splitKey, String sql, Object... args) throws DataAccessException;

    <K> int update(K splitKey, PreparedStatementCreator psc) throws DataAccessException;

    <K> int update(K splitKey, PreparedStatementCreator psc, KeyHolder generatedKeyHolder) throws DataAccessException;

    <K> int update(K splitKey, String sql, PreparedStatementSetter pss) throws DataAccessException;

    <K> int update(K splitKey, String sql, Object[] args, int[] argTypes) throws DataAccessException;

    <K> int update(K splitKey, String sql, Object... args) throws DataAccessException;

    <K> int[] batchUpdate(K splitKey, String sql, BatchPreparedStatementSetter pss) throws DataAccessException;

    <K> int[] batchUpdate(K splitKey, String sql, List<Object[]> batchArgs) throws DataAccessException;

    <K> int[] batchUpdate(K splitKey, String sql, List<Object[]> batchArgs, int[] argTypes) throws DataAccessException;

    <T, K> int[][] batchUpdate(K splitKey, String sql, Collection<T> batchArgs, int batchSize, ParameterizedPreparedStatementSetter<T> pss) throws DataAccessException;

    <T, K> T execute(K splitKey, CallableStatementCreator csc, CallableStatementCallback<T> action) throws DataAccessException;

    <T, K> T execute(K splitKey, String callString, CallableStatementCallback<T> action) throws DataAccessException;

    <K> Map<String, Object> call(K splitKey, CallableStatementCreator csc, List<SqlParameter> declaredParameters) throws DataAccessException;
}
