package com.msharp.single.jdbc.datasource.pool;

import com.msharp.single.jdbc.enums.DataSourceState;
import com.msharp.single.jdbc.datasource.config.DataSourceConfig;

/**
 * SingleDataSourceMBean
 *
 * @author mwup
 * @version 1.0
 * @created 2018/10/29 13:51
 **/
public interface SingleDataSourceMBean {

    String getId();

    DataSourceState getState();

    DataSourceConfig getConfig();

    String getCurrentState();

    int getNumConnections();

    int getNumBusyConnection();

    int getNumIdleConnection();
}