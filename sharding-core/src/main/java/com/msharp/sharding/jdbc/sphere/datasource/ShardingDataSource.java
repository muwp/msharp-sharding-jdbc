package com.msharp.sharding.jdbc.sphere.datasource;


import com.google.common.base.Preconditions;
import com.msharp.sharding.jdbc.sphere.connection.ShardingConnection;
import io.shardingsphere.api.ConfigMapContext;
import io.shardingsphere.core.rule.ShardingRule;
import io.shardingsphere.shardingjdbc.jdbc.adapter.AbstractDataSourceAdapter;
import io.shardingsphere.shardingjdbc.jdbc.core.ShardingContext;
import io.shardingsphere.shardingjdbc.jdbc.core.datasource.MasterSlaveDataSource;
import io.shardingsphere.transaction.api.TransactionTypeHolder;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

/**
 * ShardingDataSource
 *
 * @author mwup
 * @version 1.0
 * @created 2019/02/15 13:51
 **/
public class ShardingDataSource extends AbstractDataSourceAdapter {

    private final ShardingContext shardingContext;

    public ShardingDataSource(final Map<String, DataSource> dataSourceMap, final ShardingRule shardingRule) throws SQLException {
        this(dataSourceMap, shardingRule, new ConcurrentHashMap<String, Object>(), new Properties());
    }

    public ShardingDataSource(final Map<String, DataSource> dataSourceMap, final ShardingRule shardingRule, final Map<String, Object> configMap, final Properties props) throws SQLException {
        super(dataSourceMap);
        checkDataSourceType(dataSourceMap);
        if (!configMap.isEmpty()) {
            ConfigMapContext.getInstance().getConfigMap().putAll(configMap);
        }
        shardingContext = new ShardingContext(getDataSourceMap(), shardingRule, getDatabaseType(), props);
    }

    private void checkDataSourceType(final Map<String, DataSource> dataSourceMap) {
        for (DataSource each : dataSourceMap.values()) {
            Preconditions.checkArgument(!(each instanceof MasterSlaveDataSource), "Initialized data sources can not be master-slave data sources.");
        }
    }

    @Override
    public final ShardingConnection getConnection() {
        return new ShardingConnection(getShardingTransactionalDataSources().getDataSourceMap(), shardingContext, TransactionTypeHolder.get());
    }

    @Override
    public final void close() {
        super.close();
        shardingContext.close();
    }
}