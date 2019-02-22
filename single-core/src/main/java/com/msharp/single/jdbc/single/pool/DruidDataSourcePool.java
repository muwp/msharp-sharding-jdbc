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
package com.msharp.single.jdbc.single.pool;

import com.alibaba.druid.pool.DruidDataSource;
import com.msharp.single.jdbc.enums.DataSourceState;
import com.msharp.single.jdbc.exception.MSharpConfigException;
import com.msharp.single.jdbc.log.Logger;
import com.msharp.single.jdbc.log.LoggerFactory;
import com.msharp.single.jdbc.single.config.DataSourceConfig;
import com.msharp.single.jdbc.single.jdbc.SingleDataSource;
import com.msharp.single.jdbc.util.JdbcDriverClassHelper;
import com.msharp.single.jdbc.util.StringUtils;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * C3p0DataSourcePool
 *
 * @author mwup
 * @version 1.0
 * @created 2018/10/29 13:51
 **/
public class DruidDataSourcePool extends AbstractDataSourcePool implements DataSourcePool {

	protected static final Logger LOGGER = LoggerFactory.getLogger(DruidDataSourcePool.class);

	protected static Map<String, Integer> dsNameIdMap = new HashMap<String, Integer>();

	private DruidDataSource pool = null;

	@SuppressWarnings("deprecation")
	@Override
	public DataSource build(DataSourceConfig config, boolean withDefaultValue) {
		DruidDataSource druidDataSource = new DruidDataSource();

		druidDataSource.setName(buildDsName(config));
		druidDataSource.setUrl(config.getJdbcUrl());
		druidDataSource.setUsername(config.getUsername());
		druidDataSource.setPassword(config.getPassword());
		druidDataSource.setDriverClassName(StringUtils.isNotBlank(config.getDriverClass()) ? config.getDriverClass() : JdbcDriverClassHelper.getDriverClassNameByJdbcUrl(config.getJdbcUrl()));

		if (withDefaultValue) {
			druidDataSource.setInitialSize(getIntProperty(config, "initialPoolSize", 5));
			druidDataSource.setMaxActive(getIntProperty(config, "maxPoolSize", 30));
			druidDataSource.setMinIdle(getIntProperty(config, "minPoolSize", 5));
			druidDataSource.setMaxWait(getIntProperty(config, "checkoutTimeout", 1000));
			druidDataSource.setValidationQuery(getStringProperty(config, "preferredTestQuery", "SELECT 1"));
			druidDataSource.setMinEvictableIdleTimeMillis(getIntProperty(config, "minEvictableIdleTimeMillis", 1800000));// 30min
			druidDataSource.setTimeBetweenEvictionRunsMillis(getIntProperty(config, "timeBetweenEvictionRunsMillis", 30000)); // 30s
			druidDataSource.setNumTestsPerEvictionRun(getIntProperty(config, "numTestsPerEvictionRun", 6));
			druidDataSource.setValidationQueryTimeout(getIntProperty(config, "validationQueryTimeout", 0));
			druidDataSource.setRemoveAbandonedTimeout(getIntProperty(config, "removeAbandonedTimeout", 300));
			if (StringUtils.isNotBlank(getStringProperty(config, "connectionInitSql", null))) {
				List<String> initSqls = new ArrayList<String>();
				initSqls.add(getStringProperty(config, "connectionInitSql", null));
				druidDataSource.setConnectionInitSqls(initSqls);
			}

			druidDataSource.setTestWhileIdle(true);
			druidDataSource.setTestOnBorrow(false);
			druidDataSource.setTestOnReturn(false);
			druidDataSource.setRemoveAbandoned(true);
			druidDataSource.setUseUnfairLock(true);
			druidDataSource.setNotFullTimeoutRetryCount(-1);
		} else {
			try {
				PropertiesInit<DruidDataSource> propertiesInit = new PropertiesInit<DruidDataSource>(druidDataSource);
				propertiesInit.initPoolProperties(config);
			} catch (Exception e) {
				throw new MSharpConfigException(String.format("druid dataSource [%s] created error : ", config.getId()), e);
			}
		}

		this.pool = druidDataSource;
		LOGGER.info(String.format("New dataSource [%s] created.", config.getId()));

		return this.pool;
	}

	public static synchronized String buildDsName(DataSourceConfig config) {
		Integer count = dsNameIdMap.get(config.getId());
		if (count == null) {
			dsNameIdMap.put(config.getId(), 0);
			return config.getId();
		} else {
			dsNameIdMap.put(config.getId(), count + 1);
			return config.getId() + "-" + (count + 1);
		}
	}

	@Override
	public void close(SingleDataSource singleDataSource, boolean forceClose) throws SQLException {
		String dsId = singleDataSource.getId();
		LOGGER.info(singleDataSource.getAndIncrementCloseAttempt() + " attempt to close datasource [" + dsId + "]");

		if (forceClose) {
			LOGGER.info("closing old datasource [" + dsId + "]");

			this.pool.close();

			singleDataSource.setState(DataSourceState.CLOSED);
			LOGGER.info("old datasource [" + dsId + "] closed");
		} else {
			if (this.pool.getActiveCount() == 0 || singleDataSource.getCloseAttempt() >= MAX_CLOSE_ATTEMPT) {
				LOGGER.info("closing old datasource [" + dsId + "]");

				this.pool.close();

				LOGGER.info("old datasource [" + dsId + "] closed");
				singleDataSource.setState(DataSourceState.CLOSED);
			} else {
				throwException(dsId);
			}
		}
	}

	@Override
	public int getNumBusyConnection() {
		if (this.pool != null) {
			try {
				return this.pool.getActiveCount();
			} catch (Exception e) {
			}
		}

		return 0;
	}

	@Override
	public int getNumConnections() {
		if (this.pool != null) {
			try {
				return this.pool.getPoolingCount() + this.pool.getActiveCount();
			} catch (Exception e) {
			}
		}

		return 0;
	}

	@Override
	public int getNumIdleConnection() {
		if (this.pool != null) {
			try {
				return (this.pool.getPoolingCount());
			} catch (Exception e) {
			}
		}

		return 0;
	}

	@Override
	public DataSource getInnerDataSourcePool() {
		return this.pool;
	}
}
