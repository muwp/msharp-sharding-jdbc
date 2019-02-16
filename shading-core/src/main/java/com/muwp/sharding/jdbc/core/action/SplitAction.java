package com.muwp.sharding.jdbc.core.action;

import org.springframework.jdbc.core.JdbcTemplate;

/**
 * SplitAction
 *
 * @author mwup
 * @version 1.0
 * @created 2019/02/15 13:51
 **/
public interface SplitAction<T> {

    T doSplitAction(JdbcTemplate jt, String sql);
}