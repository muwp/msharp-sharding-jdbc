package com.msharp.sharding.jdbc.bean;

import java.io.Serializable;
import java.util.Arrays;

/**
 * SqlBean
 *
 * @author mwup
 * @version 1.0
 * @created 2019/02/15 13:51
 **/
public class SqlBean implements Serializable {

    /**
     * sql
     */
    private String sql;

    /**
     * sql param
     */
    private Object[] params;

    public SqlBean(String sql, Object[] params) {
        this.sql = sql;
        this.params = params;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public Object[] getParams() {
        return params;
    }

    public void setParams(Object[] params) {
        this.params = params;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("SqlBean{");
        sb.append("sql='").append(sql).append('\'');
        sb.append(", params=").append(params == null ? "null" : Arrays.asList(params).toString());
        sb.append('}');
        return sb.toString();
    }
}