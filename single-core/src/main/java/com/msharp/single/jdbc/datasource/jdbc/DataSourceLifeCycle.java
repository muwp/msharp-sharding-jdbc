package com.msharp.single.jdbc.datasource.jdbc;

import com.msharp.single.jdbc.enums.DataSourceState;

import java.sql.SQLException;

/**
 * TomcatJdbcSingleDataSource
 *
 * @author mwup
 * @version 1.0
 * @created 2018/10/29 13:51
 **/
public interface DataSourceLifeCycle {

	DataSourceState getState();

	void init();

	void markClosed();

	void markDown();

	void markUp();

	void close() throws SQLException;

}
