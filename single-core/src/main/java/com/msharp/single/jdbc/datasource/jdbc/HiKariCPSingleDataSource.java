package com.msharp.single.jdbc.datasource.jdbc;

import com.msharp.single.jdbc.constants.Constants;
import com.msharp.single.jdbc.log.Logger;
import com.msharp.single.jdbc.log.LoggerFactory;

/**
 * HiKariCPSingleDataSource
 *
 * @author mwup
 * @version 1.0
 * @created 2018/10/29 13:51
 **/
public class HiKariCPSingleDataSource extends SingleDataSource {

	protected static final Logger LOGGER = LoggerFactory.getLogger(HiKariCPSingleDataSource.class);

	public HiKariCPSingleDataSource() {
		super();
		this.poolType = Constants.CONNECTION_POOL_TYPE_HIKARICP;
	}

	@Override
	protected Logger getLogger() {
		return LOGGER;
	}

	public void setUrl(String url) {
		super.setJdbcUrl(url);
	}

	public void setUsername(String username) {
		super.setUser(username);
	}

	@Override
	public void setPassword(String password) {
		super.setPassword(password);
	}

	public void setDriverClassName(String driverClassName) {
		setProperty("driverClassName", driverClassName);
	}

	@Override
	public void setPoolType(String poolType) {
		throw new UnsupportedOperationException("HiKariCPSingleDataSource does not need to set up pool type !");
	}

	// ////////////////////////////////////////////////////////////
	// set HiKariCP properties
	// ////////////////////////////////////////////////////////////

	public void setDataSourceClassName(String dataSourceClassName) {
		setProperty("dataSourceClassName", dataSourceClassName);
	}

	public void setPoolName(String poolName) {
		setProperty("poolName", poolName);
	}

	public void setAutoCommit(Boolean autoCommit) {
		setProperty("autoCommit", autoCommit);
	}

	public void setConnectionTimeout(long connectionTimeout) {
		setProperty("connectionTimeout", 1000L);
	}

	public void setIdleTimeout(long idleTimeout) {
		setProperty("idleTimeout", idleTimeout);
	}

	public void setMaxLifetime(long maxLifetime) {
		setProperty("maxLifetime", maxLifetime);
	}

	public void setConnectionTestQuery(String connectionTestQuery) {
		setProperty("connectionTestQuery", connectionTestQuery);
	}

	public void setMinimumIdle(int minimumIdle) {
		setProperty("minimumIdle", minimumIdle);
	}

	public void setMaximumPoolSize(int maximumPoolSize) {
		setProperty("maximumPoolSize", maximumPoolSize);
	}

	public void setInitializationFailTimeout(long initializationFailTimeout) {
		setProperty("initializationFailTimeout", initializationFailTimeout);
	}

	public void setIsolateInternalQueries(boolean isolateInternalQueries) {
		setProperty("isolateInternalQueries", isolateInternalQueries);
	}

	public void setAllowPoolSuspension(boolean allowPoolSuspension) {
		setProperty("allowPoolSuspension", allowPoolSuspension);
	}

	public void setReadOnly(boolean readOnly) {
		setProperty("readOnly", readOnly);
	}

	public void setRegisterMbeans(boolean registerMbeans) {
		setProperty("registerMbeans", registerMbeans);
	}

	public void setCatalog(String catalog) {
		setProperty("catalog", catalog);
	}

	@Override
	public void setConnectionInitSql(String connectionInitSql) {
		setProperty("connectionInitSql", connectionInitSql);
	}

	public void setTransactionIsolation(String transactionIsolation) {
		setProperty("transactionIsolation", transactionIsolation);
	}

	public void setValidationTimeout(long validationTimeout) {
		setProperty("validationTimeout", validationTimeout);
	}

	public void setLeakDetectionThreshold(long leakDetectionThreshold) {
		setProperty("leakDetectionThreshold", leakDetectionThreshold);
	}
}
