/**
 * Project: zebra-client
 * <p>
 * File Created at 2011-6-19
 * $Id$
 * <p>
 * Copyright 2010 dianping.com.
 * All rights reserved.
 * <p>
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
 * TODO Comment of LongParamContext
 *
 * @author Leo Liang
 */
public class LongParamContext extends ParamContext {

    private static final long serialVersionUID = -5706638948667220828L;

    /**
     * @param index
     * @param values
     */
    public LongParamContext(int index, Object[] values) {
        super(index, values);
    }

    /**
     * (non-Javadoc)
     */
    @Override
    public void setParam(PreparedStatement stmt) throws SQLException {
        stmt.setLong(index, (Long) values[0]);
    }

}
