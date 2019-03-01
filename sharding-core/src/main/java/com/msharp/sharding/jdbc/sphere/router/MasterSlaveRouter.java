package com.msharp.sharding.jdbc.sphere.router;

import io.shardingsphere.core.constant.SQLType;
import io.shardingsphere.core.hint.HintManagerHolder;
import io.shardingsphere.core.parsing.SQLJudgeEngine;
import io.shardingsphere.core.routing.router.masterslave.MasterVisitedManager;
import io.shardingsphere.core.rule.MasterSlaveRule;
import io.shardingsphere.core.util.SQLLogger;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * Master slave router interface.
 *
 * @author muweiping
 * @created 2019/02/28 13:51
 */
@RequiredArgsConstructor
public final class MasterSlaveRouter {

    private final MasterSlaveRule masterSlaveRule;

    private final boolean showSQL;

    /**
     * Route Master slave.
     * TODO for multiple masters may return more than one data source
     *
     * @param sql SQL
     * @return data source names
     */
    public Collection<String> route(final String sql) {
        final Collection<String> result = route(new SQLJudgeEngine(sql).judge().getType());
        if (showSQL) {
            SQLLogger.logSQL(sql, result);
        }
        return result;
    }

    private Collection<String> route(final SQLType sqlType) {
        if (isMasterRoute(sqlType)) {
            MasterVisitedManager.setMasterVisited();
            return Collections.singletonList(masterSlaveRule.getMasterDataSourceName());
        }
        return Collections.singletonList(masterSlaveRule.getLoadBalanceAlgorithm().getDataSource(masterSlaveRule.getName(), masterSlaveRule.getMasterDataSourceName(), new ArrayList<>(masterSlaveRule.getSlaveDataSourceNames())));
    }

    private boolean isMasterRoute(final SQLType sqlType) {
        return SQLType.DQL != sqlType || MasterVisitedManager.isMasterVisited() || HintManagerHolder.isMasterRouteOnly();
    }
}