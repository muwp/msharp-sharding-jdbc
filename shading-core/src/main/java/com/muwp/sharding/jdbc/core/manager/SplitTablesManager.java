package com.muwp.sharding.jdbc.core.manager;

import java.util.HashMap;
import java.util.List;

/**
 * SplitTablesManager
 *
 * @author mwup
 * @version 1.0
 * @created 2019/02/15 13:51
 **/
public class SplitTablesManager {

    private static final String DB_TABLE_SEP = "$";

    private List<SplitTableManager> splitTableManagers;

    private HashMap<String, SplitTableManager> splitTablesMapFull;

    private HashMap<String, SplitTableManager> splitTablesMap;

    public SplitTablesManager() {

    }

    public SplitTablesManager(List<SplitTableManager> splitTables) {
        this.splitTableManagers = splitTables;

        init();
    }

    public void init() {
        this.splitTablesMapFull = new HashMap<>();
        this.splitTablesMap = new HashMap<>();
        for (int i = 0; i < this.splitTableManagers.size(); i++) {
            SplitTableManager st = this.splitTableManagers.get(i);
            String key = constructKey(st.getDbNam(), st.getTableName());
            this.splitTablesMapFull.put(key, st);
            this.splitTablesMap.put(st.getTableName(), st);
        }
    }

    private String constructKey(String dbName, String tableName) {
        return dbName + DB_TABLE_SEP + tableName;
    }

    public SplitTableManager searchSplitTable(String dbName, String tableName) {
        return this.splitTablesMapFull.get(constructKey(dbName, tableName));
    }

    public SplitTableManager searchSplitTable(String tableName) {
        return splitTablesMap.get(tableName);
    }

    public List<SplitTableManager> getSplitTableManagers() {
        return splitTableManagers;
    }

    public void setSplitTableManagers(List<SplitTableManager> splitTableManagers) {
        this.splitTableManagers = splitTableManagers;
    }
}
