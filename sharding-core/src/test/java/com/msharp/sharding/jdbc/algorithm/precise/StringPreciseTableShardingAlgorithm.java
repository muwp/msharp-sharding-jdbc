package com.msharp.sharding.jdbc.algorithm.precise;

import com.msharp.sharding.jdbc.sphere.algorithm.PreciseShardingJdbcAlgorithm;

/**
 * StringPreciseTableShardingAlgorithm
 *
 * @author mwup
 * @version 1.0
 * @created 2019/02/15 13:51
 **/
public class StringPreciseTableShardingAlgorithm implements PreciseShardingJdbcAlgorithm<String> {

    private int tableNum;

    @Override
    public boolean doSharding(String col, String value) {
        if (col.endsWith(Math.abs(value.hashCode()) % tableNum + "")) {
            return true;
        }
        return false;
    }

    public void setTableNum(int tableNum) {
        this.tableNum = tableNum;
    }
}