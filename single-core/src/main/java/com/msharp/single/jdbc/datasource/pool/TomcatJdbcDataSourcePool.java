package com.msharp.single.jdbc.datasource.pool;

import com.msharp.single.jdbc.enums.DataSourceState;
import com.msharp.single.jdbc.exception.MSharpConfigException;
import com.msharp.single.jdbc.log.Logger;
import com.msharp.single.jdbc.log.LoggerFactory;
import com.msharp.single.jdbc.datasource.config.DataSourceConfig;
import com.msharp.single.jdbc.datasource.jdbc.SingleDataSource;
import com.msharp.single.jdbc.util.JdbcDriverClassHelper;
import com.msharp.single.jdbc.util.StringUtils;
import org.apache.tomcat.jdbc.pool.PoolProperties;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * TomcatJdbcDataSourcePool
 *
 * @author mwup
 * @version 1.0
 * @created 2018/10/29 13:51
 **/
public class TomcatJdbcDataSourcePool extends AbstractDataSourcePool implements DataSourcePool {

	protected static final Logger LOGGER = LoggerFactory.getLogger(TomcatJdbcDataSourcePool.class);

	private org.apache.tomcat.jdbc.pool.DataSource pool = null;

	@Override
	public DataSource build(DataSourceConfig config, boolean withDefaultValue) {
		PoolProperties properties = new PoolProperties();

		properties.setUrl(config.getJdbcUrl());
		properties.setUsername(config.getUsername());
		properties.setPassword(config.getPassword());
		properties.setDriverClassName(JdbcDriverClassHelper.getDriverClassNameByJdbcUrl(config.getJdbcUrl()));

		if (withDefaultValue) {
			properties.setInitialSize(getIntProperty(config, "initialPoolSize", 5));
			properties.setMaxActive(getIntProperty(config, "maxPoolSize", 30));
			properties.setMinIdle(getIntProperty(config, "minPoolSize", 5));
			properties.setMaxIdle(getIntProperty(config, "maxPoolSize", 20));
			properties.setMaxWait(getIntProperty(config, "checkoutTimeout", 1000));
			properties.setValidationQuery(getStringProperty(config, "preferredTestQuery", "SELECT 1"));
			properties.setMinEvictableIdleTimeMillis(getIntProperty(config, "minEvictableIdleTimeMillis", 300000));// 5min
			properties.setTimeBetweenEvictionRunsMillis(getIntProperty(config, "timeBetweenEvictionRunsMillis", 30000)); // 30s
			properties.setNumTestsPerEvictionRun(getIntProperty(config, "numTestsPerEvictionRun", 6));
			properties.setValidationQueryTimeout(getIntProperty(config, "validationQueryTimeout", 0));
			properties.setValidationInterval(getIntProperty(config, "validationInterval", 30000));// 30s
			properties.setRemoveAbandonedTimeout(getIntProperty(config, "removeAbandonedTimeout", 300));
			if (StringUtils.isNotBlank(getStringProperty(config, "connectionInitSql", null))) {
				properties.setInitSQL(getStringProperty(config, "connectionInitSql", null));
			}

			properties.setTestWhileIdle(true);
			properties.setTestOnBorrow(false);
			properties.setTestOnReturn(false);
			properties.setRemoveAbandoned(true);
		} else {
			try {
				PropertiesInit<PoolProperties> propertiesInit = new PropertiesInit<PoolProperties>(properties);
				propertiesInit.initPoolProperties(config);
			} catch (Exception e) {
				throw new MSharpConfigException(
				      String.format("tomcat-jdbc dataSource [%s] created error : ", config.getId()), e);
			}
		}

		org.apache.tomcat.jdbc.pool.DataSource datasource = new org.apache.tomcat.jdbc.pool.DataSource();
		datasource.setPoolProperties(properties);

		this.pool = datasource;
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
			if (this.pool.getActive() == 0 || singleDataSource.getCloseAttempt() >= MAX_CLOSE_ATTEMPT) {
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
				return this.pool.getActive();
			} catch (Exception e) {
			}
		}

		return 0;
	}

	@Override
	public int getNumConnections() {
		if (this.pool != null) {
			try {
				return this.pool.getPoolSize();
			} catch (Exception e) {
			}
		}

		return 0;
	}

	@Override
	public int getNumIdleConnection() {
		if (this.pool != null) {
			try {
				return this.pool.getIdle();
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
