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

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * TODO Comment of ByteParamContext
 *
 * @author Leo Liang
 */
public class ByteParamContext extends ParamContext {

	private static final long serialVersionUID = -9109030072336300120L;

	/**
	 * @param index
	 * @param values
	 */
	public ByteParamContext(int index, Object[] values) {
		super(index, values);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dianping.zebra.jdbc.param.ParamContext#setParam(java.sql. PreparedStatement)
	 */
	@Override
	public void setParam(PreparedStatement stmt) throws SQLException {
		stmt.setByte(index, (Byte) values[0]);
	}

}
