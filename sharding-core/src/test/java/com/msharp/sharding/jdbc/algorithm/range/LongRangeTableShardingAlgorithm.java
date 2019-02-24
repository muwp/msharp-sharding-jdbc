package com.msharp.sharding.jdbc.algorithm.range;

import com.msharp.sharding.jdbc.sphere.algorithm.RangeShardingJdbcAlgorithm;
import io.shardingsphere.api.algorithm.sharding.RangeShardingValue;

import java.util.Collection;

/**
 * LongRangeTableShardingAlgorithm
 * http://www.tianshouzhi.com/api/tutorials/dragon
 *
 * @author mwup
 * @version 1.0
 * @created 2019/02/15 13:51
 **/
public class LongRangeTableShardingAlgorithm implements RangeShardingJdbcAlgorithm<Long> {

    private int tableNum;

    @Override
    public Collection<String> doSharding(Collection<String> availableTargetNames, RangeShardingValue<Long> shardingValue) {
        return availableTargetNames;
    }

    public void setTableNum(int tableNum) {
        this.tableNum = tableNum;
    }
}