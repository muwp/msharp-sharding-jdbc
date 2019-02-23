package com.msharp.sharding.jdbc.sphere.executor;


import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.msharp.sharding.jdbc.sphere.connection.ShardingConnection;
import io.shardingsphere.core.constant.DatabaseType;
import io.shardingsphere.core.constant.properties.ShardingPropertiesConstant;
import io.shardingsphere.core.executor.ShardingExecuteEngine;
import io.shardingsphere.core.executor.ShardingExecuteGroup;
import io.shardingsphere.core.executor.StatementExecuteUnit;
import io.shardingsphere.core.executor.sql.execute.SQLExecuteCallback;
import io.shardingsphere.core.executor.sql.execute.SQLExecuteTemplate;
import io.shardingsphere.core.executor.sql.prepare.SQLExecutePrepareTemplate;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * AbstractStatementExecutor
 * <p>
 *
 * @author mwup
 * @version 1.0
 * @created 2019/02/15 13:51
 **/
public class AbstractStatementExecutor {

    private final DatabaseType databaseType;

    private final int resultSetType;

    private final int resultSetConcurrency;

    private final int resultSetHoldability;

    private final ShardingConnection connection;

    private final SQLExecutePrepareTemplate sqlExecutePrepareTemplate;

    private final SQLExecuteTemplate sqlExecuteTemplate;

    private final Collection<Connection> connections = new LinkedList<>();

    private final List<List<Object>> parameterSets = new LinkedList<>();

    private final List<Statement> statements = new LinkedList<>();

    private final List<ResultSet> resultSets = new CopyOnWriteArrayList<>();

    private final Collection<ShardingExecuteGroup<StatementExecuteUnit>> executeGroups = new LinkedList<>();

    public AbstractStatementExecutor(final int resultSetType, final int resultSetConcurrency, final int resultSetHoldability, final ShardingConnection shardingConnection) {
        this.databaseType = shardingConnection.getShardingContext().getDatabaseType();
        this.resultSetType = resultSetType;
        this.resultSetConcurrency = resultSetConcurrency;
        this.resultSetHoldability = resultSetHoldability;
        this.connection = shardingConnection;
        int maxConnectionsSizePerQuery = connection.getShardingContext().getShardingProperties().<Integer>getValue(ShardingPropertiesConstant.MAX_CONNECTIONS_SIZE_PER_QUERY);
        ShardingExecuteEngine executeEngine = connection.getShardingContext().getExecuteEngine();
        sqlExecutePrepareTemplate = new SQLExecutePrepareTemplate(maxConnectionsSizePerQuery);
        sqlExecuteTemplate = new SQLExecuteTemplate(executeEngine);
    }

    protected final void cacheStatements() {
        for (ShardingExecuteGroup<StatementExecuteUnit> each : executeGroups) {
            statements.addAll(Lists.transform(each.getInputs(), new Function<StatementExecuteUnit, Statement>() {

                @Override
                public Statement apply(final StatementExecuteUnit input) {
                    return input.getStatement();
                }
            }));
            parameterSets.addAll(Lists.transform(each.getInputs(), new Function<StatementExecuteUnit, List<Object>>() {

                @Override
                public List<Object> apply(final StatementExecuteUnit input) {
                    return input.getRouteUnit().getSqlUnit().getParameterSets().get(0);
                }
            }));
        }
    }

    @SuppressWarnings("unchecked")
    protected final <T> List<T> executeCallback(final SQLExecuteCallback<T> executeCallback) throws SQLException {
        return sqlExecuteTemplate.executeGroup((Collection) executeGroups, executeCallback);
    }

    /**
     * Clear data.
     *
     * @throws SQLException sql exception
     */
    public void clear() throws SQLException {
        clearStatements();
        statements.clear();
        parameterSets.clear();
        connections.clear();
        resultSets.clear();
        executeGroups.clear();
    }

    private void clearStatements() throws SQLException {
        for (Statement each : getStatements()) {
            each.close();
        }
    }

    public List<Statement> getStatements() {
        return statements;
    }

    public DatabaseType getDatabaseType() {
        return databaseType;
    }

    public int getResultSetType() {
        return resultSetType;
    }

    public int getResultSetConcurrency() {
        return resultSetConcurrency;
    }

    public int getResultSetHoldability() {
        return resultSetHoldability;
    }

    public ShardingConnection getConnection() {
        return connection;
    }

    public SQLExecutePrepareTemplate getSqlExecutePrepareTemplate() {
        return sqlExecutePrepareTemplate;
    }

    public SQLExecuteTemplate getSqlExecuteTemplate() {
        return sqlExecuteTemplate;
    }

    public Collection<Connection> getConnections() {
        return connections;
    }

    public List<List<Object>> getParameterSets() {
        return parameterSets;
    }

    public List<ResultSet> getResultSets() {
        return resultSets;
    }

    public Collection<ShardingExecuteGroup<StatementExecuteUnit>> getExecuteGroups() {
        return executeGroups;
    }
}