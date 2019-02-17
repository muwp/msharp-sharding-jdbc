package com.muwp.sharding.jdbc.parser;

import com.muwp.sharding.jdbc.enums.SqlType;
import com.mysql.jdbc.StringUtils;

/**
 * ShardSqlStructure
 *
 * @author mwup
 * @version 1.0
 * @created 2019/02/15 13:51
 **/
public class ShardSqlStructure {

    private SqlType sqlType;

    private String dbName;

    private String tableName;

    private String previousPart;

    private String sebSequentPart;

    public SqlType getSqlType() {
        return sqlType;
    }

    public void setSqlType(SqlType sqlType) {
        this.sqlType = sqlType;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getPreviousPart() {
        return previousPart;
    }

    public void setPreviousPart(String previousPart) {
        this.previousPart = previousPart;
    }

    public String getSebSequentPart() {
        return sebSequentPart;
    }

    public void setSebSequentPart(String sebSequentPart) {
        this.sebSequentPart = sebSequentPart;
    }

    public String getShardSql(int dbNo, int tableNo) {
        if (sqlType == null || StringUtils.isEmptyOrWhitespaceOnly(dbName) || StringUtils.isEmptyOrWhitespaceOnly(tableName) || StringUtils.isEmptyOrWhitespaceOnly(previousPart)) {
            throw new IllegalStateException("The split SQL should be constructed after the SQL is parsed completely.");
        }

        StringBuffer sb = new StringBuffer();
        sb.append(previousPart).append(" ");
        sb.append(dbName).append("_").append(dbNo);
        sb.append(".");
        sb.append(tableName).append("_").append(tableNo).append(" ");
        if (!StringUtils.isEmptyOrWhitespaceOnly(sebSequentPart)) {
            sb.append(sebSequentPart);
        }

        return sb.toString();
    }
}
