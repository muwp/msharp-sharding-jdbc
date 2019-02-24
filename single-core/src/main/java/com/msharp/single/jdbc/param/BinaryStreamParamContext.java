package com.msharp.single.jdbc.param;

import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * TODO Comment of BinaryStreamParamContext
 *
 * @author Leo Liang
 */
public class BinaryStreamParamContext extends ParamContext {

    private static final long serialVersionUID = 8395319863452179327L;

    /**
     * @param index
     * @param values
     */
    public BinaryStreamParamContext(int index, Object[] values) {
        super(index, values);
    }

    @Override
    public void setParam(PreparedStatement stmt) throws SQLException {
        if (values.length == 1) {
            stmt.setBinaryStream(index, (InputStream) values[0]);
        } else if (values.length == 2 && values[1] instanceof Integer) {
            stmt.setBinaryStream(index, (InputStream) values[0], (Integer) values[1]);
        } else if (values.length == 2 && values[1] instanceof Long) {
            stmt.setBinaryStream(index, (InputStream) values[0], (Long) values[1]);
        }
    }

}
