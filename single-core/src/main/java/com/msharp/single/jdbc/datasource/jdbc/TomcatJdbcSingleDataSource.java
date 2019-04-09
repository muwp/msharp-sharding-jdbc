package com.msharp.single.jdbc.datasource.jdbc;

import com.msharp.single.jdbc.constants.Constants;
import com.msharp.single.jdbc.log.Logger;
import com.msharp.single.jdbc.log.LoggerFactory;

/**
 * TomcatJdbcSingleDataSource
 *
 * @author mwup
 * @version 1.0
 * @created 2018/10/29 13:51
 **/
public class TomcatJdbcSingleDataSource extends SingleDataSource {

    protected static final Logger LOGGER = LoggerFactory.getLogger(TomcatJdbcSingleDataSource.class);

    public TomcatJdbcSingleDataSource() {
        super();
        this.poolType = Constants.CONNECTION_POOL_TYPE_TOMCAT_JDBC;
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
        this.setProperty("driverClassName", driverClassName);
    }

    @Override
    public void setDriverClass(String driverClass) {
        this.setProperty("driverClassName", driverClass);
    }

    @Override
    public void setPoolType(String poolType) {
        throw new UnsupportedOperationException("TomcatJdbcSingleDataSource does not need to set up pool type !");
    }

    @Override
    protected Logger getLogger() {
        return LOGGER;
    }

    // ////////////////////////////////////////////////////////////
    // set tomcat-jdbc properties
    // ////////////////////////////////////////////////////////////

    // base property
    public void setDefaultAutoCommit(Boolean defaultAutoCommit) {
        setProperty("defaultAutoCommit", defaultAutoCommit);
    }

    public void setDefaultReadOnly(Boolean defaultReadOnly) {
        setProperty("defaultReadOnly", defaultReadOnly);
    }

    public void setDefaultTransactionIsolation(int defaultTransactionIsolation) {
        setProperty("defaultTransactionIsolation", defaultTransactionIsolation);
    }

    public void setDefaultCatalog(String defaultCatalog) {
        setProperty("defaultCatalog", defaultCatalog);
    }

    public void setMaxActive(int maxActive) {
        setProperty("maxActive", maxActive);
    }

    public void setMaxIdle(int maxIdle) {
        setProperty("maxIdle", maxIdle);
    }

    public void setMinIdle(int minIdle) {
        setProperty("minIdle", minIdle);
    }

    public void setInitialSize(int initialSize) {
        setProperty("initialSize", initialSize);
    }

    public void setMaxWait(int maxWait) {
        setProperty("maxWait", maxWait);
    }

    public void setTestOnBorrow(boolean testOnBorrow) {
        setProperty("testOnBorrow", testOnBorrow);
    }

    public void setTestOnConnect(boolean testOnConnect) {
        setProperty("testOnConnect", testOnConnect);
    }

    public void setTestOnReturn(boolean testOnReturn) {
        setProperty("testOnReturn", testOnReturn);
    }

    public void setTestWhileIdle(boolean testWhileIdle) {
        setProperty("testWhileIdle", testWhileIdle);
    }

    public void setValidationQuery(String validationQuery) {
        setProperty("validationQuery", validationQuery);
    }

    public void setValidationQueryTimeout(int validationQueryTimeout) {
        setProperty("validationQueryTimeout", validationQueryTimeout);
    }

    public void setValidatorClassName(String validatorClassName) {
        setProperty("validatorClassName", validatorClassName);
    }

    public void setTimeBetweenEvictionRunsMillis(int timeBetweenEvictionRunsMillis) {
        setProperty("timeBetweenEvictionRunsMillis", timeBetweenEvictionRunsMillis);
    }

    public void setNumTestsPerEvictionRun(int numTestsPerEvictionRun) {
        setProperty("numTestsPerEvictionRun", numTestsPerEvictionRun);
    }

    public void setMinEvictableIdleTimeMillis(int minEvictableIdleTimeMillis) {
        setProperty("minEvictableIdleTimeMillis", minEvictableIdleTimeMillis);
    }

    public void setAccessToUnderlyingConnectionAllowed(boolean accessToUnderlyingConnectionAllowed) {
        setProperty("accessToUnderlyingConnectionAllowed", accessToUnderlyingConnectionAllowed);
    }

    public void setRemoveAbandoned(boolean removeAbandoned) {
        setProperty("removeAbandoned", removeAbandoned);
    }

    public void setRemoveAbandonedTimeout(int removeAbandonedTimeout) {
        setProperty("removeAbandonedTimeout", removeAbandonedTimeout);
    }

    public void setLogAbandoned(boolean logAbandoned) {
        setProperty("logAbandoned", logAbandoned);
    }

    public void setConnectionProperties(String connectionProperties) {
        setProperty("connectionProperties", connectionProperties);
    }

    public void setPoolPreparedStatements(boolean poolPreparedStatements) {
        setProperty("poolPreparedStatements", poolPreparedStatements);
    }

    public void setMaxOpenPreparedStatements(int maxOpenPreparedStatements) {
        setProperty("maxOpenPreparedStatements", maxOpenPreparedStatements);
    }

    // advanced property
    public void setInitSQL(String initSQL) {
        setProperty("initSQL", initSQL);
    }

    public void setJdbcInterceptors(String interceptors) {
        setProperty("jdbcInterceptors", interceptors);
    }

    public void setValidationInterval(long validationInterval) {
        setProperty("validationInterval", validationInterval);
    }

    public void setJmxEnabled(boolean enabled) {
        setProperty("jmxEnabled", enabled);
    }

    public void setFairQueue(boolean fairQueue) {
        setProperty("fairQueue", fairQueue);
    }

    public void setAbandonWhenPercentageFull(int percentage) {
        setProperty("abandonWhenPercentageFull", percentage);
    }

    public void setMaxAge(long maxAge) {
        setProperty("maxAge", maxAge);
    }

    public void setUseEquals(boolean useEquals) {
        setProperty("useEquals", useEquals);
    }

    public void setSuspectTimeout(int seconds) {
        setProperty("suspectTimeout", seconds);
    }

    public void setRollbackOnReturn(boolean rollbackOnReturn) {
        setProperty("rollbackOnReturn", rollbackOnReturn);
    }

    public void setCommitOnReturn(boolean commitOnReturn) {
        setProperty("commitOnReturn", commitOnReturn);
    }

    public void setAlternateUsernameAllowed(boolean alternateUsernameAllowed) {
        setProperty("alternateUsernameAllowed", alternateUsernameAllowed);
    }

    public void setDataSource(Object ds) {
        throw new UnsupportedOperationException("zebra does not support setDataSource method in tomcat-jdbc");
    }

    public void setDataSourceJNDI(String jndiDS) {
        setProperty("dataSourceJNDI", jndiDS);
    }

    public void setUseDisposableConnectionFacade(boolean useDisposableConnectionFacade) {
        setProperty("useDisposableConnectionFacade", useDisposableConnectionFacade);
    }

    public void setLogValidationErrors(boolean logValidationErrors) {
        setProperty("logValidationErrors", logValidationErrors);
    }

    public void setPropagateInterruptState(boolean propagateInterruptState) {
        setProperty("propagateInterruptState", propagateInterruptState);
    }

    public void setIgnoreExceptionOnPreLoad(boolean ignoreExceptionOnPreLoad) {
        setProperty("ignoreExceptionOnPreLoad", ignoreExceptionOnPreLoad);
    }

    public void setName(String name) {
        setProperty("name", name);
    }

    public void setUseLock(boolean useLock) {
        setProperty("useLock", useLock);
    }

}
