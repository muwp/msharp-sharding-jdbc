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
package com.msharp.single.jdbc.datasource.jdbc;

import com.msharp.single.jdbc.enums.DataSourceState;
import com.msharp.single.jdbc.constants.Constants;
import com.msharp.single.jdbc.exception.MSharpConfigException;
import com.msharp.single.jdbc.exception.MSharpException;
import com.msharp.single.jdbc.log.Logger;
import com.msharp.single.jdbc.log.LoggerFactory;
import com.msharp.single.jdbc.datasource.config.DataSourceConfig;
import com.msharp.single.jdbc.datasource.pool.DataSourcePool;
import com.msharp.single.jdbc.datasource.pool.DataSourcePoolFactory;
import com.msharp.single.jdbc.datasource.pool.SingleDataSourceMBean;
import com.msharp.single.jdbc.datasource.statement.SingleConnection;
import com.msharp.single.jdbc.util.StringUtils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * SingleDataSource
 *
 * @author mwup
 * @version 1.0
 * @created 2018/10/29 13:51
 **/
public class SingleDataSource extends C3p0DataSourceAdapter implements DataSourceLifeCycle, SingleDataSourceMBean {

    protected static final Logger LOGGER = LoggerFactory.getLogger(SingleDataSource.class);

    protected static final Pattern JDBC_URL_PATTERN = Pattern.compile("jdbc:mysql://([^:]+:\\d+)/([^\\?]+).*");

    protected String datasourceId;

    /**
     * c3p0 ,
     * tomcat-jdbc,
     * druid ,
     * dbcp2,
     * dbcp
     */
    protected String poolType = Constants.CONNECTION_POOL_TYPE_C3P0;

    protected boolean lazyInit = true;

    protected DataSourceConfig config;

    protected boolean withDefaultValue;

    protected boolean forceClose;

    protected volatile boolean init = false;

    protected volatile DataSourcePool dataSourcePool;

    protected volatile DataSourceState state = DataSourceState.INITIAL;

    protected volatile AtomicInteger closeAttempt = new AtomicInteger(1);

    protected volatile CountPunisher punisher;

    public SingleDataSource() {
        this.config = new DataSourceConfig();
        this.config.setCanRead(true);
        this.config.setCanWrite(true);
        this.config.setActive(true);
        this.punisher = new CountPunisher(this, config.getTimeWindow(), config.getPunishLimit());
        this.forceClose = true;
    }

    public SingleDataSource(DataSourceConfig config) {
        this.datasourceId = config.getId();
        this.config = config;
        this.punisher = new CountPunisher(this, config.getTimeWindow(), config.getPunishLimit());
        this.poolType = config.getType();
        this.lazyInit = config.isLazyInit();
        this.withDefaultValue = true;
        this.forceClose = false;
        this.dataSourcePool = DataSourcePoolFactory.buildDataSourcePool(config);
        initDataSourceWithFilters(config);
    }

    @Override
    public synchronized void init() {
        if (!init) {
            mergeDataSourceConfig();

            this.withDefaultValue = false;
            if (this.getClass().isAssignableFrom(SingleDataSource.class)) {
                if (!this.poolType.equals(Constants.CONNECTION_POOL_TYPE_C3P0)) {
                    this.withDefaultValue = true;
                }
            }

            this.dataSourcePool = DataSourcePoolFactory.buildDataSourcePool(this.config);
            initDataSourceWithFilters(this.config);

            init = true;
        }
    }

    private void mergeDataSourceConfig() {
        if (config.getDriverClass() == null || config.getDriverClass().length() <= 0) {
            // in case that DBA has not give default value to driverClass.
            config.setDriverClass(dsProperties.getDriverClass());
        }

        this.config.setType(this.poolType);
        this.config.setProperties(dsProperties.getProperties());
    }

    public void setJdbcUrl(String jdbcUrl) {
        checkNull("jdbcUrl", jdbcUrl);
        this.config.setJdbcUrl(jdbcUrl);

        Matcher matcher = JDBC_URL_PATTERN.matcher(jdbcUrl);
        if (matcher.find()) {
            this.config.setId(matcher.group(1) + "/" + matcher.group(2));
        }
    }

    public void setUser(String user) {
        checkNull("user", user);
        this.config.setUsername(user);
    }

    public void setPassword(String password) {
        this.config.setPassword(password);
    }

    protected void checkNull(String key, String value) {
        if (StringUtils.isBlank(value)) {
            throw new MSharpConfigException(key + " cannot be null!");
        }
    }

    private void checkNull() {
        if (this.dataSourcePool == null) {
            throw new MSharpConfigException("dataSourcePool is null, because you have to add ( init-method=\"init\" ) in your spring bean definition or explicitly invoke init method.");
        }
    }

    private void checkState() throws SQLException {
        if (state == DataSourceState.CLOSED || state == DataSourceState.DOWN) {
            throw new SQLException(String.format("dataSource is not available, current state is [%s]", state));
        }
    }

    @Override
    public void close() throws SQLException {
        closeOrigin();
    }

    public void closeOrigin() throws SQLException {
        checkNull();
        this.dataSourcePool.close(this, forceClose);
    }

    @Override
    public synchronized DataSourceConfig getConfig() {
        return this.config;
    }

    @Override
    public Connection getConnection() throws SQLException {
        return getConnection(null, null);
    }

    @Override
    public Connection getConnection(final String username, final String password) throws SQLException {
        return getConnectionOrigin(username, password);
    }

    private SingleConnection getConnectionOrigin(String username, String password) throws SQLException {
        checkState();
        checkNull();
        Connection conn = null;
        try {
            conn = this.dataSourcePool.getInnerDataSourcePool().getConnection();
        } catch (SQLException e) {
            punisher.countAndPunish(e);
            throw e;
        }

        if (state == DataSourceState.INITIAL) {
            state = DataSourceState.UP;
        }

        return new SingleConnection(this, this.config, conn);
    }

    @Override
    public String getCurrentState() {
        return state.toString();
    }

    @Override
    public String getId() {
        return this.datasourceId;
    }

    @Override
    public int getNumBusyConnection() {
        checkNull();

        if (state == DataSourceState.UP) {
            return this.dataSourcePool.getNumBusyConnection();
        } else {
            return 0;
        }
    }

    @Override
    public int getNumConnections() {
        checkNull();

        if (state == DataSourceState.UP) {
            return this.dataSourcePool.getNumConnections();
        } else {
            return 0;
        }
    }

    @Override
    public int getNumIdleConnection() {
        checkNull();

        if (state == DataSourceState.UP) {
            return this.dataSourcePool.getNumIdleConnection();
        } else {
            return 0;
        }
    }

    public CountPunisher getPunisher() {
        return this.punisher;
    }

    @Override
    public DataSourceState getState() {
        return this.state;
    }

    private DataSource initDataSourceWithFilters(final DataSourceConfig value) {
        return initDataSourceOrigin(value);
    }

    private DataSource initDataSourceOrigin(DataSourceConfig value) {
        DataSource result = this.dataSourcePool.build(value, withDefaultValue);

        if (!this.lazyInit) {
            Connection conn = null;
            try {
                conn = this.getConnection();
                LOGGER.info(String.format("dataSource [%s] init pool finish", value.getId()));
            } catch (SQLException e) {
                throw new MSharpException(String.format("dataSource [%s] init pool fail", value.getId()), e);
            } finally {
                try {
                    if (conn != null) {
                        conn.close();
                    }
                } catch (SQLException e) {
                    LOGGER.error(String.format("dataSource [%s] init pool fail", value.getId()));
                }
            }
        }

        return result;
    }

    public boolean isAvailable() {
        return this.state == DataSourceState.INITIAL || this.state == DataSourceState.UP;
    }

    public boolean isClosed() {
        return this.state == DataSourceState.CLOSED;
    }

    public boolean isDown() {
        return this.state == DataSourceState.DOWN;
    }

    @Override
    public void markClosed() {
        this.state = DataSourceState.CLOSED;
    }

    @Override
    public void markDown() {
        this.state = DataSourceState.DOWN;
    }

    @Override
    public void markUp() {
        this.state = DataSourceState.INITIAL;
    }

    @Override
    protected Logger getLogger() {
        return LOGGER;
    }

    @Override
    protected void refresh(String propertyName) {
    }

    public void setPoolType(String poolType) {
        this.poolType = poolType;
    }

    public int getAndIncrementCloseAttempt() {
        return this.closeAttempt.getAndIncrement();
    }

    public int getCloseAttempt() {
        return this.closeAttempt.get();
    }

    public synchronized void setState(DataSourceState state) {
        this.state = state;
    }

    public synchronized void setLazyInit(boolean lazyInit) {
        this.lazyInit = lazyInit;
    }
}
