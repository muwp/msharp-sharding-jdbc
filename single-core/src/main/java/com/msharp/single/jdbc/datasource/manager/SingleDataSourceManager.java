package com.msharp.single.jdbc.datasource.manager;

import com.msharp.single.jdbc.datasource.config.DataSourceConfig;
import com.msharp.single.jdbc.datasource.jdbc.SingleDataSource;

/**
 * SingleDataSourceManager
 *
 * @author mwup
 * @version 1.0
 * @created 2018/10/29 13:51
 **/
public interface SingleDataSourceManager {

    SingleDataSource createDataSource(DataSourceConfig config);

    void destoryDataSource(SingleDataSource dataSource);

    void init();

    void stop();
}