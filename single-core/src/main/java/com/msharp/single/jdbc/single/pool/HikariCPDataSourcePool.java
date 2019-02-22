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

import com.msharp.single.jdbc.enums.DataSourceState;
import com.msharp.single.jdbc.exception.MSharpConfigException;
import com.msharp.single.jdbc.log.Logger;
import com.msharp.single.jdbc.log.LoggerFactory;
import com.msharp.single.jdbc.single.config.DataSourceConfig;
import com.msharp.single.jdbc.single.jdbc.SingleDataSource;
import com.msharp.single.jdbc.util.JdbcDriverClassHelper;
import com.msharp.single.jdbc.util.StringUtils;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * HikariCPDataSourcePool
 *
 * @author mwup
 * @version 1.0
 * @created 2018/10/29 13:51
 **/
public class HikariCPDataSourcePool extends AbstractDataSourcePool implements DataSourcePool {

	protected static final Logger LOGGER = LoggerFactory.getLogger(HikariCPDataSourcePool.class);

	private HikariDataSource pool = null;

	@Override
	public DataSource build(DataSourceConfig config, boolean withDefaultValue) {
		this.pool = new HikariDataSource();

		this.pool.setJdbcUrl(config.getJdbcUrl());
		this.pool.setUsername(config.getUsername());
		this.pool.setPassword(config.getPassword());
		this.pool.setDriverClassName(JdbcDriverClassHelper.getDriverClassNameByJdbcUrl(config.getJdbcUrl()));

		if (withDefaultValue) {
			this.pool.setMaximumPoolSize(getIntProperty(config, "maxPoolSize", 30));
			this.pool.setMinimumIdle(getIntProperty(config, "minPoolSize", 5));
			this.pool.setConnectionTimeout(getLongProperty(config, "checkoutTimeout", 1000));
			this.pool.setConnectionTestQuery(getStringProperty(config, "preferredTestQuery", "SELECT 1"));
			this.pool.setIdleTimeout(getLongProperty(config, "maxIdleTime", 1800000L)); // 1 hours
			if (StringUtils.isNotBlank(getStringProperty(config, "connectionInitSql", null))) {
				this.pool.setConnectionInitSql(getStringProperty(config, "connectionInitSql", null));
			}
		} else {
			try {
				PropertiesInit<HikariDataSource> propertiesInit = new PropertiesInit<HikariDataSource>(this.pool);
				propertiesInit.initPoolProperties(config);
			} catch (Exception e) {
				throw new MSharpConfigException(String.format("HikariCP dataSource [%s] created error : ", config.getId()),
				      e);
			}
		}
		LOGGER.info(String.format("New dataSource [%s] created.", config.getId()));

		return this.pool;
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
			if (this.pool.getHikariPoolMXBean().getActiveConnections() == 0
			      || singleDataSource.getCloseAttempt() >= MAX_CLOSE_ATTEMPT) {
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
	public DataSource getInnerDataSourcePool() {
		return this.pool;
	}

	@Override
	public int getNumBusyConnection() {
		if (this.pool != null) {
			try {
				return this.pool.getHikariPoolMXBean().getActiveConnections();
			} catch (Exception e) {
			}
		}

		return 0;
	}

	@Override
	public int getNumConnections() {
		if (this.pool != null) {
			try {
				return this.pool.getHikariPoolMXBean().getTotalConnections();
			} catch (Exception e) {
			}
		}

		return 0;
	}

	@Override
	public int getNumIdleConnection() {
		if (this.pool != null) {
			try {
				return this.pool.getHikariPoolMXBean().getIdleConnections();
			} catch (Exception e) {
			}
		}

		return 0;
	}
}