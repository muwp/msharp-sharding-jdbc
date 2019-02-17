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
 * ShardSqlParserImpl
 *
 * @author mwup
 * @version 1.0
 * @created 2019/02/15 13:51
 **/
public class ShardSqlParserImpl implements ShardSqlParser {

    private static final Logger log = LoggerFactory.getLogger(ShardSqlParserImpl.class);

    private static final int CACHE_SIZE = 5000;

    private static ShardSqlParser INSTANCE = new ShardSqlParserImpl();

    private Map<String, ShardSqlStructure> cache = new LRUMap(CACHE_SIZE);

    public static ShardSqlParser getInstance() {
        return INSTANCE;
    }

    @Override
    public ShardSqlStructure parseShardSql(final String sql) {
        ShardSqlStructure shardSqlStructure = cache.get(sql);
        if (null == shardSqlStructure) {
            shardSqlStructure = load(sql);
        }
        return shardSqlStructure;
    }

    private synchronized ShardSqlStructure load(final String sql) {
        ShardSqlStructure shardSqlStructure = cache.get(sql);

        // Don't use if contains then get, race conditon may happens due to LRU
        // map
        if (shardSqlStructure != null) {
            return shardSqlStructure;
        }

        shardSqlStructure = new ShardSqlStructure();

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

            if (tok.name != null) {
                switch (tok.name) {
                    case "SELECT":
                        shardSqlStructure.setSqlType(SqlType.SELECT);
                        break;

                    case "INSERT":
                        shardSqlStructure.setSqlType(SqlType.INSERT);
                        break;

                    case "UPDATE":
                        inProcess = true;
                        shardSqlStructure.setSqlType(SqlType.UPDATE);
                        break;

                    case "DELETE":
                        shardSqlStructure.setSqlType(SqlType.DELETE);
                        break;

                    case "INTO":
                        if (SqlType.INSERT.equals(shardSqlStructure.getSqlType())) {
                            inProcess = true;
                        }
                        break;

                    case "FROM":
                        if (SqlType.SELECT.equals(shardSqlStructure.getSqlType()) || SqlType.DELETE.equals(shardSqlStructure.getSqlType())) {
                            inProcess = true;
                        }
                        break;
                }
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

        shardSqlStructure.setDbName(dbName);
        shardSqlStructure.setTableName(tableName);

        shardSqlStructure.setPreviousPart(sbPreviousPart.toString());
        shardSqlStructure.setSebSequentPart(sbSebsequentPart.toString());

        // if race condition, it is not severe
        if (!cache.containsKey(shardSqlStructure)) {
            cache.put(sql, shardSqlStructure);
        }

        return shardSqlStructure;
    }
}
