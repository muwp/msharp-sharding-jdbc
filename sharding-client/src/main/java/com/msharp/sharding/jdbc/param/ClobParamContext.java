/**
 * Project: zebra-client
 *
 * File Created at 2011-6-19
 * $Id$
 *
 * Copyright 2010 dianping.com.
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of
 * Dianping Company. ("Confidential Information").  You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with dianping.com.
 */
package com.msharp.sharding.jdbc.param;

import java.io.Reader;
import java.sql.Clob;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * TODO Comment of ClobParamContext
 *
 * @author Leo Liang
 */
public class ClobParamContext extends ParamContext {

	private static final long serialVersionUID = 1984927262977952703L;

	/**
	 * @param index
	 * @param values
	 */
	public ClobParamContext(int index, Object[] values) {
		super(index, values);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dianping.zebra.jdbc.param.ParamContext#setParam(java.sql. PreparedStatement)
	 */
	@Override
	public void setParam(PreparedStatement stmt) throws SQLException {
		if (values.length == 1 && values[0] instanceof Clob) {
			stmt.setClob(index, (Clob) values[0]);
		} else if (values.length == 1 && values[0] instanceof Reader) {
			stmt.setClob(index, (Reader) values[0]);
		} else if (values.length == 2) {
			stmt.setClob(index, (Reader) values[0], (Long) values[1]);
		}
	}

}
