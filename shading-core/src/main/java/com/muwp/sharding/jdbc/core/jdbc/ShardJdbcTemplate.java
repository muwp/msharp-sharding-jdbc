package com.muwp.sharding.jdbc.core.jdbc;

import com.muwp.sharding.jdbc.core.handler.Executor;
import com.muwp.sharding.jdbc.core.handler.ShardExecutor;
import com.muwp.sharding.jdbc.core.manager.ShardJdbcTemplateManager;
import com.muwp.sharding.jdbc.core.manager.ShardTablesManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.*;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * ShardJdbcTemplate
 *
 * @author mwup
 * @version 1.0
 * @created 2019/02/15 13:51
 **/
public class ShardJdbcTemplate implements ShardJdbcOperations {

    protected static final Logger log = LoggerFactory.getLogger(ShardJdbcTemplate.class);

    private ShardTablesManager shardTablesManager;

    private Executor executor;

    protected boolean readWriteSeparate = false;

    public ShardJdbcTemplate() {

    }

    public ShardJdbcTemplate(List<String> ipPorts, String user, String password, String... tables) {
        this.addTable(ipPorts, user, password, tables);
    }

    public void addTable(List<String> ipPorts, String user, String password, String... tables) {
        // TODO parse datasources and tables
    }

    @Override
    public <K> void execute(K splitKey, String sql) throws DataAccessException {
        this.executor.execute(splitKey, sql, (jt, SQL) -> {
            jt.execute(SQL);
            return null;
        });

    }

    @Override
    public <T, K> T query(K splitKey, String sql, ResultSetExtractor<T> rse) throws DataAccessException {
        return this.executor.execute(splitKey, sql, (jt, SQL) -> jt.query(SQL, rse));
    }

    @Override
    public <K> void query(K splitKey, String sql, RowCallbackHandler rch) throws DataAccessException {
        this.executor.execute(splitKey, sql, (jt, SQL) -> {
            jt.query(SQL, rch);
            return null;
        });
    }

    @Override
    public <T, K> List<T> query(K splitKey, String sql, RowMapper<T> rowMapper) throws DataAccessException {
        return this.executor.execute(splitKey, sql, (jt, SQL) -> jt.query(SQL, rowMapper));
    }

    @Override
    public <T, K> T queryForObject(K splitKey, String sql, RowMapper<T> rowMapper) throws DataAccessException {
        return this.executor.execute(splitKey, sql, (jt, SQL) -> jt.queryForObject(SQL, rowMapper));
    }

    @Override
    public <T, K> T queryForObject(K splitKey, String sql, Class<T> requiredType) throws DataAccessException {
        return this.executor.execute(splitKey, sql, (jt, SQL) -> jt.queryForObject(SQL, requiredType));
    }

    @Override
    public <K> Map<String, Object> queryForMap(K splitKey, String sql) throws DataAccessException {
        return this.executor.execute(splitKey, sql, (jt, SQL) -> jt.queryForMap(SQL));
    }

    @Override
    public <T, K> List<T> queryForList(K splitKey, String sql, Class<T> elementType) throws DataAccessException {
        return this.executor.execute(splitKey, sql, (jdbcTemplate, SQL) -> jdbcTemplate.queryForList(SQL, elementType));
    }

    @Override
    public <K> List<Map<String, Object>> queryForList(K splitKey, String sql) throws DataAccessException {
        return this.executor.execute(splitKey, sql, (jdbcTemplate, SQL) -> jdbcTemplate.queryForList(SQL));
    }

    @Override
    public <K> SqlRowSet queryForRowSet(K splitKey, String sql) throws DataAccessException {
        return this.executor.execute(splitKey, sql, (jt, SQL) -> jt.queryForRowSet(SQL));
    }

    @Override
    public <K> int update(K splitKey, String sql) throws DataAccessException {
        return this.executor.execute(splitKey, sql, (jt, SQL) -> jt.update(SQL));
    }

    @Override
    public <K> int[] batchUpdate(K splitKey, String... sql) throws DataAccessException {
        throw new UnsupportedOperationException();
    }

    @Override
    public <T, K> T execute(K splitKey, String sql, PreparedStatementCallback<T> action) throws DataAccessException {
        return this.executor.execute(splitKey, sql, (jt, SQL) -> jt.execute(SQL, action));
    }

    @Override
    public <T, K> T query(K splitKey, String sql, PreparedStatementSetter pss, ResultSetExtractor<T> rse) throws DataAccessException {
        return this.executor.execute(splitKey, sql, (jt, SQL) -> jt.query(SQL, pss, rse));
    }

    @Override
    public <T, K> T query(K splitKey, String sql, Object[] args, int[] argTypes, ResultSetExtractor<T> rse) throws DataAccessException {
        return this.executor.execute(splitKey, sql, (jt, SQL) -> jt.query(SQL, args, argTypes, rse));
    }

    @Override
    public <T, K> T query(K splitKey, String sql, Object[] args, ResultSetExtractor<T> rse) throws DataAccessException {
        return this.executor.execute(splitKey, sql, (jt, SQL) -> jt.query(SQL, args, rse));
    }

    @Override
    public <T, K> T query(K splitKey, String sql, ResultSetExtractor<T> rse, Object... args) throws DataAccessException {
        return this.executor.execute(splitKey, sql, (jt, SQL) -> jt.query(SQL, rse, args));
    }

    @Override
    public <K> void query(K splitKey, String sql, PreparedStatementSetter pss, RowCallbackHandler rch) throws DataAccessException {
        this.executor.execute(splitKey, sql, (jt, SQL) -> {
            jt.query(SQL, pss, rch);
            return null;
        });
    }

    @Override
    public <K> void query(K splitKey, String sql, Object[] args, int[] argTypes, RowCallbackHandler rch) throws DataAccessException {
        this.executor.execute(splitKey, sql, (jt, SQL) -> {
            jt.query(SQL, args, argTypes, rch);
            return null;
        });
    }

    @Override
    public <K> void query(K splitKey, String sql, Object[] args, RowCallbackHandler rch) throws DataAccessException {
        this.executor.execute(splitKey, sql, (jt, SQL) -> {
            jt.query(SQL, args, rch);
            return null;
        });
    }

    @Override
    public <K> void query(K splitKey, String sql, RowCallbackHandler rch, Object... args) throws DataAccessException {
        this.executor.execute(splitKey, sql, (jt, SQL) -> {
            jt.query(SQL, rch, args);
            return null;
        });
    }

    @Override
    public <T, K> List<T> query(K splitKey, String sql, PreparedStatementSetter pss, RowMapper<T> rowMapper) throws DataAccessException {
        return this.executor.execute(splitKey, sql, (jt, SQL) -> jt.query(SQL, pss, rowMapper));
    }

    @Override
    public <T, K> List<T> query(K splitKey, String sql, Object[] args, int[] argTypes, RowMapper<T> rowMapper) throws DataAccessException {
        return this.executor.execute(splitKey, sql, (jt, SQL) -> jt.query(SQL, args, argTypes, rowMapper));
    }

    @Override
    public <T, K> List<T> query(K splitKey, String sql, Object[] args, RowMapper<T> rowMapper) throws DataAccessException {
        return this.executor.execute(splitKey, sql, (jt, SQL) -> jt.query(SQL, args, rowMapper));
    }

    @Override
    public <T, K> List<T> query(K splitKey, String sql, RowMapper<T> rowMapper, Object... args) throws DataAccessException {
        return this.executor.execute(splitKey, sql, (jt, SQL) -> jt.query(SQL, rowMapper, args));
    }

    @Override
    public <T, K> T queryForObject(K splitKey, String sql, Object[] args, int[] argTypes, RowMapper<T> rowMapper) throws DataAccessException {
        return this.executor.execute(splitKey, sql, (jt, SQL) -> jt.queryForObject(SQL, args, argTypes, rowMapper));
    }

    @Override
    public <T, K> T queryForObject(K splitKey, String sql, Object[] args, RowMapper<T> rowMapper) throws DataAccessException {
        return this.executor.execute(splitKey, sql, (jt, SQL) -> jt.queryForObject(SQL, args, rowMapper));
    }

    @Override
    public <T, K> T queryForObject(K splitKey, String sql, RowMapper<T> rowMapper, Object... args) throws DataAccessException {
        return this.executor.execute(splitKey, sql, (jt, SQL) -> jt.queryForObject(SQL, rowMapper, args));
    }

    @Override
    public <T, K> T queryForObject(K splitKey, String sql, Object[] args, int[] argTypes, Class<T> requiredType) throws DataAccessException {
        return this.executor.execute(splitKey, sql, (jt, SQL) -> jt.queryForObject(SQL, args, argTypes, requiredType));
    }

    @Override
    public <T, K> T queryForObject(K splitKey, String sql, Object[] args, Class<T> requiredType) throws DataAccessException {
        return this.executor.execute(splitKey, sql, (jt, SQL) -> jt.queryForObject(SQL, args, requiredType));
    }

    @Override
    public <T, K> T queryForObject(K splitKey, String sql, Class<T> requiredType, Object... args) throws DataAccessException {
        return this.executor.execute(splitKey, sql, (jt, SQL) -> jt.queryForObject(SQL, requiredType, args));
    }

    @Override
    public <K> Map<String, Object> queryForMap(K splitKey, String sql, Object[] args, int[] argTypes) throws DataAccessException {
        return this.executor.execute(splitKey, sql, (jt, SQL) -> jt.queryForMap(SQL, args, argTypes));
    }

    @Override
    public <K> Map<String, Object> queryForMap(K splitKey, String sql, Object... args) throws DataAccessException {
        return this.executor.execute(splitKey, sql, (jt, SQL) -> jt.queryForMap(SQL, args));
    }

    @Override
    public <T, K> List<T> queryForList(K splitKey, String sql, Object[] args, int[] argTypes, Class<T> elementType) throws DataAccessException {
        return this.executor.execute(splitKey, sql, (jt, SQL) -> jt.queryForList(SQL, args, argTypes, elementType));
    }

    @Override
    public <T, K> List<T> queryForList(K splitKey, String sql, Object[] args, Class<T> elementType) throws DataAccessException {
        return this.executor.execute(splitKey, sql, (jt, SQL) -> jt.queryForList(SQL, args, elementType));
    }

    @Override
    public <T, K> List<T> queryForList(K splitKey, String sql, Class<T> elementType, Object... args) throws DataAccessException {
        return this.executor.execute(splitKey, sql, (jdbcTemplate, SQL) -> jdbcTemplate.queryForList(SQL, elementType, args));
    }

    @Override
    public <K> List<Map<String, Object>> queryForList(K splitKey, String sql, Object[] args, int[] argTypes) throws DataAccessException {
        return this.executor.execute(splitKey, sql, (jdbcTemplate, SQL) -> jdbcTemplate.queryForList(SQL, args, argTypes));
    }

    @Override
    public <K> List<Map<String, Object>> queryForList(K splitKey, String sql, Object... args) throws DataAccessException {
        return this.executor.execute(splitKey, sql, (jdbcTemplate, SQL) -> jdbcTemplate.queryForList(SQL, args));
    }

    @Override
    public <K> SqlRowSet queryForRowSet(K splitKey, String sql, Object[] args, int[] argTypes) throws DataAccessException {
        return this.executor.execute(splitKey, sql, (jdbcTemplate, SQL) -> jdbcTemplate.queryForRowSet(SQL, args, argTypes));
    }

    @Override
    public <K> SqlRowSet queryForRowSet(K splitKey, String sql, Object... args) throws DataAccessException {
        return this.executor.execute(splitKey, sql, (jt, SQL) -> jt.queryForRowSet(SQL, args));
    }

    @Override
    public <K> int update(K splitKey, String sql, PreparedStatementSetter pss) throws DataAccessException {
        return this.executor.execute(splitKey, sql, (jt, SQL) -> jt.update(SQL, pss));
    }

    @Override
    public <K> int update(K splitKey, String sql, Object[] args, int[] argTypes) throws DataAccessException {
        return this.executor.execute(splitKey, sql, (jt, SQL) -> jt.update(SQL, args, argTypes));
    }

    @Override
    public <K> int update(K splitKey, String sql, Object... args) throws DataAccessException {
        return this.executor.execute(splitKey, sql, (jt, SQL) -> jt.update(SQL, args));
    }

    @Override
    public <K> int[] batchUpdate(K splitKey, String sql, BatchPreparedStatementSetter pss) throws DataAccessException {
        throw new UnsupportedOperationException();
    }

    @Override
    public <K> int[] batchUpdate(K splitKey, String sql, List<Object[]> batchArgs) throws DataAccessException {
        throw new UnsupportedOperationException();
    }

    @Override
    public <K> int[] batchUpdate(K splitKey, String sql, List<Object[]> batchArgs, int[] argTypes) throws DataAccessException {
        throw new UnsupportedOperationException();
    }

    @Override
    public <T, K> int[][] batchUpdate(K splitKey, String sql, Collection<T> batchArgs, int batchSize, ParameterizedPreparedStatementSetter<T> pss) throws DataAccessException {
        throw new UnsupportedOperationException();
    }

    public boolean isReadWriteSeparate() {
        return readWriteSeparate;
    }

    public void setReadWriteSeparate(boolean readWriteSeparate) {
        this.readWriteSeparate = readWriteSeparate;
    }

    protected JdbcTemplate getWriteJdbcTemplate(ShardJdbcTemplateManager sn) {
        return getJdbcTemplate(sn, false);
    }

    protected JdbcTemplate getReadJdbcTemplate(ShardJdbcTemplateManager sn) {
        return getJdbcTemplate(sn, true);
    }

    public JdbcTemplate getJdbcTemplate(ShardJdbcTemplateManager sn, boolean read) {
        if (!read) {
            return sn.getMaster();
        }

        if (readWriteSeparate) {
            return sn.getRoundRobinSlaveTemplate();
        }

        return sn.getMaster();
    }

    public void setShardTablesManager(ShardTablesManager shardTablesManager) {
        this.shardTablesManager = shardTablesManager;
        this.executor = new ShardExecutor(this.shardTablesManager, this.readWriteSeparate);
    }

    public ShardTablesManager getShardTablesManager() {
        return this.shardTablesManager;
    }
}
