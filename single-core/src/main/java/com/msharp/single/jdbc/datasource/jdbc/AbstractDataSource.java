package com.msharp.single.jdbc.datasource.jdbc;

import com.msharp.single.jdbc.constants.Constants;
import com.msharp.single.jdbc.util.StringUtils;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

/**
 * AbstractDataSource
 *
 * @author mwup
 * @version 1.0
 * @created 2018/10/29 13:51
 **/
public abstract class AbstractDataSource implements DataSource {

	public static final String LOCAL = Constants.CONFIG_MANAGER_TYPE_LOCAL;

	protected String configManagerType = LOCAL;

	private int loginTimeout = 0;

	private PrintWriter out = null;

	protected void close() throws SQLException {
	}

	@Override
	public PrintWriter getLogWriter() throws SQLException {
		return out;
	}

	@Override
	public void setLogWriter(PrintWriter out) throws SQLException {
		this.out = out;
	}

	@Override
	public int getLoginTimeout() throws SQLException {
		return this.loginTimeout;
	}

	@Override
	public void setLoginTimeout(int seconds) throws SQLException {
		this.loginTimeout = seconds;
	}

	public Logger getParentLogger() throws SQLFeatureNotSupportedException {
		throw new UnsupportedOperationException("getParentLogger");
	}

	protected void init() {
	}

	@Override
	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		if(iface == null) {
			return false;
		}

		return iface.isAssignableFrom(this.getClass());
	}

	public void setConfigManagerType(String configManagerType) {
		if (StringUtils.isNotBlank(configManagerType)) {
			this.configManagerType = configManagerType;
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T> T unwrap(Class<T> iface) throws SQLException {
		if(iface == null) {
			return null;
		}

		try {
			if(iface.isAssignableFrom(this.getClass())) {
				return (T) this;
			} else {
				throw new SQLException(getClass().getName() + " Can not unwrap to" + iface.getName());
			}
		} catch (Exception e) {
			throw new SQLException(e);
		}
	}
}
