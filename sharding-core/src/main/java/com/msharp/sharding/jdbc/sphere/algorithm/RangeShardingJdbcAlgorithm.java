package com.msharp.sharding.jdbc.sphere.algorithm;

import io.shardingsphere.api.algorithm.sharding.standard.RangeShardingAlgorithm;

/**
 * RangeShardingJdbcAlgorithm
 *
 * @author mwup
 * @version 1.0
 * @created 2019/02/15 13:51
 **/
public interface RangeShardingJdbcAlgorithm<T extends Comparable<?>> extends RangeShardingAlgorithm<T> {
}
