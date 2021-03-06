package com.msharp.sharding.jdbc.sphere.executor;

import com.msharp.sharding.jdbc.sphere.connection.ShardingConnection;
import io.shardingsphere.core.constant.ConnectionMode;
import io.shardingsphere.core.executor.ShardingExecuteGroup;
import io.shardingsphere.core.executor.StatementExecuteUnit;
import io.shardingsphere.core.executor.sql.execute.SQLExecuteCallback;
import io.shardingsphere.core.executor.sql.execute.result.MemoryQueryResult;
import io.shardingsphere.core.executor.sql.execute.result.StreamQueryResult;
import io.shardingsphere.core.executor.sql.execute.threadlocal.ExecutorExceptionHandler;
import io.shardingsphere.core.executor.sql.prepare.SQLExecutePrepareCallback;
import io.shardingsphere.core.merger.QueryResult;
import io.shardingsphere.core.routing.RouteUnit;
import io.shardingsphere.core.routing.SQLRouteResult;
import io.shardingsphere.shardingjdbc.executor.SQLExecuteCallbackFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.List;

/**
 * * Prepared statement executor.
 * <p>
 *
 * @author mwup
 * @version 1.0
 * @created 2019/02/15 13:51
 **/
public final class PreparedStatementExecutor extends AbstractStatementExecutor {

    private final boolean returnGeneratedKeys;

    public PreparedStatementExecutor(final int resultSetType, final int resultSetConcurrency, final int resultSetHoldability, final boolean returnGeneratedKeys, final ShardingConnection shardingConnection) {
        super(resultSetType, resultSetConcurrency, resultSetHoldability, shardingConnection);
        this.returnGeneratedKeys = returnGeneratedKeys;
    }

    /**
     * Initialize executor.
     *
     * @param routeResult route result
     * @throws SQLException SQL exception
     */
    public void init(final SQLRouteResult routeResult) throws SQLException {
        getExecuteGroups().addAll(obtainExecuteGroups(routeResult.getRouteUnits()));
        cacheStatements();
    }

    private Collection<ShardingExecuteGroup<StatementExecuteUnit>> obtainExecuteGroups(final Collection<RouteUnit> routeUnits) throws SQLException {
        return getSqlExecutePrepareTemplate().getExecuteUnitGroups(routeUnits, new SQLExecutePrepareCallback() {

            @Override
            public List<Connection> getConnections(final ConnectionMode connectionMode, final String dataSourceName, final int connectionSize) throws SQLException {
                return PreparedStatementExecutor.super.getConnection().getConnections(connectionMode, dataSourceName, connectionSize);
            }

            @Override
            public StatementExecuteUnit createStatementExecuteUnit(final Connection connection, final RouteUnit routeUnit, final ConnectionMode connectionMode) throws SQLException {
                return new StatementExecuteUnit(routeUnit, createPreparedStatement(connection, routeUnit.getSqlUnit().getSql()), connectionMode);
            }
        });
    }

    private PreparedStatement createPreparedStatement(final Connection connection, final String sql) throws SQLException {
        return returnGeneratedKeys ? connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
                : connection.prepareStatement(sql, getResultSetType(), getResultSetConcurrency(), getResultSetHoldability());
    }

    /**
     * Execute query.
     *
     * @return result set list
     * @throws SQLException SQL exception
     */
    public List<QueryResult> executeQuery() throws SQLException {
        final boolean isExceptionThrown = ExecutorExceptionHandler.isExceptionThrown();
        SQLExecuteCallback<QueryResult> executeCallback = new SQLExecuteCallback<QueryResult>(getDatabaseType(), isExceptionThrown) {

            @Override
            protected QueryResult executeSQL(final StatementExecuteUnit statementExecuteUnit) throws SQLException {
                return getQueryResult(statementExecuteUnit);
            }
        };
        return executeCallback(executeCallback);
    }

    private QueryResult getQueryResult(final StatementExecuteUnit statementExecuteUnit) throws SQLException {
        PreparedStatement preparedStatement = (PreparedStatement) statementExecuteUnit.getStatement();
        ResultSet resultSet = preparedStatement.executeQuery();
        getResultSets().add(resultSet);
        return ConnectionMode.MEMORY_STRICTLY == statementExecuteUnit.getConnectionMode() ? new StreamQueryResult(resultSet) : new MemoryQueryResult(resultSet);
    }

    /**
     * Execute update.
     *
     * @return effected records count
     * @throws SQLException SQL exception
     */
    public int executeUpdate() throws SQLException {
        final boolean isExceptionThrown = ExecutorExceptionHandler.isExceptionThrown();
        SQLExecuteCallback<Integer> executeCallback = SQLExecuteCallbackFactory.getPreparedUpdateSQLExecuteCallback(getDatabaseType(), isExceptionThrown);
        List<Integer> results = executeCallback(executeCallback);
        return accumulate(results);
    }

    private int accumulate(final List<Integer> results) {
        int result = 0;
        for (Integer each : results) {
            result += null == each ? 0 : each;
        }
        return result;
    }

    /**
     * Execute SQL.
     *
     * @return return true if is DQL, false if is DML
     * @throws SQLException SQL exception
     */
    public boolean execute() throws SQLException {
        boolean isExceptionThrown = ExecutorExceptionHandler.isExceptionThrown();
        SQLExecuteCallback<Boolean> executeCallback = SQLExecuteCallbackFactory.getPreparedSQLExecuteCallback(getDatabaseType(), isExceptionThrown);
        List<Boolean> result = executeCallback(executeCallback);
        if (null == result || result.isEmpty() || null == result.get(0)) {
            return false;
        }
        return result.get(0);
    }

    public boolean isReturnGeneratedKeys() {
        return returnGeneratedKeys;
    }
}
