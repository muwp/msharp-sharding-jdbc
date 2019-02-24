package com.msharp.single.jdbc.datasource.pool;

import com.msharp.single.jdbc.constants.Constants;
import com.msharp.single.jdbc.datasource.config.DataSourceConfig;
import com.msharp.single.jdbc.util.JdbcDriverClassHelper;

/**
 * DataSourcePoolFactory
 *
 * @author mwup
 * @version 1.0
 * @created 2018/10/29 13:51
 **/
public class DataSourcePoolFactory {

    public static DataSourcePool buildDataSourcePool(DataSourceConfig value) {

        JdbcDriverClassHelper.loadDriverClass(value.getDriverClass(), value.getJdbcUrl());

        if (value.getType().equalsIgnoreCase(Constants.CONNECTION_POOL_TYPE_C3P0)) {
            return new C3p0DataSourcePool();
        } else if (value.getType().equalsIgnoreCase(Constants.CONNECTION_POOL_TYPE_TOMCAT_JDBC)) {
            return new TomcatJdbcDataSourcePool();
        } else if (value.getType().equalsIgnoreCase("druid")) {
            return new DruidDataSourcePool();
        } else if (value.getType().equalsIgnoreCase(Constants.CONNECTION_POOL_TYPE_DBCP2)) {
            return new Dbcp2DataSourcePool();
        } else if (value.getType().equalsIgnoreCase(Constants.CONNECTION_POOL_TYPE_DBCP)) {
            return new DbcpDataSourcePool();
        } else if (value.getType().equalsIgnoreCase(Constants.CONNECTION_POOL_TYPE_HIKARICP)) {
            return new HikariCPDataSourcePool();
        } else {
            return new DruidDataSourcePool();
            //throw new MSharpConfigException("illegal datasource pool type : " + value.getType());
        }

    }
}
