package com.msharp.sharding.jdbc.jtemplate.action;

import org.springframework.jdbc.core.JdbcTemplate;

/**
 * ShardAction
 *
 * @author mwup
 * @version 1.0
 * @created 2019/02/15 13:51
 **/
@FunctionalInterface
public interface ShardAction<T> {

    /**
     * 分片处理
     *
     * @param jt  spring jdbc template
     * @param sql sql
     * @return 返回处理结果
     */
    T doAction(JdbcTemplate jt, String sql);
}