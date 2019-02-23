/*
 * Copyright (c) 2011-2018, Meituan Dianping. All Rights Reserved.
 *
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.msharp.single.jdbc.single.stat;

import com.msharp.single.jdbc.single.config.DataSourceConfig;
import com.msharp.single.jdbc.single.jdbc.SingleDataSource;
import com.msharp.single.jdbc.single.stat.SinglePreparedStatement;
import com.msharp.single.jdbc.single.stat.SingleStatement;

import java.sql.*;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executor;

/**
 * SingleConnection
 *
 * @author mwup
 * @version 1.0
 * @created 2018/10/29 13:51
 **/
public class SingleConnection implements Connection {

    private final String dsId;

    private final Connection conn;

    private final SingleDataSource dataSource;

    public SingleConnection(SingleDataSource dataSource, final DataSourceConfig config, Connection conn) {
        this.dsId = config.getId();
        this.dataSource = dataSource;
        this.conn = conn;
    }

    public String getDataSourceId() {
        return dsId;
    }

    @Override
    public void abort(Executor executor) throws SQLException {
        throw new UnsupportedOperationException("zebra does not support abort");
    }

    @Override
    public void clearWarnings() throws SQLException {
        conn.clearWarnings();
    }

    private void closeOrigin() throws SQLException {
        conn.close();
    }

    @Override
    public void close() throws SQLException {
        closeOrigin();
    }

    @Override
    public void commit() throws SQLException {
        conn.commit();
    }

    @Override
    public Array createArrayOf(String typeName, Object[] elements) throws SQLException {
        return conn.createArrayOf(typeName, elements);
    }

    @Override
    public Blob createBlob() throws SQLException {
        return conn.createBlob();
    }

    @Override
    public Clob createClob() throws SQLException {
        return conn.createClob();
    }

    @Override
    public NClob createNClob() throws SQLException {
        return conn.createNClob();
    }

    @Override
    public SQLXML createSQLXML() throws SQLException {
        return conn.createSQLXML();
    }

    @Override
    public Statement createStatement() throws SQLException {
        Statement stmt = conn.createStatement();
        return new SingleStatement(this.dsId, this, stmt);
    }

    @Override
    public Statement createStatement(int resultSetType, int resultSetConcurrency) throws SQLException {
        Statement stmt = conn.createStatement(resultSetType, resultSetConcurrency);
        return new SingleStatement(this.dsId, this, stmt);
    }

    @Override
    public Statement createStatement(int resultSetType, int resultSetConcurrency, int resultSetHoldability)
            throws SQLException {
        Statement stmt = conn.createStatement(resultSetType, resultSetConcurrency, resultSetHoldability);
        return new SingleStatement(this.dsId, this, stmt);
    }

    @Override
    public Struct createStruct(String typeName, Object[] attributes) throws SQLException {
        return conn.createStruct(typeName, attributes);
    }

    @Override
    public boolean getAutoCommit() throws SQLException {
        return conn.getAutoCommit();
    }

    @Override
    public void setAutoCommit(boolean autoCommit) throws SQLException {
        conn.setAutoCommit(autoCommit);
    }

    @Override
    public String getCatalog() throws SQLException {
        return conn.getCatalog();
    }

    @Override
    public void setCatalog(String catalog) throws SQLException {
        conn.setCatalog(catalog);
    }

    @Override
    public String getClientInfo(String name) throws SQLException {
        return conn.getClientInfo(name);
    }

    @Override
    public Properties getClientInfo() throws SQLException {
        return conn.getClientInfo();
    }

    @Override
    public void setClientInfo(Properties properties) throws SQLClientInfoException {
        conn.setClientInfo(properties);
    }

    public SingleDataSource getDataSource() {
        return dataSource;
    }

    @Override
    public int getHoldability() throws SQLException {
        return conn.getHoldability();
    }

    @Override
    public void setHoldability(int holdability) throws SQLException {
        conn.setHoldability(holdability);
    }

    @Override
    public DatabaseMetaData getMetaData() throws SQLException {
        return conn.getMetaData();
    }

    @Override
    public int getNetworkTimeout() throws SQLException {
        throw new UnsupportedOperationException("zebra does not support getNetworkTimeout");
    }

    @Override
    public String getSchema() throws SQLException {
        return conn.getSchema();
    }

    @Override
    public void setSchema(String schema) throws SQLException {
        conn.setSchema(schema);
    }

    @Override
    public int getTransactionIsolation() throws SQLException {
        return conn.getTransactionIsolation();
    }

    @Override
    public void setTransactionIsolation(int level) throws SQLException {
        conn.setTransactionIsolation(level);
    }

    @Override
    public Map<String, Class<?>> getTypeMap() throws SQLException {
        return conn.getTypeMap();
    }

    @Override
    public void setTypeMap(Map<String, Class<?>> map) throws SQLException {
        conn.setTypeMap(map);
    }

    @Override
    public SQLWarning getWarnings() throws SQLException {
        return conn.getWarnings();
    }

    @Override
    public boolean isClosed() throws SQLException {
        return conn.isClosed();
    }

    @Override
    public boolean isReadOnly() throws SQLException {
        return conn.isReadOnly();
    }

    @Override
    public void setReadOnly(boolean readOnly) throws SQLException {
        conn.setReadOnly(readOnly);
    }

    @Override
    public boolean isValid(int timeout) throws SQLException {
        return conn.isValid(timeout);
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return conn.isWrapperFor(iface);
    }

    @Override
    public String nativeSQL(String sql) throws SQLException {
        return conn.nativeSQL(sql);
    }

    @Override
    public CallableStatement prepareCall(String sql) throws SQLException {
        return conn.prepareCall(sql);
    }

    @Override
    public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
        return conn.prepareCall(sql, resultSetType, resultSetConcurrency);
    }

    @Override
    public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency,
                                         int resultSetHoldability) throws SQLException {
        return conn.prepareCall(sql, resultSetType, resultSetConcurrency, resultSetHoldability);
    }

    @Override
    public PreparedStatement prepareStatement(String sql) throws SQLException {
        String newSql = processSQL(sql, true);
        PreparedStatement pstmt = conn.prepareStatement(newSql);

        return new SinglePreparedStatement(this.dsId, this, pstmt, newSql);
    }

    @Override
    public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency)
            throws SQLException {
        String newSql = processSQL(sql, true);
        PreparedStatement pstmt = conn.prepareStatement(newSql, resultSetType, resultSetConcurrency);

        return new SinglePreparedStatement(this.dsId, this, pstmt, newSql);
    }

    @Override
    public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
        String newSql = processSQL(sql, true);
        PreparedStatement pstmt = conn.prepareStatement(newSql, resultSetType, resultSetConcurrency, resultSetHoldability);
        return new SinglePreparedStatement(this.dsId, this, pstmt, newSql);
    }

    @Override
    public PreparedStatement prepareStatement(String sql, int autoGeneratedKeys) throws SQLException {
        String newSql = processSQL(sql, true);
        PreparedStatement pstmt = conn.prepareStatement(newSql, autoGeneratedKeys);

        return new SinglePreparedStatement(this.dsId, this, pstmt, newSql);
    }

    @Override
    public PreparedStatement prepareStatement(String sql, int[] columnIndexes) throws SQLException {
        String newSql = processSQL(sql, true);
        PreparedStatement pstmt = conn.prepareStatement(newSql, columnIndexes);

        return new SinglePreparedStatement(this.dsId, this, pstmt, newSql);
    }

    @Override
    public PreparedStatement prepareStatement(String sql, String[] columnNames) throws SQLException {
        String newSql = processSQL(sql, true);
        PreparedStatement pstmt = conn.prepareStatement(newSql, columnNames);

        return new SinglePreparedStatement(this.dsId, this, pstmt, newSql);
    }

    protected String processSQL(final String sql, boolean isPreparedStmt) throws SQLException {
        return sql;
    }

    @Override
    public void releaseSavepoint(Savepoint savepoint) throws SQLException {
        conn.releaseSavepoint(savepoint);
    }

    @Override
    public void rollback() throws SQLException {
        conn.rollback();
    }

    @Override
    public void rollback(Savepoint savepoint) throws SQLException {
        conn.rollback(savepoint);
    }

    @Override
    public void setClientInfo(String name, String value) throws SQLClientInfoException {
        conn.setClientInfo(name, value);
    }

    @Override
    public void setNetworkTimeout(Executor executor, int milliseconds) throws SQLException {
        throw new UnsupportedOperationException("zebra does not support setNetworkTimeout");
    }

    @Override
    public Savepoint setSavepoint() throws SQLException {
        return conn.setSavepoint();
    }

    @Override
    public Savepoint setSavepoint(String name) throws SQLException {
        return conn.setSavepoint(name);
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        return conn.unwrap(iface);
    }
}
