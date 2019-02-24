package com.msharp.single.jdbc.param;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * TODO Comment of ByteParamContext
 *
 * @author mwup
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

    @Override
    public void setParam(PreparedStatement stmt) throws SQLException {
        stmt.setByte(index, (Byte) values[0]);
    }

}
