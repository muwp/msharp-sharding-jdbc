package com.msharp.sharding.jdbc.sphere.algorithm;

/**
 * PreciseShardingAlgorithm
 *
 * @author mwup
 * @version 1.0
 * @created 2019/04/09 13:51
 **/
public interface PreciseShardingAlgorithm<T extends Comparable<?>> extends io.shardingsphere.api.algorithm.sharding.standard.PreciseShardingAlgorithm<T> {

}
