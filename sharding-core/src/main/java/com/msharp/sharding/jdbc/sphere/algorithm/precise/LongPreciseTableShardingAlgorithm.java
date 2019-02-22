package com.msharp.sharding.jdbc.sphere.algorithm.precise;

import io.shardingsphere.api.algorithm.sharding.PreciseShardingValue;
import io.shardingsphere.api.algorithm.sharding.standard.PreciseShardingAlgorithm;

import java.util.Collection;

/**
 * https://shardingsphere.apache.org/document/current/cn/features/
 * https://www.jianshu.com/p/ae831dcc89ce
 * LongPreciseTableShardingAlgorithm
 *
 * @author mwup
 * @version 1.0
 * @created 2019/02/15 13:51
 **/
public class LongPreciseTableShardingAlgorithm implements PreciseShardingAlgorithm<Long> {

    private int tableNum;

    @Override
    public String doSharding(Collection<String> collection, PreciseShardingValue<Long> preciseShardingValue) {
        for (final String each : collection) {
            if (each.endsWith(Math.abs(preciseShardingValue.getValue()) % tableNum + "")) {
                return each;
            }
        }
        throw new IllegalArgumentException();
    }

    public void setTableNum(int tableNum) {
        this.tableNum = tableNum;
    }
}