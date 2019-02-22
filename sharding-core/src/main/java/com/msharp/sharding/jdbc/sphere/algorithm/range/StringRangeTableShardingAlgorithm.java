package com.msharp.sharding.jdbc.sphere.algorithm.range;

import io.shardingsphere.api.algorithm.sharding.RangeShardingValue;
import io.shardingsphere.api.algorithm.sharding.standard.RangeShardingAlgorithm;

import java.util.Collection;

/**
 * StringRangeTableShardingAlgorithm
 *
 * @author mwup
 * @version 1.0
 * @created 2019/02/15 13:51
 **/
public class StringRangeTableShardingAlgorithm implements RangeShardingAlgorithm<Long> {

    private int tableNum;

    @Override
    public Collection<String> doSharding(Collection<String> availableTargetNames, RangeShardingValue<Long> shardingValue) {
        return availableTargetNames;
    }

    public void setTableNum(int tableNum) {
        this.tableNum = tableNum;
    }
}