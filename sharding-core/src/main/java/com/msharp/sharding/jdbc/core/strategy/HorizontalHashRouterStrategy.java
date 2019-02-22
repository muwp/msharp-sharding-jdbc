package com.msharp.sharding.jdbc.core.strategy;

/**
 * HorizontalHashRouterStrategy
 *
 * @author mwup
 * @version 1.0
 * @created 2019/02/15 13:51
 **/
public class HorizontalHashRouterStrategy implements RouterStrategy {

    /**
     * 结点数
     */
    private int nodeNum;

    /**
     * 每个结果的数据库数
     */
    private int dbNum;

    /**
     * 每个数据库的表个数
     */
    private int tableNum;

    public HorizontalHashRouterStrategy() {

    }

    public HorizontalHashRouterStrategy(int nodeNum, int dbNum, int tableNum) {
        this.nodeNum = nodeNum;
        this.dbNum = dbNum;
        this.tableNum = tableNum;
    }

    @Override
    public int getNodeNo(Object partitionKey) {
        return getDatabasebNo(partitionKey) / dbNum;
    }

    @Override
    public int getDatabasebNo(Object partitionKey) {
        return getTableNo(partitionKey) / tableNum;
    }

    @Override
    public int getTableNo(Object partitionKey) {
        int hashCode = calcHashCode(partitionKey);
        return hashCode % (nodeNum * dbNum * tableNum);
    }

    private int calcHashCode(Object partitionKey) {
        int hashCode = partitionKey.hashCode();
        if (hashCode < 0) {
            hashCode = -hashCode;
        }
        return hashCode;
    }
}
