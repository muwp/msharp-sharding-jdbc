package com.msharp.sharding.jdbc.sphere.algorithm.precise;

import io.shardingsphere.api.algorithm.sharding.PreciseShardingValue;
import io.shardingsphere.api.algorithm.sharding.standard.PreciseShardingAlgorithm;

import java.util.Collection;

/**
 * StringPreciseTableShardingAlgorithm
 *
 * @author mwup
 * @version 1.0
 * @created 2019/02/15 13:51
 **/
public class StringPreciseTableShardingAlgorithm implements PreciseShardingAlgorithm<String> {

    private int tableNum;

    @Override
    public String doSharding(Collection<String> collection, PreciseShardingValue<String> preciseShardingValue) {
        for (final String each : collection) {
            if (each.endsWith(Math.abs(preciseShardingValue.getValue().hashCode()) % tableNum + "")) {
                return each;
            }
        }
        throw new IllegalArgumentException();
    }

    public void setTableNum(int tableNum) {
        this.tableNum = tableNum;
    }
}