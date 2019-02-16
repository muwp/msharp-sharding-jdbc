package com.muwp.sharding.jdbc.core.strategy;

/**
 * HorizontalHashSplitStrategy
 *
 * @author mwup
 * @version 1.0
 * @created 2019/02/15 13:51
 **/
public class HorizontalHashSplitStrategy implements SplitStrategy {

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

    public HorizontalHashSplitStrategy() {

    }

    public HorizontalHashSplitStrategy(int nodeNum, int dbNum, int tableNum) {
        this.nodeNum = nodeNum;
        this.dbNum = dbNum;
        this.tableNum = tableNum;
    }

    @Override
    public int getNodeNo(Object splitKey) {
        return getDbNo(splitKey) / dbNum;
    }

    @Override
    public int getDbNo(Object splitKey) {
        return getTableNo(splitKey) / tableNum;
    }

    @Override
    public int getTableNo(Object splitKey) {
        int hashCode = calcHashCode(splitKey);
        return hashCode % (nodeNum * dbNum * tableNum);
    }

    private int calcHashCode(Object splitKey) {
        int hashCode = splitKey.hashCode();
        if (hashCode < 0) {
            hashCode = -hashCode;
        }
        return hashCode;
    }
}
