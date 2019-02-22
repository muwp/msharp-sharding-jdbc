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
package com.msharp.single.jdbc.param;

import java.sql.PreparedStatement;
import java.sql.RowId;
import java.sql.SQLException;

/**
 * TODO Comment of RowIdParamContext
 *
 * @author Leo Liang
 */
public class RowIdParamContext extends ParamContext {

	private static final long serialVersionUID = 7121140907131100800L;

	/**
	 * @param index
	 * @param values
	 */
	public RowIdParamContext(int index, Object[] values) {
		super(index, values);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dianping.zebra.jdbc.param.ParamContext#setParam(java.sql. PreparedStatement)
	 */
	@Override
	public void setParam(PreparedStatement stmt) throws SQLException {
		stmt.setRowId(index, (RowId) values[0]);
	}

}
