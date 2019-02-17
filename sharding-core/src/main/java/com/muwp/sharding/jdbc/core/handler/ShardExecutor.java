package com.muwp.sharding.jdbc.core.handler;

import com.muwp.sharding.jdbc.core.manager.ShardJdbcTemplateManager;
import com.muwp.sharding.jdbc.core.manager.ShardTableManager;
import com.muwp.sharding.jdbc.core.manager.ShardTablesManager;
import com.muwp.sharding.jdbc.core.action.ShardAction;
import com.muwp.sharding.jdbc.core.strategy.RouterStrategy;
import com.muwp.sharding.jdbc.parser.ShardSqlParser;
import com.muwp.sharding.jdbc.parser.ShardSqlStructure;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

/**
 * ShardExecutor
 *
 * @author mwup
 * @version 1.0
 * @created 2019/02/15 13:51
 **/
public class ShardExecutor implements Executor {

    protected static final Logger log = LoggerFactory.getLogger(ShardExecutor.class);

    protected ShardTablesManager shardTablesManager;

    protected boolean readWriteSeparate = false;

    public ShardExecutor(ShardTablesManager shardTablesManager, boolean readWriteSeparate) {
        this.shardTablesManager = shardTablesManager;
        this.readWriteSeparate = readWriteSeparate;
    }

    @Override
    public <T, K> T execute(K partitionKey, String sql, ShardAction<T> shardAction) {
        log.debug("execute entry, partitionKey {} sql {}", partitionKey, sql);

        final ShardSqlStructure shardSqlStructure = ShardSqlParser.INST.parseShardSql(sql);

        String dbName = shardSqlStructure.getDbName();
        String tableName = shardSqlStructure.getTableName();

        ShardTableManager shardTableManager = shardTablesManager.searchShardTableManager(dbName, tableName);

        RouterStrategy routerStrategy = shardTableManager.getRouterStrategy();

        int nodeNo = routerStrategy.getNodeNo(partitionKey);
        int dbNo = routerStrategy.getDbNo(partitionKey);
        int tableNo = routerStrategy.getTableNo(partitionKey);

        List<ShardJdbcTemplateManager> shardTemplateManagers = shardTableManager.getShardTemplateManagers();

        ShardJdbcTemplateManager sn = shardTemplateManagers.get(nodeNo);
        JdbcTemplate jt = getJdbcTemplate(sn, false);

        sql = shardSqlStructure.getShardSql(dbNo, tableNo);

        log.debug("execute do action, partitionKey {} sql {} dbName {} tableName {} nodeNo {} dbNo {} tableNo {}", partitionKey, sql, dbName, tableName, nodeNo, dbNo, tableNo);
        final T result = shardAction.doAction(jt, sql);

        log.debug("execute return, {} are returned, partitionKey {} sql {}", result, partitionKey, sql);
        return result;
    }

    public JdbcTemplate getJdbcTemplate(ShardJdbcTemplateManager sn, boolean read) {
        if (!read) {
            return sn.getMaster();
        }
        return sn.getMaster();
    }

    public void setReadWriteSeparate(boolean readWriteSeparate) {
        this.readWriteSeparate = readWriteSeparate;
    }

    public void setShardTablesManager(ShardTablesManager shardTablesManager) {
        this.shardTablesManager = shardTablesManager;
    }
}