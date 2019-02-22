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
package com.msharp.single.jdbc.param;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * TODO Comment of NStringParamContext
 *
 * @author Leo Liang
 */
public class NStringParamContext extends ParamContext {

    private static final long serialVersionUID = 4189680077757800337L;

    /**
     * @param index
     * @param values
     */
    public NStringParamContext(int index, Object[] values) {
        super(index, values);
    }

    /**
     * (non-Javadoc)
     */
    @Override
    public void setParam(PreparedStatement stmt) throws SQLException {
        stmt.setNString(index, (String) values[0]);
    }

}
