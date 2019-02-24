package com.msharp.sharding.jdbc.algorithm.precise;

import com.msharp.sharding.jdbc.sphere.algorithm.PreciseShardingJdbcAlgorithm;

/**
 * https://shardingsphere.apache.org/document/current/cn/features/
 * https://www.jianshu.com/p/ae831dcc89ce
 * LongPreciseTableShardingAlgorithm
 *
 * @author mwup
 * @version 1.0
 * @created 2019/02/15 13:51
 **/
public class LongPreciseTableShardingAlgorithm implements PreciseShardingJdbcAlgorithm<Long> {

    private int tableNum;


    @Override
    public boolean doSharding(String col, Long value) {
        return col.endsWith(Math.abs(value) % tableNum + "");
    }

    public void setTableNum(int tableNum) {
        this.tableNum = tableNum;
    }
}