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
