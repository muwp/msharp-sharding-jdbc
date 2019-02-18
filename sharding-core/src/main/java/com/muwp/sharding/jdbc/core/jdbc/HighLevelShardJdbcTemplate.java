package com.muwp.sharding.jdbc.core.jdbc;

import com.muwp.sharding.jdbc.bean.SqlBean;
import com.muwp.sharding.jdbc.core.manager.ShardJdbcTemplateManager;
import com.muwp.sharding.jdbc.core.manager.ShardTableManager;
import com.muwp.sharding.jdbc.core.strategy.RouterStrategy;
import com.muwp.sharding.jdbc.enums.SearchOperationType;
import com.muwp.sharding.jdbc.enums.UpdateOperationType;
import com.muwp.sharding.jdbc.manager.SqlParserManager;
import com.muwp.sharding.jdbc.manager.OrmManager;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

/**
 * HighLevelShardJdbcTemplate
 *
 * @author mwup
 * @version 1.0
 * @created 2019/02/15 13:51
 **/
public class HighLevelShardJdbcTemplate extends ShardJdbcTemplate implements HighLevelShardJdbcOperations {

    public HighLevelShardJdbcTemplate() {
    }

    public HighLevelShardJdbcTemplate(List<String> ipPorts, String user, String password, String... tables) {
        super(ipPorts, user, password, tables);
    }

    @Override
    public <K, T> void insert(K splitKey, T bean) {
        doUpdate(splitKey, bean.getClass(), UpdateOperationType.INSERT, bean, -1);
    }

    @Override
    public <K, T> void update(K splitKey, T bean) {
        doUpdate(splitKey, bean.getClass(), UpdateOperationType.UPDATE, bean, -1);
    }

    @Override
    public <K, T> void delete(K splitKey, long id, Class<T> clazz) {
        doUpdate(splitKey, clazz, UpdateOperationType.DELETE, null, id);
    }

    @Override
    public <K, T> T get(K splitKey, long id, final Class<T> clazz) {
        return doSelect(splitKey, clazz, "id", new Long(id));
    }

    @Override
    public <K, T> T get(K splitKey, String name, String value, final Class<T> clazz) {
        return doSelect(splitKey, clazz, name, value);
    }

    @Override
    public <K, T> List<T> search(K splitKey, T bean) {
        return doSearch(splitKey, bean, null, null, null, SearchOperationType.NORMAL);
    }

    @Override
    public <K, T> List<T> search(K splitKey, T bean, String name, Object valueFrom, Object valueTo) {
        return doSearch(splitKey, bean, name, valueFrom, valueTo, SearchOperationType.RANGE);
    }

    @Override
    public <K, T> List<T> search(K splitKey, T bean, String name, Object value) {
        return doSearch(splitKey, bean, name, value, null, SearchOperationType.FIELD);
    }

    protected <K, T> List<T> doSearch(K splitKey, final T bean, String name, Object valueFrom, Object valueTo, SearchOperationType operationType) {
        if (log.isDebugEnabled()) {
            log.debug("HighLevelShardJdbcTemplate.doSearch, the split key: {}, the bean: {}, the name: {}, the valueFrom: {}, the valueTo: {}.", splitKey, bean, name, valueFrom, valueTo);
        }

        final ShardTableManager shardTableManager = getShardTablesManager().searchSplitTable(OrmManager.getTableName(bean.getClass()));

        final RouterStrategy splitStrategy = shardTableManager.getRouterStrategy();
        final List<ShardJdbcTemplateManager> shardTemplateManagers = shardTableManager.getShardTemplateManagers();

        final String dbPrefix = shardTableManager.getDbNam();
        final String tablePrefix = shardTableManager.getTableName();

        int dbNo = splitStrategy.getDatabasebNo(splitKey);
        int nodeNo = splitStrategy.getNodeNo(splitKey);
        int tableNo = splitStrategy.getTableNo(splitKey);

        log.info("HighLevelShardJdbcTemplate.doSearch, splitKey={} dbPrefix={} tablePrefix={} nodeNo={} dbNo={} tableNo={}.", splitKey, dbPrefix, tablePrefix, nodeNo, dbNo, tableNo);

        ShardJdbcTemplateManager sn = shardTemplateManagers.get(nodeNo);
        JdbcTemplate jt = getReadJdbcTemplate(sn);

        SqlBean srb = null;

        switch (operationType) {
            case NORMAL:
                srb = SqlParserManager.generateSearchSql(bean, dbPrefix, tablePrefix, dbNo, tableNo);
                break;
            case RANGE:
                srb = SqlParserManager.generateSearchSql(bean, name, valueFrom, valueTo, dbPrefix, tablePrefix, dbNo, tableNo, -1, -1);
                break;
            case FIELD:
                srb = SqlParserManager.generateSearchSql(bean, name, valueFrom, dbPrefix, tablePrefix, dbNo, tableNo);
                break;
        }

        if (log.isDebugEnabled()) {
            log.debug("HighLevelShardJdbcTemplate.doSearch, the split SQL: {}, the split params: {}.", srb.getSql(), srb.getParams());
        }
        final List<T> beans = jt.query(srb.getSql(), srb.getParams(), (rs, rowNum) -> (T) OrmManager.convertRow2Bean(rs, bean.getClass()));

        log.info("HighLevelShardJdbcTemplate.doSearch, search result: {}.", beans);
        return beans;
    }

    protected <K, T> T doSelect(K splitKey, final Class<T> clazz, String name, Object value) {
        if (log.isDebugEnabled()) {
            log.debug("HighLevelShardJdbcTemplate.doSelect, the split key: {}, the clazz: {}, where {} = {}.", splitKey, clazz, name, value);
        }

        ShardTableManager splitTable = getShardTablesManager().searchSplitTable(OrmManager.getTableName(clazz));

        RouterStrategy routerStrategy = splitTable.getRouterStrategy();
        List<ShardJdbcTemplateManager> splitNdoes = splitTable.getShardTemplateManagers();

        String tablePrefix = splitTable.getTableName();
        String dbPrefix = splitTable.getDbNam();

        int nodeNo = routerStrategy.getNodeNo(splitKey);
        int dbNo = routerStrategy.getDatabasebNo(splitKey);
        int tableNo = routerStrategy.getTableNo(splitKey);

        log.info("HighLevelShardJdbcTemplate.doSelect, splitKey={} dbPrefix={} tablePrefix={} nodeNo={} dbNo={} tableNo={}.", splitKey, dbPrefix, tablePrefix, nodeNo, dbNo, tableNo);

        ShardJdbcTemplateManager sn = splitNdoes.get(nodeNo);
        JdbcTemplate jt = getReadJdbcTemplate(sn);

        SqlBean srb = SqlParserManager.generateSelectSql(name, value, clazz, dbPrefix, tablePrefix, dbNo, tableNo);

        if (log.isDebugEnabled()) {
            log.debug("HighLevelShardJdbcTemplate.doSelect, the split SQL: {}, the split params: {}.", srb.getSql(), srb.getParams());
        }

        final T bean = jt.queryForObject(srb.getSql(), srb.getParams(), (rs, rowNum) -> (T) OrmManager.convertRow2Bean(rs, clazz));

        log.info("HighLevelShardJdbcTemplate.doSelect, select result: {}.", bean);
        return bean;
    }

    protected <K, T> void doUpdate(K splitKey, final Class<?> clazz, UpdateOperationType operationType, T bean, long id) {
        if (log.isDebugEnabled()) {
            log.debug("HighLevelShardJdbcTemplate.doUpdate, the split key: {}, the clazz: {}, the updateOper: {}, the split bean: {}, the ID: {}.", splitKey, clazz, operationType, bean, id);
        }
        final ShardTableManager splitTable = getShardTablesManager().searchSplitTable(OrmManager.getTableName(clazz));

        final RouterStrategy routerStrategy = splitTable.getRouterStrategy();
        final List<ShardJdbcTemplateManager> shardTemplateManagers = splitTable.getShardTemplateManagers();

        String dbPrefix = splitTable.getDbNam();
        String tablePrefix = splitTable.getTableName();

        final int nodeNo = routerStrategy.getNodeNo(splitKey);
        final int dbNo = routerStrategy.getDatabasebNo(splitKey);
        final int tableNo = routerStrategy.getTableNo(splitKey);

        log.info("HighLevelShardJdbcTemplate.doUpdate, splitKey={} dbPrefix={} tablePrefix={} nodeNo={} dbNo={} tableNo={}.", splitKey, dbPrefix, tablePrefix, nodeNo, dbNo, tableNo);

        final ShardJdbcTemplateManager sn = shardTemplateManagers.get(nodeNo);
        final JdbcTemplate jt = getWriteJdbcTemplate(sn);

        SqlBean srb = null;
        switch (operationType) {
            case INSERT:
                srb = SqlParserManager.generateInsertSql(bean, dbPrefix, tablePrefix, dbNo, tableNo);
                break;
            case UPDATE:
                srb = SqlParserManager.generateUpdateSql(bean, dbPrefix, tablePrefix, dbNo, tableNo);
                break;
            case DELETE:
                srb = SqlParserManager.generateDeleteSql(id, clazz, dbPrefix, tablePrefix, dbNo, tableNo);
                break;
        }

        if (log.isDebugEnabled()) {
            log.debug("HighLevelShardJdbcTemplate.doUpdate, the split SQL: {}, the split params: {}.", srb.getSql(), srb.getParams());
        }
        long updateCount = jt.update(srb.getSql(), srb.getParams());
        log.info("HighLevelShardJdbcTemplate.doUpdate, update record num: {}.", updateCount);
    }
}
