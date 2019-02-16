package com.muwp.sharding.jdbc.core.strategy;

/**
 * SplitStrategy
 *
 * @author mwup
 * @version 1.0
 * @created 2019/02/15 13:51
 **/
public interface SplitStrategy {

    <K> int getNodeNo(K splitKey);

    <K> int getDbNo(K splitKey);

    <K> int getTableNo(K splitKey);
}
