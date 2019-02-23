package com.msharp.sharding.jdbc.jtemplate.jdbc;

import com.msharp.single.jdbc.jtemplate.bean.SqlBean;
import com.msharp.sharding.jdbc.jtemplate.manager.ShardJdbcTemplateManager;
import com.msharp.sharding.jdbc.jtemplate.manager.ShardTableManager;
import com.msharp.sharding.jdbc.jtemplate.strategy.RouterStrategy;
import com.msharp.single.jdbc.jtemplate.enums.SearchOperationType;
import com.msharp.single.jdbc.jtemplate.enums.UpdateOperationType;
import com.msharp.single.jdbc.jtemplate.manager.SqlParserManager;
import com.msharp.single.jdbc.jtemplate.manager.OrmManager;
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
    public <K, T> void insert(K shardKey, T bean) {
        doUpdate(shardKey, bean.getClass(), UpdateOperationType.INSERT, bean, -1);
    }

    @Override
    public <K, T> void update(K shardKey, T bean) {
        doUpdate(shardKey, bean.getClass(), UpdateOperationType.UPDATE, bean, -1);
    }

    @Override
    public <K, T> void delete(K shardKey, long id, Class<T> clazz) {
        doUpdate(shardKey, clazz, UpdateOperationType.DELETE, null, id);
    }

    @Override
    public <K, T> T get(K shardKey, long id, final Class<T> clazz) {
        return doSelect(shardKey, clazz, "id", new Long(id));
    }

    @Override
    public <K, T> T get(K shardKey, String name, String value, final Class<T> clazz) {
        return doSelect(shardKey, clazz, name, value);
    }

    @Override
    public <K, T> List<T> search(K shardKey, T bean) {
        return doSearch(shardKey, bean, null, null, null, SearchOperationType.NORMAL);
    }

    @Override
    public <K, T> List<T> search(K shardKey, T bean, String name, Object valueFrom, Object valueTo) {
        return doSearch(shardKey, bean, name, valueFrom, valueTo, SearchOperationType.RANGE);
    }

    @Override
    public <K, T> List<T> search(K shardKey, T bean, String name, Object value) {
        return doSearch(shardKey, bean, name, value, null, SearchOperationType.FIELD);
    }

    protected <K, T> List<T> doSearch(K shardKey, final T bean, String name, Object valueFrom, Object valueTo, SearchOperationType operationType) {
        if (log.isDebugEnabled()) {
            log.debug("HighLevelShardJdbcTemplate.doSearch, the split key: {}, the bean: {}, the name: {}, the valueFrom: {}, the valueTo: {}.", shardKey, bean, name, valueFrom, valueTo);
        }

        final ShardTableManager shardTableManager = getShardTablesManager().searchSplitTable(OrmManager.getTableName(bean.getClass()));

        final RouterStrategy splitStrategy = shardTableManager.getRouterStrategy();
        final List<ShardJdbcTemplateManager> shardTemplateManagers = shardTableManager.getShardTemplateManagers();

        final String dbPrefix = shardTableManager.getDbNam();
        final String tablePrefix = shardTableManager.getTableName();

        int dbNo = splitStrategy.getDatabasebNo(shardKey);
        int nodeNo = splitStrategy.getNodeNo(shardKey);
        int tableNo = splitStrategy.getTableNo(shardKey);

        log.info("HighLevelShardJdbcTemplate.doSearch, splitKey={} dbPrefix={} tablePrefix={} nodeNo={} dbNo={} tableNo={}.", shardKey, dbPrefix, tablePrefix, nodeNo, dbNo, tableNo);

        ShardJdbcTemplateManager sn = shardTemplateManagers.get(nodeNo);
        JdbcTemplate jt = getReadJdbcTemplate(sn);

        SqlBean srb = null;

        switch (operationType) {
            case NORMAL:
                srb = SqlParserManager.getInstance().generateSearchSql(bean, dbPrefix, tablePrefix, dbNo, tableNo);
                break;
            case RANGE:
                srb = SqlParserManager.getInstance().generateSearchSql(bean, name, valueFrom, valueTo, dbPrefix, tablePrefix, dbNo, tableNo, -1, -1);
                break;
            case FIELD:
                srb = SqlParserManager.getInstance().generateSearchSql(bean, name, valueFrom, dbPrefix, tablePrefix, dbNo, tableNo);
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

        SqlBean srb = SqlParserManager.getInstance().generateSelectSql(name, value, clazz, dbPrefix, tablePrefix, dbNo, tableNo);

        if (log.isDebugEnabled()) {
            log.debug("HighLevelShardJdbcTemplate.doSelect, the split SQL: {}, the split params: {}.", srb.getSql(), srb.getParams());
        }

        final T bean = jt.queryForObject(srb.getSql(), srb.getParams(), (rs, rowNum) -> (T) OrmManager.convertRow2Bean(rs, clazz));

        log.info("HighLevelShardJdbcTemplate.doSelect, select result: {}.", bean);
        return bean;
    }

    protected <K, T> void doUpdate(K shardKey, final Class<?> clazz, UpdateOperationType operationType, T bean, long id) {
        if (log.isDebugEnabled()) {
            log.debug("HighLevelShardJdbcTemplate.doUpdate, the shardKey: {}, the clazz: {}, the updateOper: {}, the split bean: {}, the ID: {}.", shardKey, clazz, operationType, bean, id);
        }
        final ShardTableManager splitTable = getShardTablesManager().searchSplitTable(OrmManager.getTableName(clazz));

        final RouterStrategy routerStrategy = splitTable.getRouterStrategy();
        final List<ShardJdbcTemplateManager> shardTemplateManagers = splitTable.getShardTemplateManagers();

        String dbPrefix = splitTable.getDbNam();
        String tablePrefix = splitTable.getTableName();

        final int nodeNo = routerStrategy.getNodeNo(shardKey);
        final int dbNo = routerStrategy.getDatabasebNo(shardKey);
        final int tableNo = routerStrategy.getTableNo(shardKey);

        log.info("HighLevelShardJdbcTemplate.doUpdate, shardKey={} dbPrefix={} tablePrefix={} nodeNo={} dbNo={} tableNo={}.", shardKey, dbPrefix, tablePrefix, nodeNo, dbNo, tableNo);

        final ShardJdbcTemplateManager sn = shardTemplateManagers.get(nodeNo);
        final JdbcTemplate jt = getWriteJdbcTemplate(sn);

        SqlBean srb = null;
        switch (operationType) {
            case INSERT:
                srb = SqlParserManager.getInstance().generateInsertSql(bean, dbPrefix, tablePrefix, dbNo, tableNo);
                break;
            case UPDATE:
                srb = SqlParserManager.getInstance().generateUpdateSql(bean, dbPrefix, tablePrefix, dbNo, tableNo);
                break;
            case DELETE:
                srb = SqlParserManager.getInstance().generateDeleteSql(id, clazz, dbPrefix, tablePrefix, dbNo, tableNo);
                break;
        }

        if (log.isDebugEnabled()) {
            log.debug("HighLevelShardJdbcTemplate.doUpdate, the split SQL: {}, the split params: {}.", srb.getSql(), srb.getParams());
        }
        long updateCount = jt.update(srb.getSql(), srb.getParams());
        log.info("HighLevelShardJdbcTemplate.doUpdate, update record num: {}.", updateCount);
    }
}
