package com.msharp.sharding.jdbc.sphere.spring;

import com.msharp.sharding.jdbc.sphere.datasource.ShardingDataSource;
import io.shardingsphere.api.config.rule.ShardingRuleConfiguration;
import io.shardingsphere.core.rule.ShardingRule;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Map;
import java.util.Properties;

/**
 * SpringShardingDataSource
 * <p>
 *
 * @author mwup
 * @version 1.0
 * @created 2019/02/15 13:51
 **/
public class SpringShardingDataSource extends ShardingDataSource {

    public SpringShardingDataSource(final Map<String, DataSource> dataSourceMap, final ShardingRuleConfiguration shardingRuleConfig, final Map<String, Object> configMap, final Properties props) throws SQLException {
        super(dataSourceMap, new ShardingRule(shardingRuleConfig, dataSourceMap.keySet()), configMap, props);
    }
}