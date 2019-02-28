package com.msharp.sharding.jdbc.sphere.spring;

import io.shardingsphere.api.algorithm.masterslave.MasterSlaveLoadBalanceAlgorithm;
import io.shardingsphere.api.algorithm.masterslave.MasterSlaveLoadBalanceAlgorithmType;
import io.shardingsphere.api.config.rule.MasterSlaveRuleConfiguration;
import io.shardingsphere.shardingjdbc.jdbc.core.datasource.MasterSlaveDataSource;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

/**
 * SpringMasterSlaveDataSource
 * <p>
 *
 * @author mwup
 * @version 1.0
 * @created 2019/02/15 13:51
 **/
public class SpringMasterSlaveDataSource extends MasterSlaveDataSource {

    public SpringMasterSlaveDataSource(final Map<String, DataSource> dataSourceMap, final String name, final String masterDataSourceName, final Collection<String> slaveDataSourceNames, final MasterSlaveLoadBalanceAlgorithm strategy, final Map<String, Object> configMap, final Properties props) throws SQLException {
        super(dataSourceMap, getMasterSlaveRuleConfiguration(name, masterDataSourceName, slaveDataSourceNames, strategy), null == configMap ? new LinkedHashMap<String, Object>() : configMap, null == props ? new Properties() : props);
    }

    public SpringMasterSlaveDataSource(final Map<String, DataSource> dataSourceMap, final String name, final String masterDataSourceName, final Collection<String> slaveDataSourceNames, final MasterSlaveLoadBalanceAlgorithmType strategyType, final Map<String, Object> configMap, final Properties props) throws SQLException {
        super(dataSourceMap, getMasterSlaveRuleConfiguration(name, masterDataSourceName, slaveDataSourceNames, null == strategyType ? null : strategyType.getAlgorithm()), null == configMap ? new LinkedHashMap<String, Object>() : configMap, null == props ? new Properties() : props);
    }

    private static MasterSlaveRuleConfiguration getMasterSlaveRuleConfiguration(final String name, final String masterDataSourceName, final Collection<String> slaveDataSourceNames, final MasterSlaveLoadBalanceAlgorithm loadBalanceAlgorithm) {
        return new MasterSlaveRuleConfiguration(name, masterDataSourceName, slaveDataSourceNames, loadBalanceAlgorithm);
    }
}