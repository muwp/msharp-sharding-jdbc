package com.msharp.single.jdbc.datasource.pool;

import com.msharp.single.jdbc.datasource.config.DataSourceConfig;
import com.msharp.single.jdbc.datasource.jdbc.SingleDataSource;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * DataSourcePool
 *
 * @author mwup
 * @version 1.0
 * @created 2018/10/29 13:51
 **/
public interface DataSourcePool {

    DataSource build(DataSourceConfig config, boolean withDefaultValue);

    void close(SingleDataSource singleDataSource, boolean forceClose) throws SQLException;

    DataSource getInnerDataSourcePool();

    int getNumBusyConnection();

    int getNumConnections();

    int getNumIdleConnection();
}
