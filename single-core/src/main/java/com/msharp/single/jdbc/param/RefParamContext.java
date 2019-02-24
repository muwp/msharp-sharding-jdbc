package com.msharp.single.jdbc.param;

import java.sql.PreparedStatement;
import java.sql.Ref;
import java.sql.SQLException;

/**
 * TODO Comment of RefParamContext
 *
 * @author Leo Liang
 */
public class RefParamContext extends ParamContext {

    private static final long serialVersionUID = -6226089996782874509L;

    /**
     * @param index
     * @param values
     */
    public RefParamContext(int index, Object[] values) {
        super(index, values);
    }

    @Override
    public void setParam(PreparedStatement stmt) throws SQLException {
        stmt.setRef(index, (Ref) values[0]);
    }

}
