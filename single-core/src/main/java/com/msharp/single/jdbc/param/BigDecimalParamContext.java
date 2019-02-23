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

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * TODO Comment of BigDecimalParamContext
 *
 * @author mwup
 */
public class BigDecimalParamContext extends ParamContext {

    private static final long serialVersionUID = -6915832597431575810L;

    /**
     * @param index
     * @param values
     */
    public BigDecimalParamContext(int index, Object[] values) {
        super(index, values);
    }

    @Override
    public void setParam(PreparedStatement stmt) throws SQLException {
        stmt.setBigDecimal(index, (BigDecimal) values[0]);
    }

}
