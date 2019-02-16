package com.muwp.sharding.jdbc.core;

import java.util.HashMap;
import java.util.List;

/**
 * SplitTablesHolder
 *
 * @author mwup
 * @version 1.0
 * @created 2019/02/15 13:51
 **/
public class SplitTablesHolder {

    private static final String DB_TABLE_SEP = "$";

    private List<SplitTable> splitTables;

    private HashMap<String, SplitTable> splitTablesMapFull;

    private HashMap<String, SplitTable> splitTablesMap;

    public SplitTablesHolder() {

    }

    public SplitTablesHolder(List<SplitTable> splitTables) {
        this.splitTables = splitTables;

        init();
    }

    public void init() {
        this.splitTablesMapFull = new HashMap<>();
        this.splitTablesMap = new HashMap<>();
        for (int i = 0; i < this.splitTables.size(); i++) {
            SplitTable st = this.splitTables.get(i);

            String key = constructKey(st.getDbNam(), st.getTableName());
            this.splitTablesMapFull.put(key, st);

            this.splitTablesMap.put(st.getTableName(), st);
        }
    }

    private String constructKey(String dbName, String tableName) {
        return dbName + DB_TABLE_SEP + tableName;
    }

    public SplitTable searchSplitTable(String dbName, String tableName) {
        return this.splitTablesMapFull.get(constructKey(dbName, tableName));
    }

    public SplitTable searchSplitTable(String tableName) {
        return splitTablesMap.get(tableName);
    }

    public List<SplitTable> getSplitTables() {
        return splitTables;
    }

    public void setSplitTables(List<SplitTable> splitTables) {
        this.splitTables = splitTables;
    }
}
