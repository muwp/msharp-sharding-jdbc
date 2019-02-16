package com.muwp.sharding.jdbc.core;

import com.muwp.sharding.jdbc.core.strategy.HorizontalHashSplitStrategy;
import com.muwp.sharding.jdbc.core.strategy.SplitStrategy;
import com.muwp.sharding.jdbc.core.strategy.VerticalHashSplitStrategy;
import com.muwp.sharding.jdbc.enums.SplitStrategyType;

import java.io.Serializable;
import java.util.List;

/**
 * SplitTable
 *
 * @author mwup
 * @version 1.0
 * @created 2019/02/15 13:51
 **/
public class SplitTable implements Serializable {

    private String dbNam;

    private int dbNum;

    private String tableName;

    private int tableNum;

    private SplitStrategyType splitStrategyType = SplitStrategyType.HORIZONTAL;

    private SplitStrategy splitStrategy;

    private List<SplitNode> splitNodes;

    private boolean readWriteSeparate = true;

    public void init() {
        if (splitStrategyType == SplitStrategyType.VERTICAL) {
            this.splitStrategy = new VerticalHashSplitStrategy(splitNodes.size(), dbNum, tableNum);
        } else if (splitStrategyType == SplitStrategyType.HORIZONTAL) {
            this.splitStrategy = new HorizontalHashSplitStrategy(splitNodes.size(), dbNum, tableNum);
        }
    }

    public void setSplitStrategyType(String splitStrategyType) {
        this.splitStrategyType = SplitStrategyType.valueOf(splitStrategyType);
    }

    public String getDbNam() {
        return dbNam;
    }

    public void setDbNam(String dbNamePrifix) {
        this.dbNam = dbNamePrifix;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableNamePrifix) {
        this.tableName = tableNamePrifix;
    }

    public int getDbNum() {
        return dbNum;
    }

    public void setDbNum(int dbNum) {
        this.dbNum = dbNum;
    }

    public int getTableNum() {
        return tableNum;
    }

    public void setTableNum(int tableNum) {
        this.tableNum = tableNum;
    }

    public List<SplitNode> getSplitNodes() {
        return splitNodes;
    }

    public void setSplitNodes(List<SplitNode> splitNodes) {
        this.splitNodes = splitNodes;
    }

    public SplitStrategy getSplitStrategy() {
        return splitStrategy;
    }

    public void setSplitStrategy(SplitStrategy splitStrategy) {
        this.splitStrategy = splitStrategy;
    }

    public boolean isReadWriteSeparate() {
        return readWriteSeparate;
    }

    public void setReadWriteSeparate(boolean readWriteSeparate) {
        this.readWriteSeparate = readWriteSeparate;
    }
}