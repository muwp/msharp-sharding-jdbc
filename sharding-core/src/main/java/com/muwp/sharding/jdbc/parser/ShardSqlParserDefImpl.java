package com.muwp.sharding.jdbc.parser;

import com.alibaba.druid.sql.parser.Lexer;
import com.alibaba.druid.sql.parser.Token;
import com.muwp.sharding.jdbc.enums.SqlType;
import org.apache.commons.collections.map.LRUMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.util.Map;

/**
 * ShardSqlParserDefImpl
 *
 * @author mwup
 * @version 1.0
 * @created 2019/02/15 13:51
 **/
public class ShardSqlParserDefImpl implements ShardSqlParser {

    private static final Logger log = LoggerFactory.getLogger(ShardSqlParserDefImpl.class);

    private static final int CACHE_SIZE = 1000;

    @SuppressWarnings("unchecked")
    private Map<String, ShardSqlStructure> cache = new LRUMap(CACHE_SIZE);

    public ShardSqlParserDefImpl() {
        log.info("Default ShardSqlParserDefImpl is used.");
    }

    @Override
    public ShardSqlStructure parseShardSql(String sql) {
        ShardSqlStructure splitSqlStructure = cache.get(sql);

        // Don't use if contains then get, race conditon may happens due to LRU
        // map
        if (splitSqlStructure != null) {
            return splitSqlStructure;
        }

        splitSqlStructure = new ShardSqlStructure();

        String dbName = null;
        String tableName = null;
        boolean inProcess = false;

        boolean previous = true;
        boolean sebsequent = false;
        StringBuffer sbPreviousPart = new StringBuffer();
        StringBuffer sbSebsequentPart = new StringBuffer();

        // Need to opertimize for better performance

        Lexer lexer = new Lexer(sql);
        do {
            lexer.nextToken();
            Token tok = lexer.token();
            if (tok == Token.EOF) {
                break;
            }

            if (tok.name != null)
                switch (tok.name) {
                    case "SELECT":
                        splitSqlStructure.setSqlType(SqlType.SELECT);
                        break;

                    case "INSERT":
                        splitSqlStructure.setSqlType(SqlType.INSERT);
                        break;

                    case "UPDATE":
                        inProcess = true;
                        splitSqlStructure.setSqlType(SqlType.UPDATE);
                        break;

                    case "DELETE":
                        splitSqlStructure.setSqlType(SqlType.DELETE);
                        break;

                    case "INTO":
                        if (SqlType.INSERT.equals(splitSqlStructure.getSqlType())) {
                            inProcess = true;
                        }
                        break;

                    case "FROM":
                        if (SqlType.SELECT.equals(splitSqlStructure.getSqlType()) || SqlType.DELETE.equals(splitSqlStructure.getSqlType())) {
                            inProcess = true;
                        }
                        break;
                }

            if (sebsequent) {
                sbSebsequentPart.append(tok == Token.IDENTIFIER ? lexer.stringVal() : tok.name).append(" ");
            }

            if (inProcess) {
                if (dbName == null && tok == Token.IDENTIFIER) {
                    dbName = lexer.stringVal();
                    previous = false;
                } else if (dbName != null && tableName == null && tok == Token.IDENTIFIER) {
                    tableName = lexer.stringVal();

                    inProcess = false;
                    sebsequent = true;
                }
            }

            if (previous) {
                sbPreviousPart.append(tok == Token.IDENTIFIER ? lexer.stringVal() : tok.name).append(" ");
            }

        } while (true);

        if (StringUtils.isEmpty(dbName) || StringUtils.isEmpty(tableName)) {
            throw new UnsupportedOperationException("The split sql is not supported: " + sql);
        }

        splitSqlStructure.setDbName(dbName);
        splitSqlStructure.setTableName(tableName);

        splitSqlStructure.setPreviousPart(sbPreviousPart.toString());
        splitSqlStructure.setSebsequentPart(sbSebsequentPart.toString());

        // if race condition, it is not severe
        if (!cache.containsKey(splitSqlStructure))
            cache.put(sql, splitSqlStructure);

        return splitSqlStructure;
    }
}
