package com.msharp.sharding.jdbc.jtemplate.handler;

import com.msharp.sharding.jdbc.jtemplate.action.ShardAction;

/**
 * Executor
 *
 * @author mwup
 * @version 1.0
 * @created 2019/02/15 13:51
 **/
@FunctionalInterface
public interface Executor {

    /**
     * 根据分片关键字处理sql
     *
     * @param partitionKey 分片key
     * @param sql          sql语句
     * @param shardAction  分片处理
     * @param <T>          分片关键字类型
     * @param <K>          返回结果类型
     * @return
     */
    <T, K> T execute(K partitionKey, String sql, ShardAction<T> shardAction);
}