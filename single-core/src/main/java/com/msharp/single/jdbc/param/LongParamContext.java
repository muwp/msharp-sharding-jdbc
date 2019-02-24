package com.msharp.single.jdbc.param;

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
