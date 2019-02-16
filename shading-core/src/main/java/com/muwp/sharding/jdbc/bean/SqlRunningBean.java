package com.muwp.sharding.jdbc.bean;

import java.io.Serializable;

/**
 * SqlRunningBean
 *
 * @author mwup
 * @version 1.0
 * @created 2019/02/15 13:51
 **/
public class SqlRunningBean implements Serializable {

    private String sql;

    private Object[] params;

    public SqlRunningBean(String sql, Object[] params) {
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
}