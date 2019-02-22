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
 * TODO Comment of BooleanParamContext
 *
 * @author Leo Liang
 */
public class BooleanParamContext extends ParamContext {

    private static final long serialVersionUID = -4390286596448078383L;

    /**
     * @param index
     * @param values
     */
    public BooleanParamContext(int index, Object[] values) {
        super(index, values);
    }

    /**
     * (non-Javadoc)
     *
     */
    @Override
    public void setParam(PreparedStatement stmt) throws SQLException {
        stmt.setBoolean(index, (Boolean) values[0]);
    }

}
