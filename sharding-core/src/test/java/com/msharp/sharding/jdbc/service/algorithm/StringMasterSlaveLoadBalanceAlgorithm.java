package com.msharp.sharding.jdbc.service.algorithm;

import io.shardingsphere.api.algorithm.masterslave.MasterSlaveLoadBalanceAlgorithm;

import java.util.List;

public class StringMasterSlaveLoadBalanceAlgorithm implements MasterSlaveLoadBalanceAlgorithm {

    @Override
    public String getDataSource(String name, String masterDataSourceName, List<String> slaveDataSourceNames) {
        return masterDataSourceName;
    }
}
