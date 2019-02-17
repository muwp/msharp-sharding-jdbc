package com.muwp.sharding.jdbc.core.strategy;

/**
 * RouterStrategy
 *
 * @author mwup
 * @version 1.0
 * @created 2019/02/15 13:51
 **/
public interface RouterStrategy {

    <K> int getNodeNo(K partitionKey);

    <K> int getDatabasebNo(K partitionKey);

    <K> int getTableNo(K partitionKey);
}
