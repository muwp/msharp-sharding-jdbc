package com.muwp.sharding.jdbc.parser;

/**
 * ShardSqlParser
 *
 * @author mwup
 * @version 1.0
 * @created 2019/02/15 13:51
 **/
public interface ShardSqlParser {

    ShardSqlParser INST = new ShardSqlParserDefImpl();

    ShardSqlStructure parseShardSql(String sql);
}
