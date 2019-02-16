package com.muwp.sharding.jdbc.core.handler;

import com.muwp.sharding.jdbc.core.action.SplitAction;

/**
 * Executor
 *
 * @author mwup
 * @version 1.0
 * @created 2019/02/15 13:51
 **/
@FunctionalInterface
public interface Executor {

    <T, K> T execute(K splitKey, String sql, SplitAction<T> splitAction);
}