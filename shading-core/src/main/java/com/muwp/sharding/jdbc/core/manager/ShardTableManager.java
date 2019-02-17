package com.muwp.sharding.jdbc.core.manager;

import com.muwp.sharding.jdbc.core.strategy.HorizontalHashRouterStrategy;
import com.muwp.sharding.jdbc.core.strategy.RouterStrategy;
import com.muwp.sharding.jdbc.core.strategy.VerticalHashRouterStrategy;
import com.muwp.sharding.jdbc.enums.RouterStrategyType;

import java.util.List;

/**
 * ShardTableManager
 *
 * @author mwup
 * @version 1.0
 * @created 2019/02/15 13:51
 **/
public class ShardTableManager {

    private String dbNam;

    private int dbNum;

    private String tableName;

    private int tableNum;

    private RouterStrategyType splitStrategyType = RouterStrategyType.HORIZONTAL;

    private RouterStrategy routerStrategy;

    private List<ShardJdbcTemplateManager> shardTemplateManagers;

    private boolean readWriteSeparate = true;

    public void init() {
        if (splitStrategyType == RouterStrategyType.VERTICAL) {
            this.routerStrategy = new VerticalHashRouterStrategy(shardTemplateManagers.size(), dbNum, tableNum);
        } else if (splitStrategyType == RouterStrategyType.HORIZONTAL) {
            this.routerStrategy = new HorizontalHashRouterStrategy(shardTemplateManagers.size(), dbNum, tableNum);
        }
    }

    public void setSplitStrategyType(String splitStrategyType) {
        this.splitStrategyType = RouterStrategyType.valueOf(splitStrategyType);
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

    public List<ShardJdbcTemplateManager> getShardTemplateManagers() {
        return shardTemplateManagers;
    }

    public void setShardTemplateManagers(List<ShardJdbcTemplateManager> shardTemplateManagers) {
        this.shardTemplateManagers = shardTemplateManagers;
    }

    public RouterStrategy getRouterStrategy() {
        return routerStrategy;
    }

    public void setRouterStrategy(RouterStrategy splitStrategy) {
        this.routerStrategy = splitStrategy;
    }

    public boolean isReadWriteSeparate() {
        return readWriteSeparate;
    }

    public void setReadWriteSeparate(boolean readWriteSeparate) {
        this.readWriteSeparate = readWriteSeparate;
    }
}