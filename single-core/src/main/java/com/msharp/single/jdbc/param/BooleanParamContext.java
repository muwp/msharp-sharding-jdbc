package com.msharp.single.jdbc.param;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * TODO Comment of BooleanParamContext
 *
 * @author mwup
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

    @Override
    public void setParam(PreparedStatement stmt) throws SQLException {
        stmt.setBoolean(index, (Boolean) values[0]);
    }

}
