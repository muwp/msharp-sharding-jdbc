package com.msharp.sharding.jdbc.service.algorithm;

import io.shardingsphere.api.algorithm.sharding.PreciseShardingValue;
import io.shardingsphere.api.algorithm.sharding.standard.PreciseShardingAlgorithm;

import java.util.Collection;

/**
 * StringPreciseDatabaseShardingAlgorithm
 *
 * @author mwup
 * @version 1.0
 * @created 2019/02/15 13:51
 **/
public class StringPreciseDatabaseShardingAlgorithm implements PreciseShardingAlgorithm<String> {

    private int databaseNum;

    @Override
    public String doSharding(Collection<String> collection, PreciseShardingValue<String> preciseShardingValue) {
        for (final String each : collection) {
            if (each.endsWith(Math.abs(preciseShardingValue.getValue().hashCode()) % databaseNum + "")) {
                return each;
            }
        }
        throw new IllegalArgumentException();
    }

    public void setDatabaseNum(int databaseNum) {
        this.databaseNum = databaseNum;
    }
}