package com.msharp.single.jdbc.param;

import java.sql.Array;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * TODO Comment of ArrayParamContext
 *
 * @author mwup
 */
public class ArrayParamContext extends ParamContext {

    private static final long serialVersionUID = 5772726703630477288L;

    /**
     * @param index
     * @param values
     */
    public ArrayParamContext(int index, Object[] values) {
        super(index, values);
    }

    /**
     * (non-Javadoc)
     */
    @Override
    public void setParam(PreparedStatement stmt) throws SQLException {
        stmt.setArray(index, (Array) values[0]);
    }
}
