package com.msharp.single.jdbc.jtemplate.parser;

/**
 * ShardSqlParser
 *
 * @author mwup
 * @version 1.0
 * @created 2019/02/15 13:51
 **/
public interface ShardSqlParser {

    /**
     * 解析 sql 方法
     *
     * @param sql
     * @return
     */
    ShardSqlStructure parseShardSql(String sql);
}
