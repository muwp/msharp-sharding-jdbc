package com.muwp.sharding.jdbc.core.action;

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

    T doAction(JdbcTemplate jt, String sql);
}