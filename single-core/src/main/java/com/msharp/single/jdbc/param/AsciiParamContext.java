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

import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * TODO Comment of AsciiParamContext
 *
 * @author Leo Liang
 */
public class AsciiParamContext extends ParamContext {

    private static final long serialVersionUID = 1233295504362311453L;

    /**
     * @param index
     * @param values
     */
    public AsciiParamContext(int index, Object[] values) {
        super(index, values);
    }

    /**
     * (non-Javadoc)
     */
    @Override
    public void setParam(PreparedStatement stmt) throws SQLException {
        if (values.length == 1) {
            stmt.setAsciiStream(index, (InputStream) values[0]);
        } else if (values.length == 2 && values[1] instanceof Integer) {
            stmt.setAsciiStream(index, (InputStream) values[0], (Integer) values[1]);
        } else if (values.length == 2 && values[1] instanceof Long) {
            stmt.setAsciiStream(index, (InputStream) values[0], (Long) values[1]);
        }
    }
}
