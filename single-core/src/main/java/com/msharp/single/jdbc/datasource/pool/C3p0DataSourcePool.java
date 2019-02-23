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
package com.msharp.single.jdbc.datasource.pool;

import com.mchange.v2.c3p0.DataSources;
import com.mchange.v2.c3p0.PoolBackedDataSource;
import com.msharp.single.jdbc.enums.DataSourceState;
import com.msharp.single.jdbc.exception.MSharpConfigException;
import com.msharp.single.jdbc.log.Logger;
import com.msharp.single.jdbc.log.LoggerFactory;
import com.msharp.single.jdbc.datasource.config.Any;
import com.msharp.single.jdbc.datasource.config.DataSourceConfig;
import com.msharp.single.jdbc.datasource.jdbc.SingleDataSource;
import com.msharp.single.jdbc.util.JdbcDriverClassHelper;
import com.msharp.single.jdbc.util.StringUtils;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * C3p0DataSourcePool
 *
 * @author mwup
 * @version 1.0
 * @created 2018/10/29 13:51
 **/
public class C3p0DataSourcePool extends AbstractDataSourcePool implements DataSourcePool {

	protected static final Logger LOGGER = LoggerFactory.getLogger(C3p0DataSourcePool.class);

	private PoolBackedDataSource pool = null;

	@Override
	public DataSource build(DataSourceConfig config, boolean withDefaultValue) {
		try {
			DataSource unPooledDataSource = DataSources.unpooledDataSource(config.getJdbcUrl(), config.getUsername(),
					config.getPassword());
			final Map<String, Object> props = new HashMap<String, Object>();

			props.put("driverClass", StringUtils.isNotBlank(config.getDriverClass()) ? config.getDriverClass() : JdbcDriverClassHelper.getDriverClassNameByJdbcUrl(config.getJdbcUrl()));
			for (Any any : config.getProperties()) {
				props.put(any.getName(), any.getValue());
			}

			if (props.containsKey("connectionInitSql")) {
				throw new MSharpConfigException(
						"c3p0 does not support connectionInitSql method, please select other dataSource pool");
			}

			if(!props.containsKey("checkoutTimeout")){
				props.put("checkoutTimeout",1000);
			}

			this.pool = (PoolBackedDataSource) DataSources.pooledDataSource(unPooledDataSource, props);

			LOGGER.info(String.format("New dataSource [%s] created.", config.getId()));

			return this.pool;
		} catch (MSharpConfigException e) {
			throw e;
		} catch (Exception e) {
			throw new MSharpConfigException(String.format("c3p0 dataSource [%s] created error : ", config.getId()), e);
		}
	}

	@Override
	public void close(SingleDataSource singleDataSource, boolean forceClose) throws SQLException {
		String dsId = singleDataSource.getId();
		LOGGER.info(singleDataSource.getAndIncrementCloseAttempt() + " attempt to close datasource [" + dsId + "]");

		if (forceClose) {
			LOGGER.info("closing old datasource [" + dsId + "]");

			DataSources.destroy(this.pool);

			LOGGER.info("datasource [" + dsId + "] closed");
			singleDataSource.setState(DataSourceState.CLOSED);
		} else {
			if (this.pool.getNumBusyConnections() == 0 || singleDataSource.getCloseAttempt() >= MAX_CLOSE_ATTEMPT) {
				LOGGER.info("closing old datasource [" + dsId + "]");

				DataSources.destroy(this.pool);

				LOGGER.info("datasource [" + dsId + "] closed");
				singleDataSource.setState(DataSourceState.CLOSED);
			} else {
				throwException(dsId);
			}
		}
	}

	@Override
	public int getNumBusyConnection() {
		if (pool != null) {
			try {
				return pool.getNumBusyConnections();
			} catch (Exception e) {
			}
		}

		return 0;
	}

	@Override
	public int getNumConnections() {
		if (pool != null) {
			try {
				return pool.getNumConnections();
			} catch (Exception e) {
			}
		}

		return 0;
	}

	@Override
	public int getNumIdleConnection() {
		if (pool != null) {
			try {
				return pool.getNumIdleConnections();
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