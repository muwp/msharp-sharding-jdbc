package com.msharp.sharding.jdbc.sphere.algorithm;

import io.shardingsphere.api.algorithm.sharding.PreciseShardingValue;
import io.shardingsphere.api.algorithm.sharding.standard.PreciseShardingAlgorithm;

import java.util.Collection;

/**
 * PreciseShardingJdbcAlgorithm
 *
 * @author mwup
 * @version 1.0
 * @created 2019/02/15 13:51
 **/
public interface PreciseShardingJdbcAlgorithm<T extends Comparable<?>> extends PreciseShardingAlgorithm<T> {

    @Override
    default String doSharding(Collection<String> collection, PreciseShardingValue<T> preciseShardingValue) {
        for (final String each : collection) {
            if (this.doSharding(each, preciseShardingValue.getValue())) {
                return each;
            }
        }
        throw new IllegalArgumentException();
    }

    boolean doSharding(String col, T value);
}
