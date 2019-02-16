package com.muwp.sharding.jdbc.core.strategy;

/**
 * HorizontalHashSplitStrategy
 *
 * @author mwup
 * @version 1.0
 * @created 2019/02/15 13:51
 **/
public class HorizontalHashSplitStrategy implements SplitStrategy {

    private int portNum;

    private int dbNum;

    private int tableNum;

    public HorizontalHashSplitStrategy() {

    }

    public HorizontalHashSplitStrategy(int portNum, int dbNum, int tableNum) {
        this.portNum = portNum;
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
        return hashCode % (portNum * dbNum * tableNum);
    }

    private int calcHashCode(Object splitKey) {
        int hashCode = splitKey.hashCode();
        if (hashCode < 0) {
            hashCode = -hashCode;
        }
        return hashCode;
    }
}
