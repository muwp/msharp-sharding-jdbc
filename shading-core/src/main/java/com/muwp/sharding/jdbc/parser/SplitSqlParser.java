package com.muwp.sharding.jdbc.parser;

/**
 * SplitSqlParser
 *
 * @author mwup
 * @version 1.0
 * @created 2019/02/15 13:51
 **/
public interface SplitSqlParser {

    SplitSqlParser INST = new SplitSqlParserDefImpl();

    SplitSqlStructure parseSplitSql(String sql);
}
