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
