package com.msharp.sharding.jdbc.core.manager;

import org.springframework.beans.factory.InitializingBean;

import java.util.HashMap;
import java.util.List;

/**
 * ShardTablesManager
 *
 * @author mwup
 * @version 1.0
 * @created 2019/02/15 13:51
 **/
public class ShardTablesManager implements InitializingBean {

    private static final String DB_TABLE_SEP = "$";

    private List<ShardTableManager> splitTableManagers;

    private HashMap<String, ShardTableManager> splitTablesMapFull;

    private HashMap<String, ShardTableManager> splitTablesMap;

    @Override
    public void afterPropertiesSet() throws Exception {
        init();
    }

    public void init() {
        final int size = this.splitTableManagers.size();
        this.splitTablesMapFull = new HashMap<>(size);
        this.splitTablesMap = new HashMap<>(size);
        for (int i = 0; i < size; i++) {
            ShardTableManager st = this.splitTableManagers.get(i);
            String key = constructKey(st.getDbNam(), st.getTableName());
            this.splitTablesMapFull.put(key, st);
            this.splitTablesMap.put(st.getTableName(), st);
        }
    }

    public ShardTableManager searchShardTableManager(String dbName, String tableName) {
        return this.splitTablesMapFull.get(constructKey(dbName, tableName));
    }

    public ShardTableManager searchSplitTable(String tableName) {
        return splitTablesMap.get(tableName);
    }

    public List<ShardTableManager> getSplitTableManagers() {
        return splitTableManagers;
    }

    public void setSplitTableManagers(List<ShardTableManager> splitTableManagers) {
        this.splitTableManagers = splitTableManagers;
    }

    private String constructKey(String dbName, String tableName) {
        return dbName + DB_TABLE_SEP + tableName;
    }
}
