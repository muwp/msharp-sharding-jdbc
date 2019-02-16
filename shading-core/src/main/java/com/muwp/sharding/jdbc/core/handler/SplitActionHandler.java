package com.muwp.sharding.jdbc.core.handler;

import com.muwp.sharding.jdbc.core.SplitNode;
import com.muwp.sharding.jdbc.core.SplitTable;
import com.muwp.sharding.jdbc.core.SplitTablesHolder;
import com.muwp.sharding.jdbc.core.action.SplitAction;
import com.muwp.sharding.jdbc.core.strategy.SplitStrategy;
import com.muwp.sharding.jdbc.parser.SplitSqlParser;
import com.muwp.sharding.jdbc.parser.SplitSqlStructure;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

/**
 * SplitActionHandler
 *
 * @author mwup
 * @version 1.0
 * @created 2019/02/15 13:51
 **/
public class SplitActionHandler implements ActionHandler {

    protected static final Logger log = LoggerFactory.getLogger(SplitActionHandler.class);

    protected SplitTablesHolder splitTablesHolder;

    protected boolean readWriteSeparate = false;

    @Override
    public <T, K> T execute(K splitKey, String sql, SplitAction<T> splitAction) {
        log.debug("execute entry, splitKey {} sql {}", splitKey, sql);

        SplitSqlStructure splitSqlStructure = SplitSqlParser.INST.parseSplitSql(sql);

        String dbName = splitSqlStructure.getDbName();
        String tableName = splitSqlStructure.getTableName();

        SplitTable splitTable = splitTablesHolder.searchSplitTable(dbName, tableName);

        SplitStrategy splitStrategy = splitTable.getSplitStrategy();

        int nodeNo = splitStrategy.getNodeNo(splitKey);
        int dbNo = splitStrategy.getDbNo(splitKey);
        int tableNo = splitStrategy.getTableNo(splitKey);

        List<SplitNode> splitNodes = splitTable.getSplitNodes();

        SplitNode sn = splitNodes.get(nodeNo);
        JdbcTemplate jt = getJdbcTemplate(sn, false);

        sql = splitSqlStructure.getSplitSql(dbNo, tableNo);

        log.debug("execute do action, splitKey {} sql {} dbName {} tableName {} nodeNo {} dbNo {} tableNo {}", splitKey, sql, dbName, tableName, nodeNo, dbNo, tableNo);
        T result = splitAction.doSplitAction(jt, sql);

        log.debug("execute return, {} are returned, splitKey {} sql {}", result, splitKey, sql);
        return result;
    }

    public JdbcTemplate getJdbcTemplate(SplitNode sn, boolean read) {
        if (!read) {
            return sn.getMasterTemplate();
        }
        return sn.getMasterTemplate();
    }

    public void setReadWriteSeparate(boolean readWriteSeparate) {
        this.readWriteSeparate = readWriteSeparate;
    }

    public void setSplitTablesHolder(SplitTablesHolder splitTablesHolder) {
        this.splitTablesHolder = splitTablesHolder;
    }
}