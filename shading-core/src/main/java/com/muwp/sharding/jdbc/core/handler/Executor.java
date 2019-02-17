package com.muwp.sharding.jdbc.core.handler;

import com.muwp.sharding.jdbc.core.action.ShardAction;

/**
 * Executor
 *
 * @author mwup
 * @version 1.0
 * @created 2019/02/15 13:51
 **/
@FunctionalInterface
public interface Executor {

    <T, K> T execute(K partitionKey, String sql, ShardAction<T> shardAction);
}