package com.muwp.sharding.jdbc.core.handler;

import com.muwp.sharding.jdbc.core.manager.SplitTablesManager;
import com.muwp.sharding.jdbc.core.manager.SplitTableManager;
import com.muwp.sharding.jdbc.core.manager.SplitJdbcTemplateManager;
import com.muwp.sharding.jdbc.core.action.SplitAction;
import com.muwp.sharding.jdbc.core.strategy.SplitStrategy;
import com.muwp.sharding.jdbc.parser.SplitSqlParser;
import com.muwp.sharding.jdbc.parser.SplitSqlStructure;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

/**
 * SplitExecutor
 *
 * @author mwup
 * @version 1.0
 * @created 2019/02/15 13:51
 **/
public class SplitExecutor implements Executor {

    protected static final Logger log = LoggerFactory.getLogger(SplitExecutor.class);

    protected SplitTablesManager splitTablesManager;

    protected boolean readWriteSeparate = false;

    @Override
    public <T, K> T execute(K splitKey, String sql, SplitAction<T> splitAction) {
        log.debug("execute entry, splitKey {} sql {}", splitKey, sql);

        SplitSqlStructure splitSqlStructure = SplitSqlParser.INST.parseSplitSql(sql);

        String dbName = splitSqlStructure.getDbName();
        String tableName = splitSqlStructure.getTableName();

        SplitTableManager splitTable = splitTablesManager.searchSplitTable(dbName, tableName);

        SplitStrategy splitStrategy = splitTable.getSplitStrategy();

        int nodeNo = splitStrategy.getNodeNo(splitKey);
        int dbNo = splitStrategy.getDbNo(splitKey);
        int tableNo = splitStrategy.getTableNo(splitKey);

        List<SplitJdbcTemplateManager> splitNodes = splitTable.getSplitTemplateManagers();

        SplitJdbcTemplateManager sn = splitNodes.get(nodeNo);
        JdbcTemplate jt = getJdbcTemplate(sn, false);

        sql = splitSqlStructure.getSplitSql(dbNo, tableNo);

        log.debug("execute do action, splitKey {} sql {} dbName {} tableName {} nodeNo {} dbNo {} tableNo {}", splitKey, sql, dbName, tableName, nodeNo, dbNo, tableNo);
        final T result = splitAction.doAction(jt, sql);

        log.debug("execute return, {} are returned, splitKey {} sql {}", result, splitKey, sql);
        return result;
    }

    public JdbcTemplate getJdbcTemplate(SplitJdbcTemplateManager sn, boolean read) {
        if (!read) {
            return sn.getMaster();
        }
        return sn.getMaster();
    }

    public void setReadWriteSeparate(boolean readWriteSeparate) {
        this.readWriteSeparate = readWriteSeparate;
    }

    public void setSplitTablesManager(SplitTablesManager splitTablesManager) {
        this.splitTablesManager = splitTablesManager;
    }
}