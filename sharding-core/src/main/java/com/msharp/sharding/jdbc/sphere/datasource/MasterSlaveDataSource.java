package com.msharp.sharding.jdbc.sphere.datasource;

import com.msharp.sharding.jdbc.sphere.connection.MasterSlaveConnection;
import io.shardingsphere.api.ConfigMapContext;
import io.shardingsphere.api.config.rule.MasterSlaveRuleConfiguration;
import io.shardingsphere.core.constant.properties.ShardingProperties;
import io.shardingsphere.core.rule.MasterSlaveRule;
import io.shardingsphere.shardingjdbc.jdbc.adapter.AbstractDataSourceAdapter;
import io.shardingsphere.transaction.api.TransactionTypeHolder;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.Map;
import java.util.Properties;

/**
 * MasterSlaveDataSource
 *
 * @author mwup
 * @version 1.0
 * @created 2019/03/01 13:51
 **/
public class MasterSlaveDataSource extends AbstractDataSourceAdapter {

    private final DatabaseMetaData databaseMetaData;

    private final MasterSlaveRule masterSlaveRule;

    private final ShardingProperties shardingProperties;

    public MasterSlaveDataSource(final Map<String, DataSource> dataSourceMap, final MasterSlaveRuleConfiguration masterSlaveRuleConfig, final Map<String, Object> configMap, final Properties props) throws SQLException {
        super(dataSourceMap);
        this.databaseMetaData = getDatabaseMetaData(dataSourceMap);
        if (!configMap.isEmpty()) {
            ConfigMapContext.getInstance().getConfigMap().putAll(configMap);
        }
        this.masterSlaveRule = new MasterSlaveRule(masterSlaveRuleConfig);
        this.shardingProperties = new ShardingProperties(null == props ? new Properties() : props);
    }

    public MasterSlaveDataSource(final Map<String, DataSource> dataSourceMap, final MasterSlaveRule masterSlaveRule, final Map<String, Object> configMap, final Properties props) throws SQLException {
        super(dataSourceMap);
        this.databaseMetaData = getDatabaseMetaData(dataSourceMap);
        if (!configMap.isEmpty()) {
            ConfigMapContext.getInstance().getConfigMap().putAll(configMap);
        }
        this.masterSlaveRule = masterSlaveRule;
        this.shardingProperties = new ShardingProperties(null == props ? new Properties() : props);
    }

    private DatabaseMetaData getDatabaseMetaData(final Map<String, DataSource> dataSourceMap) throws SQLException {
        try (Connection connection = dataSourceMap.values().iterator().next().getConnection()) {
            return connection.getMetaData();
        }
    }

    @Override
    public final MasterSlaveConnection getConnection() {
        return new MasterSlaveConnection(this, getShardingTransactionalDataSources().getDataSourceMap(), TransactionTypeHolder.get());
    }

    public ShardingProperties getShardingProperties() {
        return shardingProperties;
    }

    public MasterSlaveRule getMasterSlaveRule() {
        return masterSlaveRule;
    }

    public DatabaseMetaData getDatabaseMetaData() {
        return databaseMetaData;
    }
}
